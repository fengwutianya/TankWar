import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by xuan on 2016/8/23.
 */
public class Tank {
    private int x, y;
    private boolean bL = false, bR = false, bU = false, bD = false;
    enum Direction {L, LU, U, RU, R, RD, D, LD, STOP}
    private Direction dir = Direction.STOP;
    public static final int XSPEED = 15;
    public static final int YSPEED = 15;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
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
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
        g.setColor(c);

        move();
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
}