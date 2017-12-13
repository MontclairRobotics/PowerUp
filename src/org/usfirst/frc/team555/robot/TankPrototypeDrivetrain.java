package org.usfirst.frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoders;
import org.montclairrobotics.sprocket.control.ArcadeDriveInput;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrainBuilder;
import org.montclairrobotics.sprocket.drive.DriveTrainType;
import org.montclairrobotics.sprocket.drive.InvalidDriveTrainException;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;

public class TankPrototypeDrivetrain extends SprocketRobot {

	//Joystick
	Joystick driveStick    = new Joystick(0);
	ArcadeDriveInput input = new ArcadeDriveInput(driveStick);
	
	//drive train motors
	CANTalon frontLeft     = new CANTalon(0);
	CANTalon frontRight    = new CANTalon(1);
	CANTalon backLeft      = new CANTalon(2);
	CANTalon backRight     = new CANTalon(3);
	
	@Override
    public void robotInit() {

		//Drive modules
        DriveModule left = new DriveModule(new XY(-1, 0), new XY(0, 1), new Motor(frontLeft), new Motor(backLeft));
        DriveModule right = new DriveModule(new XY(1, 0), new XY(0, -1), new Motor(frontRight), new Motor(backRight));

        //Drive train builder
        DriveTrainBuilder builder = new DriveTrainBuilder();
        builder.addDriveModule(left);
        builder.addDriveModule(right);
        builder.setDriveTrainType(DriveTrainType.TANK);
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
    