package sample;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

public class Main extends Application {
    KetNoiCSDL kncsdl;
    ComboBox<String> cboKhachHang;
    ComboBox<String> cboMatHang;
    TextField txtSoHoaDon, txtTenKhachHang, txtDiaChi, txtDienThoai, txtTongTriGia;
    DatePicker dpkNgayBan;
    GridPane gridPane;
    ScrollPane scrollPane;
    Button btnNew, btnAddCT, btnSave, btnClose;
    int nRowsCT = 0;
    List<TextField> txtSTTList = new ArrayList(), txMSMatHangList = new ArrayList(), txtTenMatHangList = new ArrayList(), txtDVTlist = new ArrayList(), txtSoLuongList = new ArrayList(), txtDonGiaList = new ArrayList(), txtTriGiaList = new ArrayList();
    String MSKH_Luu = "";
    String MSHH_Luu="";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Nhập dữ liệu phiếu bánhàng");
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        VBox vBoxTop = new VBox();
        HBox hBox1 = new HBox();
        Label lblSoHoaDon = new Label("Số hóa đơn");
        txtSoHoaDon = new TextField();
        txtSoHoaDon.setOnKeyReleased(e -> Kiem_Hop_Le());
        Label lblNgayBan = new Label("Ngày bán");
        dpkNgayBan = new DatePicker(LocalDate.now());
        hBox1.getChildren().addAll(lblSoHoaDon, txtSoHoaDon, lblNgayBan, dpkNgayBan);
        hBox1.setSpacing(10);
        HBox hBox2 = new HBox();
        Label lblMSKhachHang = new Label("Mã số khách hàng:");
        cboKhachHang = new ComboBox<>();
        cboKhachHang.setMinWidth(100);
        Label lblTenKhachHang = new Label("Tên khách hàng");
        txtTenKhachHang = new TextField();
        txtTenKhachHang.setMinWidth(300);
        txtTenKhachHang.setEditable(false);
        hBox2.getChildren().addAll(lblMSKhachHang, cboKhachHang, lblTenKhachHang, txtTenKhachHang);
        hBox2.setSpacing(10);
        HBox hBox3 = new HBox();
        Label lblDiaChi = new Label("Địa chỉ:");
        txtDiaChi = new TextField();
        txtDiaChi.setMinWidth(300);
        txtDiaChi.setEditable(false);
        Label lblDienThoai = new Label("Điện thoại");
        txtDienThoai = new TextField();
        txtDienThoai.setEditable(false);
        hBox3.getChildren().addAll(lblDiaChi, txtDiaChi, lblDienThoai, txtDienThoai);
        vBoxTop.getChildren().addAll(hBox1, hBox2, hBox3);
        layout.setTop(vBoxTop);
        VBox vBoxCenter = new VBox();
        HBox hBoxTitle = new HBox();
        hBoxTitle.setMaxWidth(900);
        Label lblSTT = new Label("STT");
        lblSTT.setMinWidth(50);
        Label lblMSMatHang = new Label("MS Mặt hàng");
        lblMSMatHang.setMinWidth(100);
        Label lblTenMatHang = new Label("Tên mặt hàng");
        lblTenMatHang.setMinWidth(200);
        Label lblDVT = new Label("Đơn vị tính");
        lblDVT.setMinWidth(80);
        Label lblSoLuong = new Label("Số lượng");
        lblSoLuong.setMinWidth(100);
        Label lblDonGia = new Label("Đơn giá");
        lblDonGia.setMinWidth(100);
        Label lblTriGia = new Label("Trị giá");
        lblTriGia.setMinWidth(100);
        hBoxTitle.getChildren().addAll(lblSTT, lblMSMatHang, lblTenMatHang, lblDVT, lblSoLuong, lblDonGia, lblTriGia);
        hBoxTitle.setSpacing(10);
        gridPane = new GridPane();
        scrollPane = new ScrollPane(gridPane);
        scrollPane.setMaxWidth(820);
        HBox hBoxSummary = new HBox();
        hBoxSummary.setMaxWidth(820);
        Label lblTongTriGia = new Label("tổng :");
        lblTriGia.setMinWidth(100);
        txtTongTriGia = new TextField("0");
        txtTongTriGia.setAlignment(Pos.CENTER_RIGHT);
        hBoxSummary.getChildren().addAll(lblTongTriGia, txtTongTriGia);
        hBoxSummary.setAlignment(Pos.CENTER_RIGHT);
        hBoxSummary.setPadding(new Insets(10, 10, 10, 10));
        vBoxCenter.getChildren().addAll(hBoxTitle, scrollPane, hBoxSummary);
        layout.setCenter(vBoxCenter);
        HBox cmdBtnGroup = new HBox();
        btnNew = new Button("Tạo mới");
        btnNew.setDisable(true);
        btnAddCT = new Button("Thêm Chi Tiết");
        btnSave = new Button("Lưu");
        btnSave.setDisable(true);
        btnSave.setMinWidth(100);
        btnClose = new Button("Đóng");
        btnClose.setMinWidth(100);
        cmdBtnGroup.getChildren().addAll(btnNew, btnAddCT, btnSave, btnClose);
        cmdBtnGroup.setSpacing(10);
        layout.setBottom(cmdBtnGroup);
        layout.setPadding(new Insets(10));
        primaryStage.setScene(new Scene(layout, 850, 300));
        primaryStage.show();
        btnNew.setOnAction(e -> TaoMoi());
        btnAddCT.setOnAction(e -> ThemChiTiet());
        btnSave.setOnAction(e -> Luu_ToanBo());
        btnClose.setOnAction(e -> primaryStage.close());
        cboMatHang = new ComboBox<>();
        cboMatHang.setMinWidth(100);
        cboKhachHang.setOnAction(e->cboKhacHang_Click());
        KetNoiCSDL();
    }

    public void TaoMoi() {
        if (nRowsCT > 0) {
            gridPane.getChildren().clear();
            txtSTTList.clear();
            txMSMatHangList.clear();
            txtTenMatHangList.clear();
            txtDVTlist.clear();
            txtSoLuongList.clear();
            txtDonGiaList.clear();
            txtTriGiaList.clear();
            nRowsCT = 0;
            txtSoHoaDon.setText(String.format("%08d", Integer.parseInt(txtSoHoaDon.getText()) + 1));
            btnNew.setDisable(true);
            TriGia_Change();
        }

    }
    public void  MSMH_MouseClick(String s){
        String id=s.substring(2);
        int row= Integer.parseInt(id);
        cboMatHang.setId("cboMH"+id);
        System.out.println(id);
        cboMatHang.setOnAction(e->setCboMSMH_Click(cboMatHang.getId()));

        cboMatHang.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean>
                                        observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue()) {
                   //focusGained(cboMatHang);
                } else {
                    focusLost(cboMatHang);
                }
            }
        });
        gridPane.add(cboMatHang, 1, row);
    }
    public void focusLost(ComboBox cbo){
        String id = cbo.getId().substring(5);
        gridPane.getChildren().remove(cbo);
        Kiem_Hop_Le();
    }
