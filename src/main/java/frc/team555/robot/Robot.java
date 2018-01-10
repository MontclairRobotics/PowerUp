package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.utils.PID;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SprocketRobot {
    PowerDistributionPanel pdp;

    PID pid;
    
    PID gyroPID;
    NavXRollInput gyro;
    
    Joystick joystick1;
    Joystick joystick2;
    
	DriveTrain dt;
	
	@Override
	public void robotInit() {
		/* Control: Joysticks */
        this.joystick1 = new Joystick(0);
        this.joystick1 = new Joystick(1);
        
        /* Power Distribution Panel */
        this.pdp = new PowerDistributionPanel();
        
        /* PID */
        this.pid = new PID();

        /* Gyroscope: NavX */
        this.gyro = new NavXRollInput(SPI.Port.kMXP);
        
        /* Gyroscope: PID Control */
        this.gyroPID = new PID(0.18*13.75, 0, 0.0003*13.75).setInput(gyro);
        GyroCorrection gCorrect = new GyroCorrection(gyro, gyroPID, 20, 0.3*20);
        GyroLock gLock = new GyroLock(gCorrect);
		
		/* Drive Train: Tank */
		this.dt = new TankDriveTrain(0, 1, 2, 3);
		dt.setDefaultInput(new SquaredDriveInput(joystick1));
	}
	
	@Override
	public void update() {
		SmartDashboard.putString("Gyro PID: Input", gyroPID.getInput().toString());
		SmartDashboard.putString("DriveTrain: Input", dt.getInput().toString());
	}
}
