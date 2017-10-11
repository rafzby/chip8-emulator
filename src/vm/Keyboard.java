package vm;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
    private static final int[] KEY_MAP= {
            KeyEvent.VK_4, // 1
            KeyEvent.VK_5, // 2
            KeyEvent.VK_6, // 3
            KeyEvent.VK_7, // 4
            KeyEvent.VK_R, // 5
            KeyEvent.VK_Y, // 6
            KeyEvent.VK_U, // 7
            KeyEvent.VK_F, // 8
            KeyEvent.VK_G, // 9
            KeyEvent.VK_H, // A
            KeyEvent.VK_J, // B
            KeyEvent.VK_V, // C
            KeyEvent.VK_B, // D
            KeyEvent.VK_N, // E
            KeyEvent.VK_M, // F
    };

    private int currentKeyPressed;


    public Keyboard() {
        currentKeyPressed = 0;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        currentKeyPressed = mapKeycodeToChip8Key(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        currentKeyPressed = 0;
    }

    public int getCurrentKeyPressed() {
        return currentKeyPressed;
    }

    private int mapKeycodeToChip8Key(int keycode) {
        for (int i = 0; i < KEY_MAP.length; i++) {
            if (KEY_MAP[i] == keycode) {
                return i + 1;
            }
        }

        return 0;
    }
}
