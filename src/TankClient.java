import java.awt.*;

/**
 * Created by xuan on 2016/8/22.
 */
public class TankClient extends Frame{
    public void launchFrame() {
        setLocation(200, 200);
        setSize(800, 600);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }
}
