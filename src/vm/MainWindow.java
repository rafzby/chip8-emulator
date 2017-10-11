package vm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MainWindow extends JFrame {
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 320;

    public MainWindow(DisplayPanel displayPanel, KeyListener keyListener) {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();

        int width = WINDOW_WIDTH + getInsets().right + getInsets().left;
        int height = WINDOW_HEIGHT + getInsets().top + getInsets().bottom;
        setPreferredSize(new Dimension(width, height));

        setLayout(new BorderLayout());

        add(displayPanel, BorderLayout.CENTER);
        pack();

        addKeyListener(keyListener);

        setTitle("Chip-8 Emulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);

        try {
           Image icon = ImageIO.read(ClassLoader.getSystemResource("images/icon.png"));
           setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
