package frc.team555.hardware;

import java.util.HashMap;
import java.util.Map;

public class HardwareConfigBase {
    // These classes should be burried in either a base class
    // or better yet in Sprocket

    public interface DeviceEnum {
        int ordinal();
    }

    public class Bus<T> {
        private Map<String,PortID> ports = new HashMap<>();
        //private int channel;
        private int min, max;

        public Bus(int min, int max) {
            this.min=min;
            this.max=max;
        }
        public T add(String name, int port ) {
            if (port<min || port>max || ports.containsKey(name)) {
                //throw Invalid Bus Error
            }
            else {
                ports.put(name,new PortID<T>(port));
            }
            return (T)this;
        }
        public T add(DeviceEnum device, int port) {
            add(device.toString(),port);
            return (T)this;
        }
        public PortID<T> get(String name){
            return ports.get(name);
        }
        public PortID<T> get(DeviceEnum device){
            return ports.get(device.toString());
        }
    }

    public class CANBus extends Bus<CANBus> {
        public CANBus() {
            super(1, 100);
        }

    }
    public class PDBSlots extends Bus<PDBSlots> {
        public PDBSlots() {
            super(1, 12);
        }
    }

    // CANBus, PWMBus, etc. conceptually exist in all FRC(FTC) Robots
    // So why not make them and then use them or not.
    public CANBus canBus = new CANBus();
    public PDBSlots pdbSlots = new PDBSlots();

    // FRC Bots gotta have a pdb, so let's do it.
    public PDB pdb = new PDB(pdbSlots);

    // Either we make some of these or we don't, but let's make it easier on the
    // programmers if we do.
    public SRXBuilder srxBuilder = new SRXBuilder(canBus);

}
