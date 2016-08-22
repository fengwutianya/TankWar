import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * IDE: Ctrl+Alt+T包裹代码
 * IDE: Ctrl+o重写父类代码
 * Java: 线程调用paint(), paint()调用update()调用repaint(), 内部类最好
 * Created by xuan on 2016/8/22.
 */
public class TankClient extends Frame{
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    int x = 50; int y = 50;
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
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
    //实现接口需要完成各种函数，而继承adapter可以只关心自己需要的那个函数
    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println("loaded!");
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) x += 5;
            else if (key == KeyEvent.VK_LEFT) x -= 5;
            else if (key == KeyEvent.VK_UP) y -= 5;
            else if (key == KeyEvent.VK_DOWN) y += 5;
        }
    }
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
    }

    public void launchFrame() {
        setLocation(200, 200);
        setBackground(Color.GREEN);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyMonitor());
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
