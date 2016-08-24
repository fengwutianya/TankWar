import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by xuan on 2016/8/23.
 */
public class Missile {
    private int x, y;
    public static final int XSPEED = 30;
    public static final int YSPEED = 30;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    Tank.Direction dir;
    private boolean live = true;
    private TankClient tc = null;

    public boolean isLive() {
        return live;
    }

    public Missile(int x, int y, Tank.Direction dir, TankClient tc) {
        this.y = y;
        this.x = x;
        this.dir = dir;
        this.tc = tc;
//        System.out.println(x + " " + y + " " + dir);
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.black);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    public void move() {
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
        }
        if (x<0 || y<0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT)
            live = false;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, Missile.WIDTH, Missile.HEIGHT);
    }

    public boolean hitTank(Tank t) {
        if (this.getRect().intersects(t.getRect())) {
            t.setLive(false);
            tc.explodes.add(new Explode(x, y, tc));
            return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            if (hitTank(tanks.get(i))) {
                return true;
            }
        }
        return false;
    }
}
