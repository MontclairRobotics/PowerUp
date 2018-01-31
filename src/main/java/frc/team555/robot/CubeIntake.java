package frc.team555.robot;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Togglable;
import org.montclairrobotics.sprocket.utils.Utils;

public class CubeIntake implements Updatable, Togglable {
	private static final double WEIGHT = 0.85;
	private static final double WEIGHT_LR = 1.00 - WEIGHT;
	
	private final Motor motorL;
	private final Motor motorR;
	
	private final Input<Double> powerL;
	private final Input<Double> powerR;
	
	
	public CubeIntake() {
		this.motorL = new Motor(Hardware.motorIntakeL);
		this.motorR = new Motor(Hardware.motorIntakeR);
		
		this.powerL = new Input<Double>() {
			@Override
			public Double get() {
				return Utils.constrain(
						-Control.auxStick.getY()*WEIGHT - (Control.intakeL.get() ? WEIGHT_LR : 0) + (Control.intakeR.get() ? WEIGHT_LR : 0),
						-1.0, +1.0);
			}
		};
		
		this.powerR = new Input<Double>() {
			@Override
			public Double get() {
				return Utils.constrain(
						-Control.auxStick.getY()*WEIGHT + (Control.intakeL.get() ? WEIGHT_LR : 0) - (Control.intakeR.get() ? WEIGHT_LR : 0),
						-1.0, +1.0);
			}
		};
		
		Updater.add(this, Priority.CALC);
	}

	@Override
	public void update() {
		if (Control.liftToggle.get()) { // Check if toggle button is pressed
			disable();
			// TODO: Use Hardware to enable Lift
		}
		
		motorL.set(powerL.get());
		motorR.set(powerR.get());
	}

	@Override
	public void disable() {
		motorL.set(0.0);
		motorR.set(0.0);
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
	}
}
