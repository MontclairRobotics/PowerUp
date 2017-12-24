package frc.team555.hardware;

import com.ctre.CANTalon;

public class SRX extends CANTalon {

    public SRX(PortID<CANBus> c) {
        super(c.get());
    }
}

