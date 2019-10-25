package sample;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class KetNoiCSDL {
    Connection conn=null;

    public KetNoiCSDL(String dbfileName) {
        String url="jdbc:sqlserver://localhost:1433;"
                + "database="+dbfileName+";"
                + "user=sa;"
                + "password=123;" ;
        try
        {
            conn = DriverManager.getConnection(url);
        }
        catch (Exception e)
        {
            System.out.println("Loi sai jdbc: " + e + "\n" + dbfileName);
        }
    }

    public void Them_ChiTietHoaDon(String text, String text1, float parseFloat, float parseFloat1) {
        String sql= "insert into ChiTietHoaDon values('"+text+"','"+text1+"',"+parseFloat+","+parseFloat1+")";
        try {
           int row= conn.prepareStatement(sql).executeUpdate();
            System.out.println("thanh cong "+row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Them_HoaDon(String text, String toString, String mskh_luu) {
        String sql="insert into HoaDon values ('"+text+"','"+toString+"','"+mskh_luu+"')";
        try {
            int row=conn.prepareStatement(sql).executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DocKhachHang(ComboBox<String> cboKhachHang) {
        String sql="select MSKhachHang from KhachHang";

        ResultSet rs= null;
        try {
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next())
            {
                cboKhachHang.getItems().addAll(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DocMatHang(ComboBox<String> cboMatHang) {
        String sql="select MSMatHang from MatHang";
            try {
                ResultSet rs= conn.createStatement().executeQuery(sql);
                while (rs.next())
                {
                    cboMatHang.getItems().addAll(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public ResultSet Tim_MSKhachHang(String mskh_luu) {
        String sql="select * from KhachHang where MSKhachHang='"+mskh_luu+"'";
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet Tim_MSMH(String msmh){
        String sql="select * from MatHang where MSMatHang='"+msmh+"'";
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet Tim_SoHoaDon(String shd)  {
        String sql="select * from HoaDon where SoHoaDon='"+shd+"'";
        try {
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
