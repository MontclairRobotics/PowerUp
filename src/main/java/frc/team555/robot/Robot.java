package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.utils.PID;

public class Robot extends IterativeRobot {

    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;

    Tank drivetrain;
    JoystickControls joystick;

    @Override
    public void robotInit() {
        drivetrain = new Tank(drivetrainFL, drivetrainFR, drivetrainBL, drivetrainBR);
        drivetrain.init(0,1,2,3);
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.arcadeDrive(joystick.driveStick);
    }

}
