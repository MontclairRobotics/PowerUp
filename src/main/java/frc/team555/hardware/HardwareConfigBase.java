package frc.team555.hardware;

public class HardwareConfigBase {

    // Build control system
    public CANBus canBus = new CANBus();
    public PDBSlots pdbSlots = new PDBSlots();

    public PDB pdb = new PDB(pdbSlots);

    // Create builders for types of devices we'll use
    public SRXBuilder srxBuilder = new SRXBuilder(canBus);

}
