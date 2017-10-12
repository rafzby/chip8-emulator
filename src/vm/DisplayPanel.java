package vm;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private static final int PIXEL_SIZE = 10;

    private byte[] pixelArray;


    public DisplayPanel(byte[] pixelArray) {
        this.pixelArray = pixelArray;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics offGraphics;
        Image offScreenImage = null;
        Dimension d = size();
        offScreenImage = createImage(d.width, d.height);
        offGraphics = offScreenImage.getGraphics();
        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, d.width, d.height);
        offGraphics.setColor(getForeground());
        for (int i = 0; i < pixelArray.length; i++) {
            Color pixelColor = pixelArray[i] == 0 ? Color.BLACK : Color.WHITE;
            offGraphics.setColor(pixelColor);

            int positionX = i % 64;
            int positionY = (int) Math.floor(i/64);

            offGraphics.fillRect(positionX * PIXEL_SIZE, positionY * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
        }
        graphics.drawImage(offScreenImage, 0, 0, this);
    }
}
