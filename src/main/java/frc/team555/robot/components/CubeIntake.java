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
import org.montclairrobotics.sprocket.motors.CurrentMonitor;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;



public class CubeIntake implements Updatable, Togglable{
<<<<<<< HEAD
	public final Motor left;
	public final Motor right;
	public final Motor clamp;
	public final TargetMotor rotationalMotor;
	public final double tolerance = .01; // Todo: Maybe needs to be tuned
=======
	private double rotatePower=.1;

	private boolean auto;

	public  Motor left;
	public  Motor right;
	public  TargetMotor rotate;
	public final double tolerance = 1; // Todo: Maybe needs to be tuned
>>>>>>> cleanup

	public final int upPos = 0;
	public final int downPos = 2;
	public final int middlePos = 1;


<<<<<<< HEAD
	public final Input<Vector> power;
	
	private long clampStart=0;
	private long clampTime=500; // Todo: needs to be tuned
	private double clampPower=0.5;
	private boolean clampOpen=true; // True for open, false for close
    private double rotationalMotorPower = 0;
	private final double roatatePower = .5;

	public CubeIntake() {
		this.left = new Motor(Hardware.motorIntakeL);
		this.right = new Motor(Hardware.motorIntakeR);
		this.clamp = new Motor(Hardware.motorIntakeClamp);
		this.rotationalMotor = new TargetMotor(Hardware.intakeRotationEncoder, new PID(.1, 0, 0), new Motor(Hardware.motorRotational)); // Todo: needs to be implemented
		
=======
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

>>>>>>> cleanup
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
<<<<<<< HEAD
		Control.intakeRotationDown.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotationalMotor.set(downPos);
			}
		});
		Control.intakeRotationUp.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotationalMotor.set(upPos);
=======


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
>>>>>>> cleanup
			}
		});
		Control.intakeRotateMiddle.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
<<<<<<< HEAD
				rotationalMotor.set(middlePos);
=======
				rotate.setTarget(middlePos);
>>>>>>> cleanup
			}
		});

        Control.intakeLiftManualUp.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                rotationalMotor.set(rotationalMotorPower);
            }
        });

<<<<<<< HEAD
        Control.intakeLiftManualUp.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                rotationalMotor.set(rotationalMotorPower);
            }
        });


		new CurrentMonitor("Intake Right Motor", Hardware.motorIntakeR, new Input<Boolean>() {
			@Override
			public Boolean get() {
				return power.get().getMagnitude() > .1;
			}
		});

		new CurrentMonitor("Intake Left Motor", Hardware.motorIntakeL, new Input<Boolean>() {
			@Override
			public Boolean get() {
				return power.get().getMagnitude() > .1;
			}
		});

		new CurrentMonitor("Intake Rotational Motor", Hardware.motorRotational, new Input<Boolean>() {
			@Override
			public Boolean get() {
				return Math.abs(rotationalMotor.getTarget() - rotationalMotor.getDistance().get()) > 20;
			}
		}).setEncoder(Hardware.intakeRotationEncoder);

=======
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
>>>>>>> cleanup
		Updater.add(this, Priority.CALC);
	}


	@Override
	public void update() {
		Vector p = power.get();
<<<<<<< HEAD
		// if(p.getMagnitude() < tolerance){
		// 		openClamp();
		// }else{
		// 		closeClamp();
		// }
		left.set(p.getX());
		right.set(p.getY());

		// updateClamp();
=======
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
>>>>>>> cleanup
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
