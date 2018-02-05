package frc.team555.robot;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.Delay;

public class IntakeTestRobot extends PowerUpRobot {
	
	public void robotInit() {
		super.robotInit();
		
		this.addAutoMode(new AutoMode("Test: Cube Intake",
				AutoIntake.in(intake),	// Intake In
				new Delay(3),			// Delay 3s
				AutoIntake.out(intake)	// Intake Out
			)
		);
	}
}