package frc.team555.robot.core;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import frc.team555.robot.utils.NavXInput;
import org.montclairrobotics.sprocket.motors.SEncoder;

/**
 * The hardware class is in charge of storing all hardware
 * devices and configurations that the 2018 PowerUp robot
 * will use. The configurations are based on the google sheet
 * that can be found Here: <link>https://docs.google.com/a/montclairrobotics.org/spreadsheets/d/1iIJKKJEcQqPI1OJBf50IBtUTwjSuhOJbNW4E8-niwl4/edit?usp=sharing</link>
 * The sheet is in place so that both code and electronics are
 * on the same page from the start on what the device configurations
 * are.
 *
 *
 * Hardware device: Any physical device on the robot that is connected to the electronics
 * board or on roborio. This includes motors, cameras, servos, etc.
 *
 * Sensors and inputs should be defined in the control class and not in hardware
 * @see Control
 *
 * Structure
 *
 * - Device Port configuration: All port ID's for hardware devices.
 *      - Drive Train Motor ID's: motor ports to be used for the drive train
 * - Motor Configuration: Declaration of all motor controllers on the robot
 *      - Drive Train Motors: Declaration of the drive train motors
 *
 */
public class Hardware {

    private static class DeviceID {
        // Drive Train Motor IDS
        public static final int motorDriveBR = 8;
        public static final int motorDriveBL = 3;
        public static final int motorDriveFR = 7;
        public static final int motorDriveFL = 1;

        public static final int motorIntakeLift = 9;
        public static final int motorMainLiftFront = 4;
        public static final int motorMainLiftBack = 2;

        public static final int motorIntakeClamp = 6;
        //left intake 5
        //pincer 6
        //lifter 9
        //right intake 10

        //lift encoder 4 5

        // Gyroscope ID
        public static final SPI.Port navxPort = SPI.Port.kMXP;
    }

    // ============================
    // Motor configuration
    // ============================

    // Drive Train Motor Controllers

    public static WPI_TalonSRX motorDriveBR;
    public static WPI_TalonSRX motorDriveBL;
    public static WPI_TalonSRX motorDriveFR;
    public static WPI_TalonSRX motorDriveFL;

    // Power Cube Intake Motor Controllers
    public static WPI_TalonSRX motorIntakeL;
    public static WPI_TalonSRX motorIntakeR;

    // IntakeLift Motor Controllers
    public static WPI_TalonSRX motorLiftIntake;

    // MainLift Motor Controllers

    public static WPI_TalonSRX motorLiftMainFront;
    public static WPI_TalonSRX motorLiftMainBack;

    public static WPI_TalonSRX motorIntakeClamp;

    public static DigitalInput intakeClosedSwitch;
    public static DigitalInput intakeOpenSwitch;


    // Encoders
    public static SEncoder rightDriveEncoder;
    public static SEncoder leftDriveEncoder;

    public static SEncoder liftEncoder;
    public static SEncoder intakeLiftEncoder;

    // Gyroscope
    public static NavXInput navx;

    public static DigitalInput liftLimitSwitch;


    public static void init(){
        // Instantiate drive train motors using motor ID's

        motorDriveBR = new WPI_TalonSRX(DeviceID.motorDriveBR);
        motorDriveBL = new WPI_TalonSRX(DeviceID.motorDriveBL);
        motorDriveFR = new WPI_TalonSRX(DeviceID.motorDriveFR);
        motorDriveFL = new WPI_TalonSRX(DeviceID.motorDriveFL);
        motorDriveBR.setInverted(true);
        motorDriveFR.setInverted(true);

        motorLiftIntake = new WPI_TalonSRX(DeviceID.motorIntakeLift);
        motorLiftMainFront = new WPI_TalonSRX(DeviceID.motorMainLiftFront);
        motorLiftMainBack = new WPI_TalonSRX(DeviceID.motorMainLiftBack);

        motorLiftMainBack.setInverted(true);

        motorIntakeClamp = new WPI_TalonSRX(DeviceID.motorIntakeClamp);

        intakeClosedSwitch = new DigitalInput(9);
        intakeOpenSwitch = new DigitalInput(8);


        //double ticksPerInch=6544.0/143.0;`
        //old/new=17.1859 * 1.25/(6544.0/143.0)
        //double ticksPerInch=17.1859 * 1.25;
        //double ticksPerInch=2*80/10.71/3/Math.PI*12;
        double ticksPerInch=1.0/6/Math.PI*10.71*40;

        rightDriveEncoder = new SEncoder(new Encoder(3,2),ticksPerInch);
        leftDriveEncoder  = new SEncoder(new Encoder(0,1),ticksPerInch);

        liftEncoder       = new SEncoder(new Encoder(4,5), 0); // todo: REALLY NEED TO BE SET
        intakeLiftEncoder = new SEncoder(new Encoder(6,7), 1); // todo: REALLY NEED TO BE SET



        navx = new NavXInput(DeviceID.navxPort);

        liftLimitSwitch = new DigitalInput(10);
    }
}
