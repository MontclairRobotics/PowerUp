package frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.montclairrobotics.sprocket.utils.Input;

public class NavXInput extends AHRS implements Input<Double> {

    public NavXInput(SPI.Port id) {
        super(id);
    }



    @Override

    public Double get() {
        return new Double(super.getYaw());
    }



}