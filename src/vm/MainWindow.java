package vm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainWindow extends JFrame {
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 320;
    private DisplayPanel displayPanel;
    private KeyListener keyListener;
    private JMenuBar menuBar;
    private JLabel welcomeLabel;
    private VirtualMachineController vmr;

    public MainWindow(VirtualMachineController vmr) {
        this.vmr = vmr;
    }

    public void setup(DisplayPanel displayPanelParam, KeyListener keyListenerParam) {
        this.displayPanel = displayPanelParam;
        this.keyListener = keyListenerParam;
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        int width = WINDOW_WIDTH + getInsets().right + getInsets().left;
        int height = WINDOW_HEIGHT + getInsets().top + getInsets().bottom;
        setPreferredSize(new Dimension(width, height));

        setLayout(new BorderLayout());

        /* Menu bar */
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                openFileChooser(ev);
            };
        });
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            };
        });
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        /* In case we got no game running we display a simple label */
        if(displayPanel != null && keyListener != null) {
            add(displayPanel, BorderLayout.CENTER);
            addKeyListener(keyListener);
        } else {
            welcomeLabel = new JLabel("Click to Open a ROM File or Press CTRL+O", SwingConstants.CENTER);
            Font font = welcomeLabel.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            welcomeLabel.setFont(font.deriveFont(attributes));
            welcomeLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    openFileChooser(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
                }
            });
            add(welcomeLabel);
        }
        pack();

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

    public void cleanDisplayPanel() {
        ArrayList<Object> removeQueue = new ArrayList<Object>(){
            {
                add(welcomeLabel);
                add(displayPanel);
                add(keyListener);
            }
        };
        for(Object obj:removeQueue){
            if(obj != null && obj instanceof Component) {
                remove((Component) obj);
            }
            if(obj != null && obj instanceof KeyListener){
                removeKeyListener((KeyListener) obj);
            }
        }
    }

    private void openFileChooser(ActionEvent ev) {
        JFileChooser fileChooser = new JFileChooser();
        int n = fileChooser.showOpenDialog((Component) ev.getSource());
        if(n == JFileChooser.APPROVE_OPTION){
            String romPath = fileChooser.getSelectedFile().getAbsolutePath();
            vmr.setRunner(MainWindow.this,romPath);
        }
    }
}
