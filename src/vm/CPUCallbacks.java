package vm;

public interface CPUCallbacks {
    void onDisplayRepaint();
    void onDisplayClear();
    void onDisplaySetPixelValue(int position, int value);
    int onDisplayGetPixelValue(int position);
}
