package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SprocketRobot {
    Joystick joystick;
    
	DriveTrain dt;
	
	@Override
	public void robotInit() {
		/* Control: Joysticks */
        this.joystick = new Joystick(0);
        
		/* Drive Train: Tank */
		this.dt = new TankDriveTrain(0, 1, 2, 3);
		dt.setDefaultInput(new SquaredDriveInput(joystick));
	}
	
	@Override
	public void update() {
		SmartDashboard.putString("DriveTrain: Input", dt.getInput().toString());
		
		SmartDashboard.putNumber("Joystick: X-Axis", joystick.getX());
		SmartDashboard.putNumber("Joystick: Y-Axis", joystick.getY());
	}
}
