import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by xuan on 2016/8/23.
 */
public class Tank {
    private int x, y;
    public static final int SPEED = 15;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
        g.setColor(c);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) x += SPEED;
        else if (key == KeyEvent.VK_LEFT) x -= SPEED;
        else if (key == KeyEvent.VK_UP) y -= SPEED;
        else if (key == KeyEvent.VK_DOWN) y += SPEED;
    }
}
