package vm;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
    private static final int[] KEY_MAP= {
            KeyEvent.VK_1, // 1
            KeyEvent.VK_2, // 2
            KeyEvent.VK_3, // 3
            KeyEvent.VK_Q, // 4
            KeyEvent.VK_W, // 5
            KeyEvent.VK_E, // 6
            KeyEvent.VK_A, // 7
            KeyEvent.VK_S, // 8
            KeyEvent.VK_D, // 9
            KeyEvent.VK_Z, // A
            KeyEvent.VK_C, // B
            KeyEvent.VK_4, // C
            KeyEvent.VK_R, // D
            KeyEvent.VK_F, // E
            KeyEvent.VK_V, // F
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
