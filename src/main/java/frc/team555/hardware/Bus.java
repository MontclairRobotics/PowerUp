package frc.team555.hardware;

import java.util.HashMap;
import java.util.Map;

// A Bus is a type-save collection of devices that
// are referenced by a single integer port, id, etc.
// Bus should be used as a super class for another
// type-safe class in the form of:
// class ClassName extends Bus<ClassName> ...
public class Bus<T> {
    private Map<Integer,PortID<T>> ports = new HashMap<>();
    private int min, max;

    public Bus(int min, int max) {
        this.min=min;
        this.max=max;
    }

    @SuppressWarnings("unchecked")
    public T add(DeviceEnum device, int port ) {
        // TODO: Fix this - it's totally wrong
        // The idea is to check that this port id hasn't already been used.
        // Checking for the device.ordinal() is meaningless.
        // There needs to be a check for the port not being used
        // in any of the PortID objects already stored.
        // This could be done by iterating the map or by keeping a second
        // list with the port numbers in the raw.
        if (port<min || port>max || ports.containsKey(device.ordinal())) {
            //throw Invalid Bus Config Error
        }
        else {
            ports.put(device.ordinal(),new PortID<>(port));
        }
        return (T)this;
    }

    public PortID<T> get(DeviceEnum device){
        return ports.get(device.ordinal());
    }
}


