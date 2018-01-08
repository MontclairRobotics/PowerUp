package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestLRRobot extends SprocketRobot {
	String[] tests = {"L", "R", "LLL", "LLR", "LRL", "LRR", "RLL", "RLR", "RRL", "RRR"};
	Side[][] check = {
			{Side.LEFT},
			{Side.RIGHT},
			{Side.LEFT, Side.LEFT, Side.LEFT},
			{Side.LEFT, Side.LEFT, Side.RIGHT},
			{Side.LEFT, Side.RIGHT, Side.LEFT},
			{Side.LEFT, Side.RIGHT, Side.RIGHT},
			{Side.RIGHT, Side.LEFT, Side.LEFT},
			{Side.RIGHT, Side.LEFT, Side.RIGHT},
			{Side.RIGHT, Side.RIGHT, Side.LEFT},
			{Side.RIGHT, Side.RIGHT, Side.RIGHT},
	};
	Side[][] results = new Side[tests.length][];
	
	public void update() {
		for (int i = 0; i < tests.length; i++) {
			results[i] = Side.fromString(tests[i]);
			
			SmartDashboard.putBoolean((i + 1) + ": " + tests[i], results[i].equals(check[i]));
		}
	}
}
