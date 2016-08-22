import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by xuan on 2016/8/22.
 */
public class TankClient extends Frame{
    public void launchFrame() {
        setLocation(200, 200);
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
    }
    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }
}
