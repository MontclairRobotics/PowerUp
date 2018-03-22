package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;



public class CubeIntake implements Updatable, Togglable{
	private double rotatePower=0.5;


	public final Motor left;
	public final Motor right;
	public final TargetMotor roationalMotor;
	public final double tolerance = 1; // Todo: Maybe needs to be tuned

	public final int upPos = 0;
	public final int downPos = 2;
	public final int middlePos = 1;


	public final Input<Vector> power;
	
	public CubeIntake() {
		this.left = new Motor(Hardware.motorIntakeL);
		this.right = new Motor(Hardware.motorIntakeR);
		//this.clamp = new Motor(Hardware.motorIntakeClamp);
		this.roationalMotor = new TargetMotor(Hardware.intakeRotationEncoder, new BangBang(tolerance,rotatePower), new Motor(Hardware.motorIntakeRotate)); // Todo: needs to be implemented

		this.power = new Input<Vector>() {
			@Override
			public Vector get() {
				return new XY(
						-Control.auxStick.getY() + Control.auxStick.getX(),
						-Control.auxStick.getY() - Control.auxStick.getX()
				);
			}
		};


		Control.intakeRotateDown.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.set(downPos);
			}
		});

		Control.intakeRotateUp.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.set(upPos);
			}
		});
		Control.intakeRotateMiddle.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.set(middlePos);
			}
		});


		Control.intakeRotateUpManual.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.setPower(rotatePower);
			}
		});

		Control.intakeRotateUpManual.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.setPower(-rotatePower);
			}
		});
		
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
