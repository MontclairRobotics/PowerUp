package frc.team555.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class TankRobot extends SprocketRobot {

    WPI_TalonSRX drivetrainFL;
    WPI_TalonSRX drivetrainFR;
    WPI_TalonSRX drivetrainBL;
    WPI_TalonSRX drivetrainBR;

    PowerDistributionPanel pdp;

    PID pid;
    NavXRollInput navX;

    public final int frontLeftDeviceNumber  = 0; // Steamworks: 3
    public final int frontRightDeviceNumber = 1; // Steamworks: 1
    public final int backLeftDeviceNumber   = 2; // Steamworks: 4
    public final int backRightDeviceNumber  = 3; // Steamworks: 2

    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber   = 1;

    public final int buttonModule1 = 1;
    public final int buttonModule2 = 2;

    @Override
    public void robotInit() {

        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);
        pdp = new PowerDistributionPanel();
        pid = new PID();

        // DRIVETRAIN
        navX = new NavXRollInput(SPI.Port.kMXP);
        PID gyroPID = new PID(0.18*13.75,0,.0003*13.75);
        gyroPID.setInput(navX);
        GyroCorrection gCorrect=new GyroCorrection(navX,gyroPID,20,0.3*20);
        GyroLock gLock = new GyroLock(gCorrect);


        drivetrainFL = new WPI_TalonSRX(frontLeftDeviceNumber);
        drivetrainFR = new WPI_TalonSRX(frontRightDeviceNumber);
        drivetrainBL = new WPI_TalonSRX(backLeftDeviceNumber);
        drivetrainBR = new WPI_TalonSRX(backRightDeviceNumber);

        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0,1), new Motor(drivetrainFR), new Motor(drivetrainBR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));



        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

        // Module 1
        Button module1 = new JoystickButton(driveStick, buttonModule1);
        module1.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

        module1.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

        //Module 2
        Button module2 = new JoystickButton(driveStick, buttonModule2);
        module2.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {

            }
        });
        module1.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
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
