package frc.team555.robot;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

public class CubeIntake implements Updatable {
	public static final double CUBE_WIDTH_IN = 13.0;
	
	private final Motor motorL;
	private final Motor motorR;
	
	private final Input<Double> powerL;
	private final Input<Double> powerR;
	
	private double power = 1.0;
	
	public CubeIntake() {
		this.motorL = new Motor(Hardware.motorIntakeL);
		this.motorR = new Motor(Hardware.motorIntakeR);
		
		this.powerL = new Input<Double>() {
			@Override
			public Double get() {
				return power*(-Control.auxStick.getY() - (Control.auxIntakeL.get() ? 1.0 : 0.0) + (Control.auxIntakeR.get() ? 1.0 : 0.0));
			}
		};
		
		this.powerR = new Input<Double>() {
			@Override
			public Double get() {
				return power*(-Control.auxStick.getY() + (Control.auxIntakeL.get() ? 1.0 : 0.0) - (Control.auxIntakeR.get() ? 1.0 : 0.0));
			}
		};
		
		Updater.add(this, Priority.CALC);
	}
	
	public void setPower(double p) {
		power = p;
	}

	@Override
	public void update() {
		motorL.set(Utils.constrain(powerL.get(), -1.0, +1.0));
		motorR.set(Utils.constrain(powerR.get(), -1.0, +1.0));
	}
	
	public void set(double power) {
		motorL.set(power);
		motorR.set(power);
	}
	
	public void stop() {
		motorL.set(0.0);
		motorR.set(0.0);
	}
}
