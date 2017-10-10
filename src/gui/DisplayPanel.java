package gui;

import vm.Display;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private static final int PIXEL_SIZE = 10;

    private Display display;

    public DisplayPanel(Display display) {
        this.display = display;
    }

    @Override
    public void paint(Graphics graphics) {
        byte[] pixelArray = display.getPixelArray();

        for (int i = 0; i < pixelArray.length; i++) {
            Color pixelColor = pixelArray[i] == 0 ? Color.BLACK : Color.WHITE;
            graphics.setColor(pixelColor);

            int positionX = i % 64;
            int positionY = (int) Math.floor(i/64);

            graphics.fillRect(positionX * PIXEL_SIZE, positionY * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
        }
    }
}
