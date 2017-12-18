package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class MecanumPrototypeDrivetrain extends SprocketRobot {

	//Joystick
	Joystick driveStick;
	ArcadeDriveInput input;
	
	DriveTrain driveTrain;
	
	@Override
    public void robotInit() {
        
		driveStick = new Joystick(0);

		//Drive train builder
		//TODO: Fix Force Vectors
		//TODO: Allow linking in DriveModule, Module so that 
		//      DriveModule construction would look like...
		//      new DriveModule()
		//			.setOffsetVector(new XY(-1,1))
		//			.setForceVector(new XY(1,1))
		//			.addMotor(new Motor(new CANTalon(0)))
		//
        DriveTrainBuilder builder = new DriveTrainBuilder()
	        .addDriveModule(new DriveModule(new XY(-1, 1),  new XY(1, 1),  
	        		new Motor(new CANTalon(0)))) // front left
	        .addDriveModule(new DriveModule(new XY(1, 1),   new XY(0, -1), 
	        		new Motor(new CANTalon(1)))) // front right
	        .addDriveModule(new DriveModule(new XY(-1, -1), new XY(0, -1), 
	        		new Motor(new CANTalon(2)))) // back left
	        .addDriveModule(new DriveModule(new XY(1, -1), new XY(0, -1), 
	        		new Motor(new CANTalon(3)))) // back right
	        .setDriveTrainType(DriveTrainType.MECANUM)
	        .setInput(new ArcadeDriveInput(driveStick));
        try {
            driveTrain = builder.build();
        } catch (InvalidDriveTrainException e) {
            Debug.msg("Status","Drive Train Builder FAILED");
        }
        
	}
	
    @Override
    public void update() {
    	
    }
    
 }
    