package frc.team555.hardware;

public class CANBus extends Bus<CANBus> {
    public CANBus() {
        super(1, 100); // TODO: check can bus address limits
    }
}
