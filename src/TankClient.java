import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * IDE: Ctrl+Alt+T包裹代码
 * IDE: Ctrl+o重写父类代码
 * Java: 线程调用paint(), paint()调用update()调用repaint(), 内部类最好
 * Created by xuan on 2016/8/22.
 */
public class TankClient extends Frame{
    int x = 50; int y = 50;
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(800, 600);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, 800, 600);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    //    private class PaintThread extends Thread {
//        @Override
//        public void run() {
//            while (true) {
//                repaint();
//                try {
//                    Thread.sleep(150);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    private class PaintThread implements Runnable {
    @Override
    public void run() {
        while (true) {
                repaint();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
        g.setColor(c);
        y += 5;
        x += 5;
    }

    public void launchFrame() {
        setLocation(200, 200);
        setBackground(Color.GREEN);
        setSize(800, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setTitle("TankWar");
        setResizable(false);
        setVisible(true);
        new Thread(new PaintThread()).start();
    }
    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
//        tc.new PaintThread().start();
    }
}
