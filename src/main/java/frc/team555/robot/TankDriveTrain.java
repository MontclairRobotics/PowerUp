package frc.team555.robot;

import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.TankMapper;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class TankDriveTrain extends DriveTrain {
	public TankDriveTrain(int fl, int fr, int bl, int br) {
		super(
				new DriveModule(new XY(-1,0), new XY(0,1), new Motor(new WPI_TalonSRX(fl)), new Motor(new WPI_TalonSRX(fr))),
				new DriveModule(new XY(-1,0), new XY(0,1), new Motor(new WPI_TalonSRX(bl)), new Motor(new WPI_TalonSRX(br)))
		);
		
		this.setMapper(new TankMapper());
	}
}
