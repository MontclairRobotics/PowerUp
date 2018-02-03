package frc.team555.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
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
	    /*public static final int motorDriveBR = 4;
	    public static final int motorDriveBL = 3;
	    public static final int motorDriveFR = 2;
	    public static final int motorDriveFL = 1;*/
        public static final int motorDriveBR = 4;
        public static final int motorDriveBL = 3;
        public static final int motorDriveFR = 2;
        public static final int motorDriveFL = 1;

        public static final int testMotor1 = 7;
        public static final int testMotor2 = 8;

        public static final int motorLiftR = 0;
        public static final int motorLiftL = 0;

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

    // Lift Motor Controllers
    public static WPI_TalonSRX motorLiftR;
    public static WPI_TalonSRX motorLiftL;

    public static WPI_TalonSRX testMotor1;
    public static WPI_TalonSRX testMotor2;


    // Encoders
    public static SEncoder rightDriveEncoder;
    public static SEncoder leftDriveEncoder;

    public static SEncoder liftEncoder;
//    public static SEncoder leftLiftEncoder;

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

        motorLiftR = new WPI_TalonSRX(DeviceID.motorLiftR);
        motorLiftL = new WPI_TalonSRX(DeviceID.motorLiftL);

        testMotor1 = new WPI_TalonSRX(DeviceID.testMotor1);
        testMotor2 = new WPI_TalonSRX(DeviceID.testMotor2);
        testMotor2.setInverted(true);


        rightDriveEncoder = new SEncoder(new Encoder(3,2),6544.0/143.0);
        leftDriveEncoder  = new SEncoder(new Encoder(0,1),6544.0/143.0);

        liftEncoder = new SEncoder(null, 0);
//        leftLiftEncoder  = new SEncoder(null, 0);

        navx = new NavXInput(DeviceID.navxPort);

        liftLimitSwitch=new DigitalInput(10);
    }
}
