package frc.team555.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.DoubleInput;
import org.montclairrobotics.sprocket.utils.PID;



public class Robot extends SprocketRobot{

    // DT IDs
    public static final int frontLeftDeviceNumber  = 3;
    public static final int frontRightDeviceNumber = 1;
    public static final int backLeftDeviceNumber   = 4;
    public static final int backRightDeviceNumber  = 2;

    // Joysticks IDs
    public static final int driveStickDeviceNumber = 0;
    public static final int auxStickDeviceNumber   = 1;

    // Buttons IDs
    public static final int intakeButtonID = 1;

    // Intake IDs
    public static final int intakeOL = 1;
    public static final int intakeCL = 0;
    public static final int intakeOR = 6;
    public static final int intakeCR = 7;
    public static final int intakeL  = 0;
    public static final int intakeR  = 5;

    // PID VALs TODO: RETUNING
    public static final double pidP = 0.18*13.75;
    public static final double pidI = 0;
    public static final double pidD = .0003*13.75;

    // Intake Motors
    Motor intakeRight;
    Motor intakeLeft;

    // Intake
    DigitalInput openLeftSwitch;
    DigitalInput closeLeftSwitch;
    DigitalInput openRightSwitch;
    DigitalInput closeRightSwitch;

    // Drive Train Motors
    WPI_TalonSRX drivetrainFL;
    WPI_TalonSRX drivetrainFR;
    WPI_TalonSRX drivetrainBL;
    WPI_TalonSRX drivetrainBR;

    // Control Devices
    PowerDistributionPanel pdp;
    AHRS navX;
    MathAlgorithms mathAlgorithms;
    GyroLock gLock;
    DoubleInput gyroInput;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);

        pdp = new PowerDistributionPanel();

        // ========= INTAKE ========= //

        // intake motors
        intakeLeft  = new Motor(new VictorSP(intakeL));
        intakeRight = new Motor(new WPI_TalonSRX(intakeR));
        intakeRight.setInverted(true);
        intakeLeft.setInverted(true);

        // intake limit switches
        openLeftSwitch   = new DigitalInput(intakeOL);
        closeLeftSwitch  = new DigitalInput(intakeCL);
        openRightSwitch  = new DigitalInput(intakeOR);
        closeRightSwitch = new DigitalInput(intakeCR);

        // ========= DRIVETRAIN ========= //

        drivetrainFL = new WPI_TalonSRX(frontLeftDeviceNumber);
        drivetrainFR = new WPI_TalonSRX(frontRightDeviceNumber);
        drivetrainBL = new WPI_TalonSRX(backLeftDeviceNumber);
        drivetrainBR = new WPI_TalonSRX(backRightDeviceNumber);

        // Gyro locking
        navX = new AHRS(SPI.Port.kMXP);
        gyroInput = new DoubleInput(navX.getYaw());
        PID gyroPID = new PID(pidP, pidI, pidD);
        gyroPID.setInput(gyroInput);
        GyroCorrection gCorrect=new GyroCorrection(gyroInput, gyroPID,20,0.3*20);
        gLock = new GyroLock(gCorrect);

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
        try { dtBuilder.build(); } catch (InvalidDriveTrainException e) { e.printStackTrace(); }

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
        gLock.update();
    }



    private void checkCurrentDT(){
        double tempLeftCurrentAvg = mathAlgorithms.avg(pdp.getCurrent(frontLeftDeviceNumber),
                pdp.getCurrent(backLeftDeviceNumber));

        double tempRightCurrentAvg = mathAlgorithms.avg(pdp.getCurrent(frontRightDeviceNumber),
                pdp.getCurrent(backRightDeviceNumber));

        double dtLeftCurrentStdDev  =  mathAlgorithms.stdDiv(pdp.getCurrent(frontLeftDeviceNumber),
                pdp.getCurrent(backLeftDeviceNumber));

        double dtRightCurrentStdDev  = mathAlgorithms.stdDiv(pdp.getCurrent(frontRightDeviceNumber),
                pdp.getCurrent(backRightDeviceNumber));

        boolean STDDEVcheckFL;
        STDDEVcheckFL = mathAlgorithms.checkSTDDT(pdp.getCurrent(frontLeftDeviceNumber),
                tempLeftCurrentAvg,
                dtLeftCurrentStdDev);

        boolean STDDEVcheckFR;
        STDDEVcheckFR = mathAlgorithms.checkSTDDT(pdp.getCurrent(frontRightDeviceNumber),
                tempRightCurrentAvg,
                dtRightCurrentStdDev);



        boolean STDDEVcheckBL;
        STDDEVcheckBL = mathAlgorithms.checkSTDDT(pdp.getCurrent(backLeftDeviceNumber),
                tempLeftCurrentAvg,
                dtLeftCurrentStdDev);

        boolean STDDEVcheckBR;
        STDDEVcheckBR = mathAlgorithms.checkSTDDT(pdp.getCurrent(backRightDeviceNumber),
                tempRightCurrentAvg,
                dtRightCurrentStdDev);

        // STD DEV to Smart Dashboard
        SmartDashboard.putBoolean("FL within STD Dev",STDDEVcheckFL);
        SmartDashboard.putBoolean("FR within STD Dev",STDDEVcheckFR);
        SmartDashboard.putBoolean("BL within STD Dev",STDDEVcheckBL);
        SmartDashboard.putBoolean("BR within STD Dev",STDDEVcheckBR);

    }

}