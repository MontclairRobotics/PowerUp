package frc.team555.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.*;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;
import org.opencv.core.Mat;


public class Steamworks  extends SprocketRobot{
    
    // DT MOTOR PORTS
    public final int frontLeftID  = 3;
    public final int frontRightID = 1;
    public final int backLeftID   = 4;
    public final int backRightID  = 2;

    // CLIMB MOTOR PORTS
    public final int climbLeftID  = 6;
    public final int climbRightID = 7;

    // JOYSTICK PORTS
    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber   = 1;

    // LIMIT SWITCH PORTS
    public final int openLeftID   = 1;
    public final int openRightID  = 6;
    public final int closeLeftID  = 0;
    public final int closeRightID = 7;

    //INTAKE MOTOR PORTS
    public final int intakeLeftID  = 0;
    public final int intakeRightID = 5;


    // BUTTON IDS
    public final int intakeButtonID       = 1; //"LSHIFT" on Keyboard
    public final int gLockID              = 3;
    public final int climbButtonID        = 4;

    // SPEEDS
    public final double intakePow = 0.4;
    public final double climbPow  = 0.5;


    Motor intakeRight;
    Motor intakeLeft;

    // Intake
    DigitalInput openLeftSwitch;
    DigitalInput closeLeftSwitch;
    DigitalInput openRightSwitch;
    DigitalInput closeRightSwitch;

    //climber
    Motor climbLeft;
    Motor climbRight;

    WPI_TalonSRX drivetrainFL;
    WPI_TalonSRX drivetrainFR;
    WPI_TalonSRX drivetrainBL;
    WPI_TalonSRX drivetrainBR;


    // Control Devices
    PowerDistributionPanel pdp;

    GyroLock gLock;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);
        pdp = new PowerDistributionPanel();


        //INTAKE
        intakeRight = new Motor(new WPI_TalonSRX(5));
        intakeRight.setInverted(true);
        intakeLeft = new Motor(new VictorSP(0));
        intakeLeft.setInverted(true);
        openLeftSwitch = new DigitalInput(1);
        closeLeftSwitch = new DigitalInput(0);
        openRightSwitch = new DigitalInput(6);
        closeRightSwitch = new DigitalInput(7);

        //DRIVETRAIN
        drivetrainFL = new WPI_TalonSRX(frontLeftID);
        drivetrainFR = new WPI_TalonSRX(frontRightID);
        drivetrainBL = new WPI_TalonSRX(backLeftID);
        drivetrainBR = new WPI_TalonSRX(backRightID);



        // climb motors
        climbLeft  = new Motor(new WPI_TalonSRX(climbLeftID));
        climbRight = new Motor(new WPI_TalonSRX(climbRightID));
        climbLeft.setInverted(true);
        climbRight.setInverted(true);
        
        // intake limit switches
        openLeftSwitch   = new DigitalInput(openLeftID);
        closeLeftSwitch  = new DigitalInput(closeLeftID);
        openRightSwitch  = new DigitalInput(openRightID);
        closeRightSwitch = new DigitalInput(closeRightID);
    
        // ========= DRIVETRAIN ========= //
        drivetrainFL = new WPI_TalonSRX(frontLeftID);
        drivetrainFR = new WPI_TalonSRX(frontRightID);
        drivetrainBL = new WPI_TalonSRX(backLeftID);
        drivetrainBR = new WPI_TalonSRX(backRightID);
        


        
        // Drive train setup
        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,-1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0, 1), new Motor(drivetrainFR), new Motor(drivetrainBR));
        
        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);

        //Drive train control
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));

        
        // Create drive train
        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

        // ========= INTAKE ========= //
        Button intake = new JoystickButton(driveStick, intakeButtonID);
        
        // Intake Open
        intake.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(openLeftSwitch.get()){intakeLeft.set(0);}else {intakeLeft.set(intakePow);}
                if(!openRightSwitch.get()){intakeRight.set(0);}else {intakeRight.set(intakePow);}
            }
        });
        // Intake Close
        intake.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(closeLeftSwitch.get()){intakeLeft.set(0);}else {intakeLeft.set(-intakePow);}
                if(!closeRightSwitch.get()){intakeRight.set(0);}else {intakeRight.set(-intakePow);}
            }
        });

        // ========= CLIMB  ========= //
        Button climber = new JoystickButton(driveStick, climbButtonID);

        // Climb True
        climber.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                climbLeft.set(climbPow);
            }
        });
        // Climb Off
        climber.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
                climbLeft.set(0);
                climbRight.set(0);
            }
        });


    }

    @Override
    public void update() {
        SmartDashboard.putNumber("Intake Power", intakePow);
        SmartDashboard.putNumber("Climb Power", climbPow);
    }

}
