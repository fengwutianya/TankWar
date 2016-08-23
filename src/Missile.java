import java.awt.*;

/**
 * Created by xuan on 2016/8/23.
 */
public class Missile {
    private int x, y;
    public static final int XSPEED = 20;
    public static final int YSPEED = 20;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    Tank.Direction dir;

    public Missile(int y, int x, Tank.Direction dir) {
        this.y = y;
        this.x = x;
        this.dir = dir;
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
    }
}
