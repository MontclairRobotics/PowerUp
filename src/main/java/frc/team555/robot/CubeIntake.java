package frc.team555.robot;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Utils;

public class CubeIntake implements Updatable {
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
				return -Control.auxStick.getY()*WEIGHT - (Control.auxIntakeL.get() ? WEIGHT_LR : 0) + (Control.auxIntakeR.get() ? WEIGHT_LR : 0);
			}
		};
		
		this.powerR = new Input<Double>() {
			@Override
			public Double get() {
				return -Control.auxStick.getY()*WEIGHT + (Control.auxIntakeL.get() ? WEIGHT_LR : 0) - (Control.auxIntakeR.get() ? WEIGHT_LR : 0);
			}
		};
		
		Updater.add(this, Priority.CALC);
	}

	@Override
	public void update() {
		motorL.set(Utils.constrain(powerL.get(), -1.0, +1.0));
		motorR.set(Utils.constrain(powerR.get(), -1.0, +1.0));
	}
}
