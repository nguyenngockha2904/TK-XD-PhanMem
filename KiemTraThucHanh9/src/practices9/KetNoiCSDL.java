package practices9;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
public class KetNoiCSDL {
    private Connection conn = null;

    public KetNoiCSDL(String dbfilename) {
        String url = "jdbc:sqlserver://localhost:1433;database=" + dbfilename+";user=sa;password =123";
        try {
            conn = DriverManager.getConnection(url);
           // System.out.println("thanh cong!");
        } catch (Exception e) {
            System.out.println("loi~ jdbc");
            e.printStackTrace();
        }
    }
    public void Dong() {
        try {
            if (conn == null)
                conn.close();
        } catch (SQLException e) {
        }
    }
    public boolean Tim(String Khoa) throws SQLException{
        Statement stmt=conn.createStatement();
        String sql="select *from NgauNhien where HoanVi='"+Khoa+"'";
        ResultSet rs=stmt.executeQuery(sql);
        int i=0;
        while(rs.next())
        {
            i++;
        }
        return i>0;
    }
    public void Them(String Khoa ,Date dt) throws SQLException
    {
        SimpleDateFormat formatter= new SimpleDateFormat( "yyyy-MM-dd HH:m:s" ,Locale.getDefault());
        String TemThoiGian=formatter.format(dt);
        System.out.println(TemThoiGian);
        Statement stmt=conn.createStatement();
        String sql="insert into NgauNhien values('"+Khoa+"','"+TemThoiGian+"')";
        stmt.execute(sql);
        System.out.println("them thanh cong");
    }
}
