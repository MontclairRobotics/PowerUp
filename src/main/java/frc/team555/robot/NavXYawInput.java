package frc.team555.robot;

import org.montclairrobotics.sprocket.utils.Input;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavXYawInput extends AHRS implements Input<Double>{

    public NavXYawInput(Port id) {
        super(id);
    }

    @Override
    public Double get() {
		/*SmartDashboard.putNumber("Roll",super.getRoll());
		SmartDashboard.putNumber("Pitch",super.getPitch());
		SmartDashboard.putNumber("Yaw",super.getYaw());*/
        //SmartDashboard.putNumber("anything", super.)
        return new Double(super.getYaw());
    }

}