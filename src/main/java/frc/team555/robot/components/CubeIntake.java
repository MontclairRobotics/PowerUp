package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.CurrentMonitor;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;
import org.montclairrobotics.sprocket.utils.Togglable;



public class CubeIntake implements Updatable, Togglable{
	public final Motor left;
	public final Motor right;
	public final Motor clamp;
	public final TargetMotor rotationalMotor;
	public final double tolerance = .01; // Todo: Maybe needs to be tuned

	public final int upPos = 0;
	public final int downPos = 2;
	public final int middlePos = 1;


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
		
		this.power = new Input<Vector>() {
			@Override
			public Vector get() {
				return new XY(
						-Control.auxStick.getY() + Control.auxStick.getX(),
						-Control.auxStick.getY() - Control.auxStick.getX()
				);
			}
		};
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
			}
		});
		Control.intakeRotationMiddle.setPressAction(new ButtonAction() {
			@Override
			public void onAction() {
				rotationalMotor.set(middlePos);
			}
		});

        Control.intakeLiftManualUp.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                rotationalMotor.set(rotationalMotorPower);
            }
        });

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

		Updater.add(this, Priority.CALC);
	}


	@Override
	public void update() {
		Vector p = power.get();
		// if(p.getMagnitude() < tolerance){
		// 		openClamp();
		// }else{
		// 		closeClamp();
		// }
		left.set(p.getX());
		right.set(p.getY());

		// updateClamp();
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

	private void openClamp(){
		if(!clampOpen)
		{
			clampOpen=true;
			startClamp();
		}
	}

	private void closeClamp(){
		if(clampOpen)
		{
			clampOpen=false;
			startClamp();
		}
	}
	
	private void startClamp()
	{
		//Guess how far we have moved, and set our start time correctly
		//clampStart=System.currentTimeMillis()-(clampTime-(System.currentTimeMillis()-clampStart)));
		clampStart=System.currentTimeMillis()*2-clampTime-clampStart;
		if(clampStart>System.currentTimeMillis())
		{
			clampStart=System.currentTimeMillis();
		}
	}
	
	private void updateClamp()
	{
		if(System.currentTimeMillis()-clampStart<clampTime)
		{
			if(clampOpen)
			{
				clamp.set(clampPower);
			}
			else
			{
				clamp.set(-clampPower);
			}
		}
		else
		{
			clamp.set(0);
		}
	}
}
