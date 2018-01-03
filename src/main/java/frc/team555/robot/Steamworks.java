package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.ToggleButton;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;
import org.opencv.core.Mat;
import frc.team555.robot.NavXYawInput;
import frc.team555.robot.MathAlgorithms;

public class Steamworks  extends SprocketRobot{
    
    // Drive train motors
    public final int frontLeftDeviceNumber  = 3;
    public final int frontRightDeviceNumber = 1;
    public final int backLeftDeviceNumber   = 4;
    public final int backRightDeviceNumber  = 2;
    
    // Joysticks
    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber   = 1;
    
    // Buttons
    public final int intakeButtonID = 1;
    public final int gLockID = 3;
    public final int climbButtonID = 4;
    
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
        intakeRight = new Motor(new CANTalon(5));
        intakeRight.setInverted(true);
        intakeLeft = new Motor(new VictorSP(0));
        intakeLeft.setInverted(true);

        // climb motors
        climbLeft = new Motor(new CANTalon(6));
        climbRight = new Motor(new CANTalon(7));
        climbLeft.setInverted(true);
        climbRight.setInverted(true);
        
        // intake limit switches
        openLeftSwitch = new DigitalInput(1);
        closeLeftSwitch = new DigitalInput(0);
        openRightSwitch = new DigitalInput(6);
        closeRightSwitch = new DigitalInput(7);
    
        // ========= DRIVETRAIN ========= //
        drivetrainFL = new CANTalon(frontLeftDeviceNumber);
        drivetrainFR = new CANTalon(frontRightDeviceNumber);
        drivetrainBL = new CANTalon(backLeftDeviceNumber);
        drivetrainBR = new CANTalon(backRightDeviceNumber);
        
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
        dtBuilder.setArcadeDriveInput(driveStick);
        dtBuilder.addStep(gCorrect);
        
        // Create drive train
        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

        // ========= INTAKE ========= //
        double intakePow=0.4;
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
        double climbPow=0.4;
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
    }

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

    private void differenceCurrentCheckDT(){
        double tempLeftCurrentAvg   = mathAlgorithms.avg(pdp.getCurrent(drivetrainFL.getDeviceID()) + pdp.getCurrent(drivetrainBL.getDeviceID()));
        double tempRightCurrentAvg  = mathAlgorithms.avg(pdp.getCurrent(drivetrainFR.getDeviceID())+ pdp.getCurrent(drivetrainBR.getDeviceID()));

        // Check if the motor current draw is withing 1 standard deviation
        double checkFL = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainFL.getDeviceID()), tempLeftCurrentAvg);
        double checkFR = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainFR.getDeviceID()), tempRightCurrentAvg);
        double checkBL = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainBL.getDeviceID()), tempLeftCurrentAvg);
        double checkBR = mathAlgorithms.checkDiffDT(pdp.getCurrent(drivetrainBR.getDeviceID()), tempLeftCurrentAvg);

        // Debug motor checks
        SmartDashboard.putNumber("DT FL Diff",checkFL);
        SmartDashboard.putNumber("DT FR Diff",checkFR);
        SmartDashboard.putNumber("DT BL Diff",checkBL);
        SmartDashboard.putNumber("DT BR Diff",checkBR);
    }


}
