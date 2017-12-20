package frc.team555.robot;

        import com.ctre.CANTalon;
        import edu.wpi.first.wpilibj.*;

public class Steamworks extends IterativeRobot {

    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;

    JoystickControls joystick;
    Tank drivetrain;

    @Override
    public void robotInit() {
        drivetrain = new Tank(drivetrainFL, drivetrainFR, drivetrainBL, drivetrainBR);
        drivetrain.init(3,1,4,2);
        joystick = new JoystickControls(0,1);
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.arcadeDrive(joystick.driveStick,true);
    }

}
