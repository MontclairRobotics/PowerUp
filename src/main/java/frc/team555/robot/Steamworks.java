package frc.team555.robot;

import com.ctre.CANTalon;
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
import frc.team555.robot.NavXYawInput;
import frc.team555.robot.MathAlgorithms;

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

    // Intake
    DigitalInput openLeftSwitch;
    DigitalInput closeLeftSwitch;
    DigitalInput openRightSwitch;
    DigitalInput closeRightSwitch;
    Motor intakeRight;
    Motor intakeLeft;

    //climber
    Motor climbLeft;
    Motor climbRight;

    // Drive Train Motors
    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;


    // Control Devices
    PowerDistributionPanel pdp;
    MathAlgorithms mathAlgorithms;
    NavXYawInput navX;
    GyroLock gLock;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);
        pdp = new PowerDistributionPanel();
        
        // intake motors
        intakeRight = new Motor(new CANTalon(intakeRightID));
        intakeLeft = new Motor(new VictorSP(intakeLeftID));
        intakeLeft.setInverted(true);
        intakeRight.setInverted(true);

        // climb motors
        climbLeft  = new Motor(new CANTalon(climbLeftID));
        climbRight = new Motor(new CANTalon(climbRightID));
        climbLeft.setInverted(true);
        climbRight.setInverted(true);
        
        // intake limit switches
        openLeftSwitch   = new DigitalInput(openLeftID);
        closeLeftSwitch  = new DigitalInput(closeLeftID);
        openRightSwitch  = new DigitalInput(openRightID);
        closeRightSwitch = new DigitalInput(closeRightID);
    
        // ========= DRIVETRAIN ========= //
        drivetrainFL = new CANTalon(frontLeftID);
        drivetrainFR = new CANTalon(frontRightID);
        drivetrainBL = new CANTalon(backLeftID);
        drivetrainBR = new CANTalon(backRightID);
        
        // Gyro locking
        navX = new NavXYawInput(SPI.Port.kMXP);
        PID gyroPID = new PID(0.18*13.75,0,.0003*13.75);
        gyroPID.setInput(navX);
        GyroCorrection gCorrect=new GyroCorrection(navX,gyroPID,20,0.3*20);
        gLock = new GyroLock(gCorrect);
        new ToggleButton(driveStick, gLockID, gLock);
        
        // Drive train setup
        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,-1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0, 1), new Motor(drivetrainFR), new Motor(drivetrainBR));
        
        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);

        //Drive train control
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));
        dtBuilder.addStep(gCorrect);
        
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
        gLock.update();
        stdDevCurrentCheckDT();
        differenceCurrentCheckDT();

        SmartDashboard.putNumber("Intake Power", intakePow);
        SmartDashboard.putNumber("Climb Power", climbPow);
    }

    //Current Checks Maths using STD DEV
    private void stdDevCurrentCheckDT(){
        // Calculate Averages and standard deviations for drive train current draws
        double tempLeftCurrentAvg   = mathAlgorithms.avg(pdp.getCurrent(drivetrainFL.getDeviceID()) + pdp.getCurrent(drivetrainBL.getDeviceID()));
        double tempRightCurrentAvg  = mathAlgorithms.avg(pdp.getCurrent(drivetrainFR.getDeviceID())+ pdp.getCurrent(drivetrainBR.getDeviceID()));
        double dtLeftCurrentStdDev  = mathAlgorithms.stdDiv(pdp.getCurrent(drivetrainFR.getDeviceID()), pdp.getCurrent(drivetrainBR.getDeviceID()));
        double dtRightCurrentStdDev = mathAlgorithms.stdDiv(pdp.getCurrent(drivetrainFL.getDeviceID()), pdp.getCurrent(drivetrainBL.getDeviceID()));
        
        // Check if the motor current draw is withing 1 standard deviation
        boolean checkFL = mathAlgorithms.checkSTDDT(pdp.getCurrent(drivetrainFL.getDeviceID()), tempLeftCurrentAvg,  dtLeftCurrentStdDev);
        boolean checkFR = mathAlgorithms.checkSTDDT(pdp.getCurrent(drivetrainFR.getDeviceID()), tempRightCurrentAvg, dtRightCurrentStdDev);
        boolean checkBL = mathAlgorithms.checkSTDDT(pdp.getCurrent(drivetrainBL.getDeviceID()), tempLeftCurrentAvg, dtLeftCurrentStdDev);
        boolean checkBR = mathAlgorithms.checkSTDDT(pdp.getCurrent(drivetrainBR.getDeviceID()), tempLeftCurrentAvg, dtRightCurrentStdDev);
        
        // Debug motor checks
        SmartDashboard.putBoolean("DT FL within 1 STD",checkFL);
        SmartDashboard.putBoolean("DT FR within 1 STD",checkFR);
        SmartDashboard.putBoolean("DT BL within 1 STD",checkBL);
        SmartDashboard.putBoolean("DT BR within 1 STD",checkBR);
    }

    //DT Current Checks MAths using Diff from Motor
    private void differenceCurrentCheckDT(){
        double tempLeftCurrentAvg   = mathAlgorithms.avg(pdp.getCurrent(drivetrainFL.getDeviceID()) + pdp.getCurrent(drivetrainBL.getDeviceID()));
        double tempRightCurrentAvg  = mathAlgorithms.avg(pdp.getCurrent(drivetrainFR.getDeviceID())+ pdp.getCurrent(drivetrainBR.getDeviceID()));

        // Check if the motor current draw is withing 1 standard deviation
        double checkFL = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainFL.getDeviceID()), tempLeftCurrentAvg);
        double checkFR = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainFR.getDeviceID()), tempRightCurrentAvg);
        double checkBL = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainBL.getDeviceID()), tempLeftCurrentAvg);
        double checkBR = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainBR.getDeviceID()), tempRightCurrentAvg);

        // Debug motor checks
        SmartDashboard.putNumber("DT FL Diff",checkFL);
        SmartDashboard.putNumber("DT FR Diff",checkFR);
        SmartDashboard.putNumber("DT BL Diff",checkBL);
        SmartDashboard.putNumber("DT BR Diff",checkBR);
    }


}
