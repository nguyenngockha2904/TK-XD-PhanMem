package SongHi;

public class MyLine {
    private final int[] x;
    private final int y;
    public int[] getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public MyLine(int[] x,int y)
    {
        this.x=new int[x.length];
        for (int i=0;i<x.length;i++)
        {
            this.x[i]=x[i];
        }
        this.y=y;
    }
}
