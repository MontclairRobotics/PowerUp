package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;

public class Mecanum {

    SpeedController drivetrainFL;
    SpeedController drivetrainFR;
    SpeedController drivetrainBL;
    SpeedController drivetrainBR;

    public Mecanum(SpeedController frontL, SpeedController frontR, SpeedController backL, SpeedController backR){
        this.drivetrainFL = frontL;
        this.drivetrainFR = frontR;
        this.drivetrainBL = backL;
        this.drivetrainBR = backR;
    }


}
