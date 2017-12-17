package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.usfirst.frc.team555.robot.HwCfg.CANChannel;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;

public class MecanumPrototypeDrivetrain extends SprocketRobot {

	//Joystick
	Joystick driveStick    = new Joystick(0);
	ArcadeDriveInput input = new ArcadeDriveInput(driveStick);
	
	//drive train motors
	CANTalon frontLeft     = HwCfg.BuildCANTalon(CANChannel.FRONTLEFT);
	CANTalon frontRight    = HwCfg.BuildCANTalon(CANChannel.FRONTRIGHT);
	CANTalon backLeft      = HwCfg.BuildCANTalon(CANChannel.BACKLEFT);
	CANTalon backRight     = HwCfg.BuildCANTalon(CANChannel.BACKRIGHT);
	
	//Drive modules //TODO: Fix Force Vectors
    DriveModule frontLeftModule  = new DriveModule(new XY(-1, 1),  new XY(1, 1),  new Motor(frontLeft));
    DriveModule frontRightModule = new DriveModule(new XY(1, 1),   new XY(0, -1), new Motor(frontRight));
    DriveModule backLeftModule   = new DriveModule(new XY(-1, -1), new XY(0, -1), new Motor(backLeft));
    DriveModule backRightModule  = new DriveModule(new XY(1, -10), new XY(0, -1), new Motor(backRight));
	
	@Override
    public void robotInit() {
        
        //Drive train builder
        DriveTrainBuilder builder = new DriveTrainBuilder();
        builder.addDriveModule(frontLeftModule);
        builder.addDriveModule(frontRightModule);
        builder.addDriveModule(backLeftModule);
        builder.addDriveModule(backRightModule);
        builder.setDriveTrainType(DriveTrainType.MECANUM);
        builder.setInput(input);
        try {
            builder.build();
        } catch (InvalidDriveTrainException e) {
            Debug.msg("Status","Drive Train Builder FAILED");
        }
        
	}
	
    @Override
    public void update() {
    	
    }
    
 }
    