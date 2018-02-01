package frc.team555.robot;

import org.montclairrobotics.sprocket.auto.AutoState;

public class AutoIntake extends AutoState {
	private enum Direction {
		IN  (-1.0),
		OUT (+1.0);
		
		double sign;
		
		Direction(double s) {
			sign = s;
		}
	}
	
	private CubeIntake intake;
	private Direction direction;
	
	private AutoIntake(CubeIntake in, Direction dir) {
		this.intake = in;
		this.direction = dir;
	}
	
	public static AutoIntake in(CubeIntake i) {
		return new AutoIntake(i, Direction.IN);
	}
	
	public static AutoIntake out(CubeIntake i) {
		return new AutoIntake(i, Direction.OUT);
	}
	
	@Override
	public void userStart() {
		Hardware.encoderIntakeL.reset();
		Hardware.encoderIntakeR.reset();
	}

	@Override
	public void stateUpdate() {
		intake.set(direction.sign * 0.5);
	}
	
	@Override
	public boolean isDone() {
		return Math.abs(Hardware.encoderIntakeL.getInches().get()) > CubeIntake.CUBE_WIDTH_IN ||
				Math.abs(Hardware.encoderIntakeR.getInches().get()) > CubeIntake.CUBE_WIDTH_IN;
	}
	
	@Override
	public void userStop() {
		intake.stop();
	}
}
