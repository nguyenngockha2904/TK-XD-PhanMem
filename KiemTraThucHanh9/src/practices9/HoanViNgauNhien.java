package practices9;
import java.sql.*;
import java.util.Date;
public class HoanViNgauNhien {

    public static void main(String[] args) throws SQLException {

        final String fileName="MyData";
        KetNoiCSDL kncsdl =new KetNoiCSDL(fileName);
        String HoanVi =TaoHoanVi();
        System.out.println(HoanVi);
        boolean HopLe=!kncsdl.Tim(HoanVi);
        if(HopLe)
        {
            Date NgayGio=new Date();
            kncsdl.Them(HoanVi,NgayGio);
            System.out.println(NgayGio);
        }else{}
        kncsdl.Dong();
    }
    private static String TaoHoanVi(){
        String s="";
        String s1="123456789";
        int l;
        while ((l=s1.length())>0)
        {
            int pos=  (int)(Math.random() * l);
            s+=s1.charAt(pos);
            if (pos>0)
            {
                s1=  s1.substring(0,pos) + s1.substring(pos+1);
            }else
                s1= s1.substring(1);
        }
        return s;
    }
}
