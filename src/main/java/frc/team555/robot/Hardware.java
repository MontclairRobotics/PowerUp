package frc.team555.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;

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

    // ============================
    // Device Port configuration
    // ============================

    // Drive Train Motor IDs
    public static final int motorDriveBRID = 1;
    public static final int motorDriveBLID = 2;
    public static final int motorDriveFRID = 3;
    public static final int motorDriveFLID = 4;

    // Power Cube Intake Motor IDs
    public static final int motorIntakeLID = 5;
    public static final int motorIntakeRID = 6;

    // Lift Motor IDs TODO: electronics spreadsheet
    public static final int motorLiftRID   = 0;
    public static final int motorLiftLID   = 0;


    // Output Motor IDs TODO: electronics spreadsheet
    public static final int motorOutputRID = 0;
    public static final int motorOutputLID = 0;


    // Gyro ID
    public static final SPI.Port navxPort = SPI.Port.kMXP;


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

    // Output Motor Controllers
    public static WPI_TalonSRX motorOutputR;
    public static WPI_TalonSRX motorOutputL;


    // Gyroscope
    public static AHRS navx;

    public static void init(){
        // Instantiate drive train motors using motor ID's
        motorDriveBR = new WPI_TalonSRX(motorDriveBRID);
        motorDriveBR.setInverted(true);
        motorDriveBL = new WPI_TalonSRX(motorDriveBLID);
        motorDriveFR = new WPI_TalonSRX(motorDriveFRID);
        motorDriveFR.setInverted(true);
        motorDriveFL = new WPI_TalonSRX(motorDriveFLID);


        // Instantiate intake motors using motor ID's
        motorIntakeL = new WPI_TalonSRX(motorIntakeLID);
        motorIntakeL.setInverted(true);
        motorIntakeR = new WPI_TalonSRX(motorIntakeRID);

        // Instantiate lift motors using motor ID's
        motorLiftR = new WPI_TalonSRX(motorLiftRID);
        motorLiftL = new WPI_TalonSRX(motorLiftLID);

        // Instantiate output motors using motor ID's
        motorOutputR = new WPI_TalonSRX(motorOutputRID);
        motorOutputR = new WPI_TalonSRX(motorOutputRID);


        navx = new AHRS(navxPort);
    }
}
