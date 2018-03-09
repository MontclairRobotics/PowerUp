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
	public final Motor clamp;
	public final double tolerance = .01;
	
	public final Input<Vector> power;
	
	private long clampStart=0;
	private long clampTime=500;
	private double clampPower=0.5;
	private boolean clampOpen=true;//True for open, false for close
	
	public CubeIntake() {
		this.left = new Motor(Hardware.motorIntakeL);
		this.right = new Motor(Hardware.motorIntakeR);
		this.clamp = new Motor(Hardware.motorIntakeClamp);
		
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
		if(p.getMagnitude() < tolerance){
			openClamp();
		}else{
			closeClamp();
		}
		left.set(p.getX());
		right.set(p.getY());
		updateClamp();
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
