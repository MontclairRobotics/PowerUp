package frc.team555.hardware;

import java.util.HashMap;
import java.util.Map;

// A Bus is a type-save collection of devices that
// are referenced by a single integer port, id, etc.
public class Bus<T> {
    private Map<Integer,PortID> ports = new HashMap<>();
    //private int channel;
    private int min, max;

    public Bus(int min, int max) {
        this.min=min;
        this.max=max;
    }
    @SuppressWarnings("unchecked")
    public T add(DeviceEnum device, int port ) {
        if (port<min || port>max || ports.containsKey(device.ordinal())) {
            //throw Invalid Bus Config Error
        }
        else {
            ports.put(device.ordinal(),new PortID<T>(port));
        }
        return (T)this;
    }
    @SuppressWarnings("unchecked")
    public PortID<T> get(DeviceEnum device){
        return (PortID<T>)ports.get(device.ordinal());
    }
}


