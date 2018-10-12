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
                t += 1;
                repaint();
            }
        };
        Timer timer = new Timer(10, listener);
        timer.start();
    }

    private double fbm(int numIters, double x, double y, double z, double persistence, double scale, double low, double high) {
        double maxAmp = 0;
        double amp = 1;
        double freq = scale;
        double noise = 0;

        for (int i = 0; i < numIters; i++) {
            noise += osn.eval(x * freq, y * freq, z * freq) * amp;
            maxAmp += amp;
            amp *= persistence;
            freq *= 2;
        }

        noise = noise / maxAmp;
        noise = noise * (high - low) / 2 + (high + low) / 2;

        return noise;
    }
    public void paintComponent(Graphics g) {
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                this.r = this.fbm(2, i, j, this.t, 2, 0.01, 0, 255);
                this.gr = this.fbm(2, j, i, this.t, 2, 0.01, 0, 255);
                this.b = this.fbm(2, this.t, i, j, 2, 0.01, 0, 255);
                g.setColor(new Color((int) r, (int) gr, (int) b));
                g.fillRect(2 * i, 2 * j, 2, 2);
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
