package frc.team555.hardware;

import com.ctre.CANTalon;
import frc.team555.hardware.HardwareConfigBase.*;


public class SRX extends CANTalon {

    public SRX(PortID<HardwareConfigBase.CANBus> c) {
        super(c.get());
    }
}

