package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;

public class PowerUpRobot extends SprocketRobot {

    Hardware hardware;
    DriveTrain dt;

    @Override
    public void robotInit(){
        Hardware.init();
        Hardware.motorDriveBL.get();
    }
}
