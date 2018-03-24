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
	private double rotatePower=1;

	private boolean auto;

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
		right.setInverted(true);
		//this.clamp = new Motor(Hardware.motorIntakeClamp);
		Motor rotateMotor=new Motor(Hardware.motorIntakeRotate);
		this.roationalMotor = new TargetMotor(Hardware.intakeRotationEncoder, new BangBang(tolerance,rotatePower), rotateMotor); // Todo: needs to be implemented

		this.power = new Input<Vector>() {
			@Override
			public Vector get() {
				double x=-Control.auxStick.getX();
				if(Math.abs(x)<0.2)
				{
					x=0;
				}
				double y=-Control.auxStick.getY();
				if(Math.abs(y)<0.2)
				{
					y=0;
				}
				return new XY(x,y);
			}
		};


		/*Control.intakeRotateDown.setPressAction(new ButtonAction() {
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
		Control.intakeRotateUpManual.setReleaseAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.setPower(0);
			}
		});

		Control.intakeRotateDownManual.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.setPower(-rotatePower);
			}
		});
		Control.intakeRotateDownManual.setReleaseAction(new ButtonAction() {
			@Override
			public void onAction() {
				roationalMotor.setPower(0);
			}
		});
*/
		roationalMotor.setPower(0);
		Updater.add(this, Priority.CALC);
	}


	@Override
	public void update() {
		Vector p = power.get();

		if(!auto) {
			left.set(p.getY() - p.getX());
			right.set(p.getY() + p.getX());
		}
		auto=false;
	}


	public void auto()
	{
		auto=true;
		enable();
	}
	@Override
	public void enable() {
		left.set(1);
		right.set(1);
	}

	@Override
	public void disable() {
		left.set(0);
		right.set(0);
	}

}
