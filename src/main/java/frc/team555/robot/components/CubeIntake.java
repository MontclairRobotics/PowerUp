package frc.team555.robot.components;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.LimitedMotor;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;



public class CubeIntake implements Updatable, Togglable{
	private double rotatePower=.1;

	private boolean auto;

	public  Motor left;
	public  Motor right;
	public  TargetMotor rotate;
	public final double tolerance = 1; // Todo: Maybe needs to be tuned

	public final int upPos = 0;
	public final int downPos = 2;
	public final int middlePos = 1;


	public  Input<Vector> power;
	
	public CubeIntake() {
		this.left = new Motor(Hardware.motorIntakeL);
		this.right = new Motor(Hardware.motorIntakeR);
		right.setInverted(true);
		//this.clamp = new Motor(Hardware.motorIntakeClamp);
		LimitedMotor rotateMotor=new LimitedMotor(Hardware.motorIntakeRotate,true,Hardware.intakeLowerBound,Hardware.intakeUpperBound);
		//Motor motor=new Motor(Hardware.motorIntakeRotate);
		this.rotate=new TargetMotor(Hardware.intakeRotationEncoder,new BangBang(tolerance,rotatePower),rotateMotor);
		// this.roationalMotor = new TargetMotor(Hardware.intakeRotationEncoder, new BangBang(tolerance,rotatePower), rotateMotor); // Todo: needs to be implemented

		this.power = new Input<Vector>() {
			@Override
			public Vector get() {
				double x=-Control.auxStick.getX();
				if(Math.abs(x)<0.1)
				{
					x=0;
				}
				double y=-Control.auxStick.getY();
				if(Math.abs(y)<0.1)
				{
					y=-.05;
				}
				return new XY(x,y);
			}
		};


		Control.intakeRotateDown.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setTarget(downPos);
                SmartDashboard.putString("Rotating Down","DOWN");
			}
		});

		Control.intakeRotateUp.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setTarget(upPos);
			}
		});
		Control.intakeRotateMiddle.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setTarget(middlePos);
			}
		});


		Control.intakeRotateUpManual.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setPower(rotatePower);
			}
		});
		Control.intakeRotateUpManual.setReleaseAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setPower(0);
			}
		});

		Control.intakeRotateDownManual.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setPower(-rotatePower);
			}
		});
		Control.intakeRotateDownManual.setReleaseAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotate.setPower(0);
			}
		});

		rotate.setPower(0);
		Updater.add(this, Priority.CALC);
	}


	@Override
	public void update() {
		Vector p = power.get();
		if(!auto) {
			left.set(p.getY() - p.getX()*.25);
			right.set(p.getY() + p.getX()*.25);
		}
		auto=false;
        Debug.msg("RotateMotor PID OUT",rotate.pid.get());
        Debug.msg("RotateMotor PID TGT",rotate.pid.getTarget());
	}


	public void auto()
	{
		auto=true;
		enable();
	}

	public void setAutoPower(double power){
		auto = true;
		left.set(power);
		right.set(power);
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
