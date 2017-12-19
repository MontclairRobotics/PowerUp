//package frc.team555.robot;
//
//        import com.ctre.CANTalon;
//        import edu.wpi.first.wpilibj.*;
//
//public class Steamworks extends IterativeRobot {
//
//    CANTalon drivetrainFrontLeft;
//    CANTalon drivetrainFrontRight;
//    CANTalon drivetrainBackLeft;
//    CANTalon drivetrainBackRight;
//
//    VictorSP intakeLeft;
//    CANTalon intakeRight;
//
//    DigitalInput sensorLimitOpenLeft;
//    DigitalInput sensorLimitCloseLeft;
//    DigitalInput sensorLimitOpenRight;
//    DigitalInput sensorLimitCloseRight;
//
//    JoystickControls joystick;
//    RobotDrive drive;
//
//    @Override
//    public void robotInit() {
//
//        //DRIVETRAIN
//        drivetrainFrontLeft = new CANTalon(3);
//        drivetrainFrontRight = new CANTalon(1);
//        drivetrainBackLeft = new CANTalon(2);
//        drivetrainBackRight = new CANTalon(4);
//
//        drivetrainFrontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//        drivetrainFrontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//        drivetrainBackLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//        drivetrainBackRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//
//        //INTAKE
//        intakeLeft = new VictorSP(0);
//        intakeRight = new CANTalon(5);
//
//        sensorLimitOpenLeft = new DigitalInput(1);
//        sensorLimitOpenRight = new DigitalInput(2);
//        sensorLimitCloseLeft = new DigitalInput(3);
//        sensorLimitCloseRight = new DigitalInput(4);
//    }
//
//    @Override
//    public void teleopInit() {
//    }
//
//    @Override
//    public void robotPeriodic() {
//    }
//
//    @Override
//    public void teleopPeriodic() {
//        drive.arcadeDrive(joystick.driveStick);
//    }
//
//}
