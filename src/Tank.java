import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by xuan on 2016/8/23.
 */
public class Tank {
    private int x, y;
    private boolean bL = false, bR = false, bU = false, bD = false;
    enum Direction {L, LU, U, RU, R, RD, D, LD, STOP}
    private Direction dir = Direction.STOP;
    private Direction gunDir = Direction.R;
    public static final int XSPEED = 15;
    public static final int YSPEED = 15;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private TankClient tc;
    private boolean good;
    private boolean live = true;
    private static Random r = new Random();
    private int step = r.nextInt(12) + 3;

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isGood() {
        return good;
    }

    public boolean isLive() {
        return live;
    }

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }
    public Tank(int x, int y, boolean good, TankClient tc) {
        this(x, y, good);
        this.tc = tc;
    }

    void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
        if (x < 0) x = 0;
        if (y < 25) y = 25;
        if (x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

        if (!good) {
            Direction[] dirs = Direction.values();
            if (step == 0) {
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
                step = r.nextInt(12) + 3;
            }
            step --;
        }
    }

    public void draw(Graphics g) {
        if (!live) return;
        Color c = g.getColor();
        if (good) g.setColor(Color.RED);
        else g.setColor(Color.BLUE);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        switch (gunDir) {
            case L:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
                break;
            case LU:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
                break;
            case U:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
                break;
            case RU:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
                break;
            case R:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2);
                break;
            case RD:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
                break;
            case D:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
                break;
            case LD:
                g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
                break;
        }

        move();
//        g.drawString("missiles: " + tc.missiles.size(), 60, 60);
//        g.drawString("explodes" + tc.explodes.size(), 60, 80);
//        System.out.println("Tank" + x + " " + y);
        int rn = r.nextInt(40);
        if (rn > 38 && !good) fire();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {

            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locationDirection();
        if (dir != Direction.STOP) gunDir = dir;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
                fire();
//                System.out.println("Tank" + x + " " + y);
                break;
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        locationDirection();
    }

    void locationDirection() {
        if (bL && !bU && !bR && !bD)            dir = Direction.L;
        else if (bL && bU && !bR && !bD)        dir = Direction.LU;
        else if (!bL && bU && !bR && !bD)       dir = Direction.U;
        else if (!bL && bU && bR && !bD)        dir = Direction.RU;
        else if (!bL && !bU && bR && !bD)       dir = Direction.R;
        else if (!bL && !bU && bR && bD)        dir = Direction.RD;
        else if (!bL && !bU && !bR && bD)       dir = Direction.D;
        else if (bL && !bU && !bR && bD)        dir = Direction.LD;
        else                                    dir = Direction.STOP;

    }

    public void fire() {
        if (!this.live) return;
        int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
        int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
        tc.missiles.add(new Missile(x, y, good, gunDir, tc));
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, Tank.WIDTH, Tank.HEIGHT);
    }
}
