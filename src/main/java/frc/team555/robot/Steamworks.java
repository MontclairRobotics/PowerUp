package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.PID;

public class Steamworks  extends SprocketRobot{
    /*
     * ========================================================================
     *   Assign Device ID's
     * ========================================================================
     */
    
    // Drive train motors
    public final int frontLeftDeviceNumber = 3;
    public final int frontRightDeviceNumber = 1;
    public final int backLeftDeviceNumber = 4;
    public final int backRightDeviceNumber = 2;
    
    // Joysticks
    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber = 1;
    
    // Buttons
    public final int intakeButtonID = 1;
    
    
    /*
     * ========================================================================
     *   Declare Hardware Devices
     * ========================================================================
     */
    
    // Intake Motors
    Motor intakeRight;
    Motor intakeLeft;
    
    // Intake
    DigitalInput openLeftSwitch;
    DigitalInput closeLeftSwitch;
    DigitalInput openRightSwitch;
    DigitalInput closeRightSwitch;
    
    // Drive Train Motors
    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;
    
    // Control Devices
    PowerDistributionPanel pdp;
    NavXRollInput navX;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);
        pdp = new PowerDistributionPanel();

        // ========= INTAKE ========= //
        
        // intake motors
        intakeRight = new Motor(new CANTalon(5));
        intakeRight.setInverted(true);
        intakeLeft = new Motor(new VictorSP(0));
        intakeLeft.setInverted(true);
        
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
        navX = new NavXRollInput(SPI.Port.kMXP);
        PID gyroPID = new PID(0.18*13.75,0,.0003*13.75);
        gyroPID.setInput(navX);
        GyroCorrection gCorrect=new GyroCorrection(navX,gyroPID,20,0.3*20);
        GyroLock gLock = new GyroLock(gCorrect);
        
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
    
    
    
        // ========= CONTROL ========= //
        double intakePow=0.3;
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
    }

    @Override
    public void update() {
        checkCurrentDT();
    }

    private void checkCurrentDT(){
        double tempLeftCurrentAvg = (pdp.getCurrent(drivetrainFL.getDeviceID()) + pdp.getCurrent(drivetrainBL.getDeviceID()))/2;

        double tempRightCurrentAvg = (pdp.getCurrent(drivetrainFR.getDeviceID())+ pdp.getCurrent(drivetrainBR.getDeviceID()))/2;

        double dtLeftCurrentStdDev  =  Math.sqrt((Math.pow(Math.abs(pdp.getCurrent(drivetrainFL.getDeviceID()) - tempLeftCurrentAvg),2) +
                Math.pow(Math.abs(pdp.getCurrent(drivetrainBL.getDeviceID()) - tempLeftCurrentAvg),2))/2);

        double dtRightCurrentStdDev  =  Math.sqrt((Math.pow(Math.abs(pdp.getCurrent(drivetrainFR.getDeviceID()) - tempRightCurrentAvg),2) +
                Math.pow(Math.abs(pdp.getCurrent(drivetrainBR.getDeviceID()) - tempRightCurrentAvg),2))/2);

        boolean checkFL;
        if (Math.abs(pdp.getCurrent(drivetrainFL.getDeviceID()) - tempLeftCurrentAvg) < dtLeftCurrentStdDev){
            checkFL = true;
        }else{
            checkFL = false;
        }

        boolean checkFR;
        if (Math.abs(pdp.getCurrent(drivetrainFR.getDeviceID()) - tempRightCurrentAvg) < dtRightCurrentStdDev){
            checkFR = true;
        }else{
            checkFR = false;
        }

        boolean checkBL;
        if (Math.abs(pdp.getCurrent(drivetrainBL.getDeviceID()) - tempLeftCurrentAvg) < dtLeftCurrentStdDev){
            checkBL = true;
        }else{
            checkBL = false;
        }

        boolean checkBR;
        if (Math.abs(pdp.getCurrent(drivetrainBR.getDeviceID()) - tempRightCurrentAvg) < dtRightCurrentStdDev){
            checkBR = true;
        }else{
            checkBR = false;
        }

        SmartDashboard.putBoolean("FL within STD Dev",checkFL);
        SmartDashboard.putBoolean("FR within STD Dev",checkFR);
        SmartDashboard.putBoolean("BL within STD Dev",checkBL);
        SmartDashboard.putBoolean("BR within STD Dev",checkBR);
    }

}
