import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
public class Clock extends JPanel implements Runnable {
    Thread thread=null;
    SimpleDateFormat formatter =new SimpleDateFormat( "s", Locale.getDefault());
    Date curreDate;
    int xcenter=100;
    int ycenter=100;
    int lastxs=0,lastys=0,lastxm=0,lastym=0,lastxh=0,lastyh=0;
    private void drawSture(Graphics g)
    {
        g.setFont(new Font("Times New Roman",Font.PLAIN,14));
        g.setColor(Color.blue);
        g.drawOval( xcenter-50, ycenter-50, 100, 100);
        g.setColor(Color.darkGray);
        g.drawString("9", xcenter-50, ycenter+3);
        g.drawString("3", xcenter+40, ycenter+3);
        g.drawString("12" , xcenter-7, ycenter-37);
        g.drawString("6", xcenter-3, ycenter+45);

    }
    public void paint(Graphics g)
    {
        int  xhour, yhour, xminute, yminute, xsecond, ysecond, hour, minute,second;
        drawSture(g);
        curreDate= new Date();
        formatter.applyPattern("s");
        second= Integer.parseInt(formatter.format(curreDate));
        formatter.applyPattern("m");
        minute=Integer.parseInt(formatter.format(curreDate));
        formatter.applyPattern("h");
        hour= Integer.parseInt(formatter.format(curreDate));
        xhour = (int)(Math.cos((hour*30+minute/2)*3.14f/180-3.14f/2)*30+xcenter);
        yhour =  (int)(Math.sin((hour*30+minute/2)*3.14f/180-3.14f/2)*30+ycenter);
        xminute =  (int)(Math.cos(minute*3.14f/30-3.14f/2)*40+xcenter);
        yminute = (int)(Math.sin(minute*3.14f/30-3.14f/2)*40+ycenter);
        xsecond= (int)(Math.cos(second*3.14f/30-3.14f/2)*45+xcenter);
        ysecond= (int)(Math.sin(second*3.14f/30-3.14f/2)*45+ycenter);
        g.setColor(Color.black);

        if(xsecond!=lastxs||ysecond!=lastys) {

            g.drawLine(xcenter, ycenter, xsecond,ysecond);
        }

        if (xminute!=lastxm||yminute!=lastym)
        {
            g.drawLine(xcenter, ycenter-1, xminute,yminute );
            g.drawLine(xcenter-1, ycenter, xminute,yminute);
        }
        if(xhour!=lastxh||yhour!=lastyh)
        {
            g.drawLine(xcenter, ycenter-1, xhour,yhour);
            g.drawLine(xcenter-1, ycenter, xhour,yhour);
        }
        }
    public  void start(){
            if (thread == null) {
                thread = new Thread(this);
                thread.start();
            }

        }
    public void stop(){
        thread = null;
    }
    @Override
    public void run() {
        while (thread != null) {
            try {
                Thread.sleep(500); // 600 mili-second
            }
            catch (InterruptedException e)
            {

            }
            repaint();

        }
        thread = null;
    }
    public void update(Graphics g)
    {
        paint(g);

    }
}
