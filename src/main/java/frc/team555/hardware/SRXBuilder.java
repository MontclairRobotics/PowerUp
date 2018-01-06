package frc.team555.hardware;

public class SRXBuilder {
    private CANBus bus;
    public SRXBuilder(CANBus canBus) {
        this.bus = canBus;
    }
    public SRX build(DeviceEnum device) {
        return new SRX(bus.get(device));
    }
}

