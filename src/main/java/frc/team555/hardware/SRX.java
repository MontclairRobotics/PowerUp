package frc.team555.hardware;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SRX extends WPI_TalonSRX {

    public SRX(PortID<CANBus> c) {
        super(c.get());
    }
}

