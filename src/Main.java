import javax.swing.*;

/**
 * Created by clarissa_briasco on 3/16/17.
 */
public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Stick Hero!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1200, 800); //(x, y, w, h)

        Panel panel = new Panel();
        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);

    }

}