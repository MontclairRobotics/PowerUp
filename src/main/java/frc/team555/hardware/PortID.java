package frc.team555.hardware;

public class PortID<T> {
    private int channel;
    public PortID(int c) {
        channel=c;
    }
    public int get() {
        return channel;
    }
}