package frc.team555.hardware;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDB extends PowerDistributionPanel {
    private PDBSlots pdbSlots;

    public PDB(PDBSlots pdbSlots) {
        this.pdbSlots = pdbSlots;
    }

    public double getCurrent(DeviceEnum device) {
        return super.getCurrent(pdbSlots.get(device).get());
    }
}
