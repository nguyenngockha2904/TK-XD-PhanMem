package SongHi;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable {
    public static int SoLan=0;
    Thread thread=null;
    JFrame f=new JFrame();
    MyList mList=null;
    public MyPanel(MyList ml)
    {
        f.setSize(400,400);
        mList =ml;
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.add(this);
    }
    @Override
    public void paint(Graphics g)
    {
        try {
            mList.draw(g);

        }
        catch(InterruptedException ex) {

            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);

        }
        SoLan =SoLan+1;

    }
    public void start()
    {
        if(thread==null)
        {
            thread= new Thread(this);
            thread.start();

        }
    }
    public void stop()
    {
        thread =null;
    }
    @Override
    public void run() {
        while (thread!=null)
        {
            try
            {
                Thread.sleep(50);
            }
            catch(InterruptedException e)
            {

            }
            repaint();

        }
        thread =null;
    }
    public void update(Graphics g)
    {
       f.paint(g);
    }

}
