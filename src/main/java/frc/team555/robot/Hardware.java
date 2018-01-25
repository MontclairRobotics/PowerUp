package frc.team555.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
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
	    public static final int motorDriveBR = 4;
	    public static final int motorDriveBL = 3;
	    public static final int motorDriveFR = 2;
	    public static final int motorDriveFL = 1;

	    // Gyroscope ID
	    public static final SPI.Port navxPort = SPI.Port.kMXP;
	}
    
    // Drive Train Motors
    public static WPI_TalonSRX motorDriveBR;
    public static WPI_TalonSRX motorDriveBL;
    public static WPI_TalonSRX motorDriveFR;
    public static WPI_TalonSRX motorDriveFL;

    // Encoders
    public static SEncoder rightEncoder;
    public static SEncoder leftEncoder;

    // Gyroscope
    public static NavXInput navx;

    public static void init(){
        // Instantiate drive train motors using motor ID's
        motorDriveBR = new WPI_TalonSRX(DeviceID.motorDriveBR);
        motorDriveBR.setInverted(true);
        motorDriveBL = new WPI_TalonSRX(DeviceID.motorDriveBL);
        motorDriveFR = new WPI_TalonSRX(DeviceID.motorDriveFR);
        motorDriveFR.setInverted(true);
        motorDriveFL = new WPI_TalonSRX(DeviceID.motorDriveFL);

        rightEncoder = new SEncoder(new Encoder(3,2),6544.0/143.0);
        leftEncoder  = new SEncoder(new Encoder(0,1),6544.0/143.0);

        navx = new NavXInput(DeviceID.navxPort);
    }
}
