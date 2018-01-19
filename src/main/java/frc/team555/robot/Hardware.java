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

    //Drive Train Motor IDS
    public static final int motorDriveBRID = 1;
    public static final int motorDriveBLID = 2;
    public static final int motorDriveFRID = 3;
    public static final int motorDriveFLID = 4;


    // Gyro ID
    public static final SPI.Port navxPort = SPI.Port.kMXP;


    // ============================
    // Motor configuration
    // ============================

    // Drive Train Motors
    public static WPI_TalonSRX motorDriveBR;
    public static WPI_TalonSRX motorDriveBL;
    public static WPI_TalonSRX motorDriveFR;
    public static WPI_TalonSRX motorDriveFL;

    public static AHRS navx;

    public static void init(){
        // Instantiate drive train motors using motor ID's
        motorDriveBR = new WPI_TalonSRX(motorDriveBRID);
        motorDriveBR.setInverted(true);
        motorDriveBL = new WPI_TalonSRX(motorDriveBLID);
        motorDriveFR = new WPI_TalonSRX(motorDriveFRID);
        motorDriveFR.setInverted(true);
        motorDriveFL = new WPI_TalonSRX(motorDriveFLID);



        navx = new AHRS(navxPort);
    }
}
