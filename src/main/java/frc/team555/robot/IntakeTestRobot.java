package frc.team555.robot;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.Delay;

public class IntakeTestRobot extends PowerUpRobot {
	
	public void robotInit() {
		super.robotInit();
		
		this.addAutoMode(new AutoMode("Test: Cube Intake",
				AutoIntake.in(intake),	// Intake in
				new Delay(3),
				AutoIntake.out(intake)
			)
		);
	}
}
