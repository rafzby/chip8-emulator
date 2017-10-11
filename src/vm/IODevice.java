package vm;

public interface IODevice {
    void clearDisplay();
    void repaintDisplay();
    void setPixelValue(int position, int value);
    void playSound();
    void stopSound();
    int getPixelValue(int position);
    int getCurrentKeyPressed();
}
