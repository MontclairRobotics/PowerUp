package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Togglable;



public class CubeIntake implements Updatable, Togglable{
	public final Motor left;
	public final Motor right;
	
	public final Input<Vector> power;
	
	public CubeIntake() {
		this.left = new Motor(Hardware.motorIntakeL);
		this.right = new Motor(Hardware.motorIntakeR);
		
		this.power = new Input<Vector>() {
			@Override
			public Vector get() {
				return new XY(
						-Control.auxStick.getY() + Control.auxStick.getX(),
						-Control.auxStick.getY() - Control.auxStick.getX()
				);
			}
		};
		
		Updater.add(this, Priority.CALC);
	}
	

	@Override
	public void update() {
		Vector p = power.get();
		left.set(p.getX());
		right.set(p.getY());
	}

	@Override
	public void enable() {
		left.set(-.5);
		right.set(-.5);
	}

	@Override
	public void disable() {
		left.set(0);
		right.set(0);
	}
}
