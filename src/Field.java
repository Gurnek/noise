import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Field extends JPanel {
    private OpenSimplexNoise osn = new OpenSimplexNoise();
    private double t = 0;
    private double r;
    private double gr;
    private double b;

    private Field() {
        ActionListener listener = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                t += 0.02;
                repaint();
            }
        };
        Timer timer = new Timer(10, listener);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                this.r = (osn.eval(i * 0.01, j * 0.01, this.t) + 1) / 2;
                r *= 255;
                this.gr = (osn.eval(j * 0.01, i * 0.01, this.t) + 1) / 2;
                gr *= 255;
                this.b = (osn.eval(this.t, i * 0.01, j * 0.01) + 1) / 2;
                b *= 255;
                g.setColor(new Color((int) r, (int) gr, (int) b));
                g.fillRect(2 * i, 2 * j, 4, 4);
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Hello");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Field field = new Field();
        f.add(field);
        f.setSize(800, 800);
        f.setVisible(true);
    }
}
