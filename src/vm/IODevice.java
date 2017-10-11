package vm;

public interface IODevice {
    void clearDisplay();
    void repaintDisplay();
    void setPixelValue(int position, int value);
    int getPixelValue(int position);
    int getCurrentKeyPressed();
}
