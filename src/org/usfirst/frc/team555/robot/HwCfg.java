package org.usfirst.frc.team555.robot;

import com.ctre.CANTalon;

public final class HwCfg {

	// In order to really use this effectively it probably needs to be moved to 
	// Sprocket. That way sprocket functions can be built to accept the enums instead
	// of simply ints.
	private HwCfg() {
	}
	
	// all configured CAN devices would be enumerated here 
	// to make it easy to check for duplicates and/or available channels
	public static enum CANChannel {
		NONE(-1),FRONTLEFT(1), FRONTRIGHT(2), BACKLEFT(3), BACKRIGHT(4);
		
		private final int channel;
		CANChannel(int channel) {
			this.channel = channel;			
		}
		public int getChannel() {
			return channel; 
		}
	};
		
	// all configured PDB devices would be enumerated here 
	// to make it easy to check for duplicates and/or available channels
	public static enum PDBChannel {
		NONE(-1), FRONTLEFT(4), FRONTRIGHT(5), BACKLEFT(6), BACKRIGHT(7);
		
		private final int channel;
		PDBChannel(int channel) {
			this.channel = channel;			
		}
		public int getChannel() {
			return channel; 
		}
	};

	// This function should really be in a more generic class in Sprocket.
	// I would suggest a class that accepts both a CANChannel and a PDBChannel 
	// to implement control and monitoring 
	public static CANTalon BuildCANTalon(CANChannel channel){
		if (channel.getChannel()<0) return null;
		return new CANTalon(channel.getChannel());
	}
}
