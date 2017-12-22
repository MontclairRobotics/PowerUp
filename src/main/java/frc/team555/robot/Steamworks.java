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
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;
import org.opencv.core.Mat;
import frc.team555.robot.NavXRollInput;

public class Steamworks  extends SprocketRobot{

    public final int frontLeftDeviceNumber = 3;
    public final int frontRightDeviceNumber = 1;
    public final int backLeftDeviceNumber = 4;
    public final int backRightDeviceNumber = 2;

    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber = 1;

    public final int intakeButtonID = 1;
    public final int gLockID = 3;

    Motor intakeRight;
    Motor intakeLeft;
    DigitalInput openLeftSwitch;
    DigitalInput closeLeftSwitch;
    DigitalInput openRightSwitch;
    DigitalInput closeRightSwitch;

    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;

    PowerDistributionPanel pdp;
    NavXRollInput navX;
    GyroLock gLock;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);
        pdp = new PowerDistributionPanel();

        //INTAKE
        intakeRight = new Motor(new CANTalon(5));
        intakeRight.setInverted(true);
        intakeLeft = new Motor(new VictorSP(0));
        intakeLeft.setInverted(true);
        openLeftSwitch = new DigitalInput(1);
        closeLeftSwitch = new DigitalInput(0);
        openRightSwitch = new DigitalInput(6);
        closeRightSwitch = new DigitalInput(7);

        //DRIVETRAIN
        drivetrainFL = new CANTalon(frontLeftDeviceNumber);
        drivetrainFR = new CANTalon(frontRightDeviceNumber);
        drivetrainBL = new CANTalon(backLeftDeviceNumber);
        drivetrainBR = new CANTalon(backRightDeviceNumber);

        navX = new NavXRollInput(SPI.Port.kMXP);
        PID gyroPID = new PID(0.18*13.75,0,.0003*13.75);
        gyroPID.setInput(navX);
        GyroCorrection gCorrect=new GyroCorrection(navX,gyroPID,20,0.3*20);
        gLock = new GyroLock(gCorrect);
        new ToggleButton(driveStick, gLockID, gLock);


        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,-1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0, 1), new Motor(drivetrainFR), new Motor(drivetrainBR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setArcadeDriveInput(driveStick);
        dtBuilder.addStep(gCorrect);

        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }



        // INTAKE CONTROL
        double intakePow=0.3;
        Button intake = new JoystickButton(driveStick, intakeButtonID);
        intake.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(openLeftSwitch.get()){intakeLeft.set(0);}else {intakeLeft.set(intakePow);}
                if(!openRightSwitch.get()){intakeRight.set(0);}else {intakeRight.set(intakePow);}
            }
        });

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
        gLock.update();

    }


}
