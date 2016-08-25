import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * IDE: Ctrl+Alt+T包裹代码
 * IDE: Ctrl+o重写父类代码
 * Java: 线程调用repaint(), repaint()调用update()调用paint(), 内部类最好
 * Created by xuan on 2016/8/22.
 */
public class TankClient extends Frame{
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
//    Tank enemyTank = new Tank(100, 100, false, this);
    List<Missile> missiles = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Tank> myTanks = new ArrayList<>();
    Image offScreenImage = null;
//    Explode e = new Explode(150, 150, this);

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
            for (int i = 0; i < myTanks.size(); i++) {
                Tank myTank = myTanks.get(i);
                myTank.keyPressed(e);
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            for (int i = 0; i < myTanks.size(); i++) {
                Tank myTank = myTanks.get(i);
                myTank.keyReleased(e);
            }

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
        g.drawString("missiles: " + missiles.size(),100, 300);
        g.drawString("tanks: " + tanks.size(), 100, 400);
        g.drawString("explodes: " + explodes.size(), 100, 500);
        g.drawString("mytanks: " + myTanks.size(), 100, 600);
        for (int i = 0; i < myTanks.size(); i++) {
            Tank myTank = myTanks.get(i);
            if (!myTank.isLive()) myTanks.remove(myTank);
            myTank.draw(g);
        }
//        enemyTank.draw(g);
//        e.draw(g);
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }
        for (int i = 0; i < tanks.size(); i++) {
            Tank enemyTank = tanks.get(i);
            if (!enemyTank.isLive()) tanks.remove(enemyTank);
            else enemyTank.draw(g);
        }
        for (int i = 0; i < missiles.size(); i++) { //此处用foreach会出错，迭代修改
            Missile m =missiles.get(i);
            if (!m.isLive())
                missiles.remove(m);
            else {
                m.draw(g);
                if (m.hitTanks(tanks) || m.hitTanks(myTanks)) {
                    missiles.remove(m);
                }
            }
        }
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
        for (int i = 0; i < 10; i++) {
            tanks.add(new Tank(100+50*i, 50+50*i, false, this));
        }
        myTanks.add(new Tank(400, 400, true, this));
    }
    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
//        tc.new PaintThread().start();
    }
}
