package frc.team555.robot;

import edu.wpi.first.wpilibj.DriverStation;

public enum Side {
	LEFT('L'), RIGHT('R');
	
	char letter;
	
	private Side(char v) {
		this.letter = v;
	}
	
	public static Side[] fromString(String msg) {
		Side[] sides = new Side[msg.length()]; // Should hold 3 items
		
		for (int i = 0; i < msg.length(); i++) {
			switch (msg.charAt(i)) {
			case 'L': sides[i] = LEFT;
			case 'R': sides[i] = RIGHT;
			default: break; //TODO: Program some sort of error case.
			}
		}
		
		return sides;
	}
	
	/** @return a list of what Side your alliance color is on (near switch, scale, far switch). **/
	public static Side[] fromDriverStation() {
		return Side.fromString(DriverStation.getInstance().getGameSpecificMessage());
	}
}