//    public void focusGained(ComboBox cbo){
//        String id = cbo.getId().substring(5);
//        gridPane.getChildren().equals(cbo);
//        Kiem_Hop_Le();
//    }
    public void  Luu_ToanBo(){
        try
        {
            kncsdl.Them_HoaDon(txtSoHoaDon.getText(),dpkNgayBan.getValue().toString(), MSKH_Luu);
            for(int i=0; i<=nRowsCT; i++) {
                kncsdl.Them_ChiTietHoaDon(txtSoHoaDon.getText(),txMSMatHangList.get(i).getText(),
                        Float.parseFloat(txtSoLuongList.get(i).getText()),
                        Float.parseFloat(txtTriGiaList.get(i).getText().replace(".","")));
            }
            btnSave.setDisable(false);
            btnNew.setDisable(false);
        } catch (Exception e)
        {
            System.out.println(e.getMessage()+ ". Cần chuyển sang Region Settings là Việt Nam");
        }
    }
    public void Kiem_Hop_Le(){
        boolean HopLe = SHD_HopLe_LucThem() && MSKH_HopLe() && nRowsCT>0 && MSMH_HopLe()
                && SL_HopLe();
        btnSave.setDisable(false);
    }
    public void KetNoiCSDL(){
        final String fileName = "CSDLBH1"; // "MyData";
        kncsdl = new KetNoiCSDL(fileName);
        try {
            kncsdl.DocKhachHang(cboKhachHang);

        }
        catch (Exception e)
        {
            System.out.println("Không thể đọc bảng khách hàng");
        }
        try {
            kncsdl.DocMatHang(cboMatHang);
        } catch (Exception e)
        {
            System.out.println("Không thể đọc bảng mặt hàng");
        }
    }
    public void  cboKhacHang_Click(){
        try {
            MSKH_Luu=cboKhachHang.getSelectionModel().getSelectedItem().toString();
            ResultSet rs = kncsdl.Tim_MSKhachHang(MSKH_Luu);
            while (rs.next()){
                cboKhachHang.setValue(rs.getString(1));
                txtTenKhachHang.setText(rs.getString(2));
                txtDiaChi.setText(rs.getString(3));
                txtDienThoai.setText(rs.getString(4));
            }
        }
        catch (Exception e) {
        }
        Kiem_Hop_Le();
    }
    public void setCboMSMH_Click(String s){
        int index=cboMatHang.getSelectionModel().getSelectedIndex();
        try {
            MSHH_Luu = cboMatHang.getSelectionModel().getSelectedItem().toString();
            //System.out.println(cboKhachHang.getSelectionModel().getSelectedItem());
            ResultSet rs = kncsdl.Tim_MSMH(MSHH_Luu);

            while (rs.next()) {
                for (int i=0;i<10;i++) {
                    txMSMatHangList.add(i,new TextField(rs.getString(1)));
                    txtTenMatHangList.add(i, new TextField(rs.getString(2)));
                    txtDVTlist.add(i, new TextField(rs.getString(3)));
                    txtDonGiaList.add(i, new TextField(rs.getString(4)));
                }
        }   txtMSMH.setText(txMSMatHangList.get(index).getText());
            txtTenMH.setText(txtTenMatHangList.get(index).getText());
            txtDonGia.setText(txtDonGiaList.get(index).getText());
            txtDVT.setText(txtDVTlist.get(index).getText());

        }
        catch (Exception e) {
        }
        Kiem_Hop_Le();
    }TextField txtMSMH,txtTenMH,txtDVT,txtDonGia;
       public void ThemChiTiet(){
        TextField txt;
        txt = new TextField(); txtSTTList.add(txt); txt.setPrefWidth(50);
        gridPane.add(txt,0, nRowsCT);
        txtMSMH=new TextField();
        txMSMatHangList.add(txtMSMH);
        txtMSMH.setPrefWidth(100);
        txtMSMH.setId("MH"+nRowsCT);
        txtMSMH.setOnMouseClicked(e-> MSMH_MouseClick(txtMSMH.getId()));
        gridPane.add(txtMSMH, 1, nRowsCT);
        txtTenMH = new TextField();
        txtTenMH.setPrefWidth(200);
        txtTenMH.setEditable(false);
        gridPane.add(txtTenMH, 2, nRowsCT);
        txtDVT = new TextField();
        txtDVTlist.add(txtDVT);
        txtDVT.setPrefWidth(100);
        txtDVT.setEditable(false);
        gridPane.add(txtDVT, 3, nRowsCT);
        TextField txtSoLuong = new TextField();
       txtSoLuongList.add(txtSoLuong);
        txtSoLuong.setPrefWidth(100);
        gridPane.add(txtSoLuong, 4, nRowsCT);
        txtSoLuong.setOnKeyReleased(e->TriGia_Change());
        txtDonGia = new TextField();
        txtDonGiaList.add(txtDonGia);
        txtDonGia.setPrefWidth(100);
        txtDonGia.setEditable(false);
        gridPane.add(txtDonGia, 5, nRowsCT);
        txtDonGia.setOnKeyReleased(e->TriGia_Change());
           TextField txtTriGia = new TextField();
       txtTriGiaList.add(txtTriGia);
        txtTriGia.setPrefWidth(150);
        txtTriGia.setAlignment(Pos.CENTER_RIGHT);
        txtTriGia.setEditable(false);
        gridPane.add(txtTriGia, 6, nRowsCT);
        nRowsCT++; txtSTTList.get(nRowsCT-1).setText(String.valueOf(nRowsCT)); scrollPane.setVvalue(1);


        Kiem_Hop_Le();
    }
    public void  TriGia_Change(){
        float TongTriGia=0;
        for(int i=0; i<nRowsCT; i++) {
            float SoLuong = MyLib.getFloat(txtSoLuongList.get(i).getText());
            float DonGia = MyLib.getFloat(txtDonGiaList.get(i).getText());
            float TriGia = SoLuong * DonGia;
            txtTriGiaList.get(i).setText(String.format("%,12.0f", TriGia));
            TongTriGia += TriGia;
        }
        txtTongTriGia.setText(String.format("%,12.0f", TongTriGia));
        Kiem_Hop_Le();
    }
    public boolean  SHD_HopLe_LucThem(){
        String SHD= txtSoHoaDon.getText();
        try {
            ResultSet rs = kncsdl.Tim_SoHoaDon(SHD);
            boolean HopLe = (!SHD.equals("")) && (rs == null); // Không tìm thấy
            return HopLe;
        } catch (Exception e)
        {
            return false;
        }
    }
    public boolean MSKH_HopLe(){
        return ! MSKH_Luu.equals("");
    }
    public boolean  MSMH_HopLe(){
        boolean HopLe = true;
        List<String> MSMHList = new ArrayList<>();
        for(int i=0; i<nRowsCT; i++) {
            String MSMH = txMSMatHangList.get(i).getText();
            HopLe &= ! MSMH.equals("");
            HopLe &= ! MyLib.isInList(MSMH, MSMHList);
            MSMHList.add(MSMH);
        }
        return HopLe;
    }
    public boolean  SL_HopLe(){
        boolean HopLe=true;
        for(int i=0;i<nRowsCT; i++) {
            HopLe &= MyLib.isInt(txtSoLuongList.get(i).getText());
        }
        return HopLe;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
