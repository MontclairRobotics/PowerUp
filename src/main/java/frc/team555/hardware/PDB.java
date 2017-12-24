package frc.team555.hardware;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team555.hardware.HardwareConfigBase.*;

public class PDB extends PowerDistributionPanel {
    private HardwareConfigBase.PDBSlots pdbSlots;

    public PDB(HardwareConfigBase.PDBSlots pdbSlots) {
        this.pdbSlots = pdbSlots;
    }

    public double getCurrent(DeviceEnum device) {
        return super.getCurrent(pdbSlots.get(device).get());
    }
}
