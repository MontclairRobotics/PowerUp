package frc.team555.robot;

import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

public class CubeIntake implements Updatable {
	public final double TGT_DOWN = 0D;
	public final double TGT_UP = 45D;
	
	public final Motor left;
	public final Motor right;
	
	public final SEncoder rotateEnc;
	
	public final Motor rotate;
	private PID rotatePID = new PID(0, 0, 0);;
	
	public final Input<Double> power;
	
	public CubeIntake() {
		this.left = new Motor(Hardware.motorIntakeL);
		this.right = new Motor(Hardware.motorIntakeR);
		this.rotate = new Motor(Hardware.motorIntakeRotate);
		
		this.rotateEnc = Hardware.encoderIntakeRotate;
		
		rotatePID.setMinMax(-90.0, 90.0, -1.0, +1.0);
		rotatePID.setTarget(rotateEnc.get());
		rotatePID.setInput(new Input<Double>() {
			@Override
			public Double get() {
				return rotateEnc.get();
			}
		});
		
		this.power = new Input<Double>() {
			@Override
			public Double get() {
				return -Control.auxStick.getY();
			}
		};
		
		Control.intakeRotateUp.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotatePID.setTarget(TGT_UP);
			}
		});
		
		Control.intakeRotateUp.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotatePID.setTarget(TGT_DOWN);
			}
		});
		
		ButtonAction stop = new ButtonAction() {
			@Override
			public void onAction() {
				rotatePID.setTarget(rotateEnc.get());
			}
		};
		
		Control.intakeRotateUp.setOffAction(stop);
		Control.intakeRotateDown.setOffAction(stop);
		
		Updater.add(this, Priority.CALC);
	}
	
	@Override
	public void update() {
		left.set(power.get());
		right.set(power.get());
		rotate.set(rotatePID.get());
	}
	
	public void enable() {
		left.set(-.5);
		right.set(-.5);
		rotate.set(0);
	}

	public void disable() {
		left.set(0);
		right.set(0);
		rotate.set(0);
	}
}
