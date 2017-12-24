package frc.team555.robot;

import com.ctre.CANTalon;
import frc.team555.hardware.*;

public class HardwareConfig {

    // Build control system
    public CANBus canBus = new CANBus();
    public PDBSlots pdbSlots = new PDBSlots();

    public PDB pdb = new PDB(pdbSlots);

    // Create builders for types of devices we'll use
    public SRXBuilder srxBuilder = new SRXBuilder(canBus);

    // Build attached devices

    // Create list of devices so that multiple uses of any device will be standardized
    // For example, the FrontLeftDriveMotor is used in canBus, pdbSlots, and when creating the speed controller
    // all of these are "immune" from spelling errors by using the enum.
    public enum Devices implements DeviceEnum {
        FrontLeftDriveMotor, FrontRightDriveMotor, BackLeftDriveMotor,BackRightDriveMotor;
    }

    public HardwareConfig() {
        canBus
                .add(Devices.FrontLeftDriveMotor, 1)
                .add(Devices.FrontRightDriveMotor,2)
                .add(Devices.BackLeftDriveMotor,3)
                .add(Devices.BackRightDriveMotor,4);

        pdbSlots
                .add(Devices.FrontLeftDriveMotor,0)
                .add(Devices.BackLeftDriveMotor,1)
                .add(Devices.FrontRightDriveMotor,2)
                .add(Devices.BackRightDriveMotor,3);
    }


    // These should probably be built like the devices above, but using
    // a collection (not Bus) that is geared to hold another type (i.e. SpeedController)
    public CANTalon drivetrainFL = srxBuilder.build(Devices.FrontLeftDriveMotor);
    public CANTalon drivetrainFR = srxBuilder.build(Devices.FrontRightDriveMotor);
    public CANTalon drivetrainBL = srxBuilder.build(Devices.BackLeftDriveMotor);
    public CANTalon drivetrainBR = srxBuilder.build(Devices.BackRightDriveMotor);



}
