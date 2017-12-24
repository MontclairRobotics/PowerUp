package frc.team555.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team555.hardware.*;

public class HardwareConfig extends HardwareConfigBase {

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


    public CANTalon drivetrainFL = srxBuilder.build(Devices.FrontLeftDriveMotor);
    public CANTalon drivetrainFR = srxBuilder.build(Devices.FrontRightDriveMotor);
    public CANTalon drivetrainBL = srxBuilder.build(Devices.BackLeftDriveMotor);
    public CANTalon drivetrainBR = srxBuilder.build(Devices.BackRightDriveMotor);



}
