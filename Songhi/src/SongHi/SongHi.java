package SongHi;

public class SongHi {
    private static MyList a=new MyList();
    public static void main (String[] args) {

        a.addData();
        a.printOut();
        MyPanel mp=new MyPanel(a);
        mp.start();
    }

}