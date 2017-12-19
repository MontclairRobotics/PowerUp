package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;

public class Robot extends IterativeRobot {

    CANTalon drivetrainFrontLeft;
    CANTalon drivetrainFrontRight;
    CANTalon drivetrainBackLeft;
    CANTalon drivetrainBackRight;

    JoystickControls joystick;
    RobotDrive drive;

    @Override
    public void robotInit() {
        drivetrainFrontLeft = new CANTalon(0);
        drivetrainFrontRight = new CANTalon(1);
        drivetrainBackLeft = new CANTalon(2);
        drivetrainBackRight = new CANTalon(3);

        drivetrainFrontLeft.setSafetyEnabled(true);
        drivetrainFrontRight.setSafetyEnabled(true);
        drivetrainBackLeft.setSafetyEnabled(true);
        drivetrainBackRight.setSafetyEnabled(true);

        drive = new RobotDrive(drivetrainFrontLeft, drivetrainFrontRight, drivetrainBackLeft, drivetrainBackRight);
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void teleopPeriodic() {
        drive.arcadeDrive(joystick.driveStick);
    }

}
