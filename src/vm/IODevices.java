package vm;

public interface IODevices {
    void clearDisplay();
    void repaintDisplay();
    void setPixelValue(int position, int value);
    int getPixelValue(int position);
    int getCurrentKeyPressed();
}
