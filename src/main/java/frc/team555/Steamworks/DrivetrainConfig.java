package frc.team555.Steamworks;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.PowerUp.MathAlgorithms;
import frc.team555.hardware.*;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.DoubleInput;

public class DrivetrainConfig extends HardwareConfigBase {

    private MathAlgorithms mathAlgorithms;
    private WPI_TalonSRX drivetrainFL;
    private WPI_TalonSRX drivetrainFR;
    private WPI_TalonSRX drivetrainBL;
    private WPI_TalonSRX drivetrainBR;
    DriveModule dtL;
    DriveModule dtR;
    DoubleInput navXYawInput;
    DoubleInput navXRollInput;
    DoubleInput navXPitchInput;
    PowerDistributionPanel pdp;

    static final int dtFLPort = 3;
    static final int dtFRPort = 1;
    static final int dtBLPort = 4;
    static final int dtBRPort = 2;

    public enum Devices implements DeviceEnum {
        FrontLeftDriveMotor, FrontRightDriveMotor, BackLeftDriveMotor,BackRightDriveMotor
    }

    public DrivetrainConfig() {
        canBus
                .add(Devices.FrontLeftDriveMotor, dtFLPort)
                .add(Devices.FrontRightDriveMotor,dtFRPort)
                .add(Devices.BackLeftDriveMotor,dtBLPort)
                .add(Devices.BackRightDriveMotor,dtBRPort);

        pdbSlots
                .add(Devices.FrontLeftDriveMotor,dtFLPort)
                .add(Devices.BackLeftDriveMotor,dtFRPort)
                .add(Devices.FrontRightDriveMotor,dtBLPort)
                .add(Devices.BackRightDriveMotor,dtBRPort);

        navXYawInput   = new DoubleInput(new AHRS(SPI.Port.kMXP).getYaw());
        navXRollInput  = new DoubleInput(new AHRS(SPI.Port.kMXP).getRoll());
        navXPitchInput = new DoubleInput(new AHRS(SPI.Port.kMXP).getPitch());

        drivetrainFL = srxBuilder.build(Devices.FrontLeftDriveMotor);
        drivetrainFR = srxBuilder.build(Devices.FrontRightDriveMotor);
        drivetrainBL = srxBuilder.build(Devices.BackLeftDriveMotor);
        drivetrainBR = srxBuilder.build(Devices.BackRightDriveMotor);

        dtL = new DriveModule(new XY(-1, 0), new XY(0, 1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        dtR = new DriveModule(new XY(1, 1),  new XY(0, 1), new Motor(drivetrainFR), new Motor(drivetrainBR));

    }

    public void checkCurrentDT(){
        double tempLeftCurrentAvg = mathAlgorithms.avg(pdp.getCurrent(dtFLPort),
                pdp.getCurrent(dtBLPort));

        double tempRightCurrentAvg = mathAlgorithms.avg(pdp.getCurrent(dtFRPort),
                pdp.getCurrent(dtBRPort));

        double dtLeftCurrentStdDev  =  mathAlgorithms.stdDiv(pdp.getCurrent(dtFLPort),
                pdp.getCurrent(dtBLPort));

        double dtRightCurrentStdDev  = mathAlgorithms.stdDiv(pdp.getCurrent(dtFRPort),
                pdp.getCurrent(dtBRPort));

        boolean STDDEVcheckFL;
        STDDEVcheckFL = mathAlgorithms.checkSTDDT(pdp.getCurrent(dtFLPort),
                tempLeftCurrentAvg,
                dtLeftCurrentStdDev);

        boolean STDDEVcheckFR;
        STDDEVcheckFR = mathAlgorithms.checkSTDDT(pdp.getCurrent(dtFRPort),
                tempRightCurrentAvg,
                dtRightCurrentStdDev);

        boolean STDDEVcheckBL;
        STDDEVcheckBL = mathAlgorithms.checkSTDDT(pdp.getCurrent(dtBLPort),
                tempLeftCurrentAvg,
                dtLeftCurrentStdDev);

        boolean STDDEVcheckBR;
        STDDEVcheckBR = mathAlgorithms.checkSTDDT(pdp.getCurrent(dtBRPort),
                tempRightCurrentAvg,
                dtRightCurrentStdDev);


        // STD DEV to Smart Dashboard
        SmartDashboard.putBoolean("FL STD DEV Check", STDDEVcheckFL);
        SmartDashboard.putBoolean("FR STD DEV Check", STDDEVcheckFR);
        SmartDashboard.putBoolean("BL STD DEV Check", STDDEVcheckBL);
        SmartDashboard.putBoolean("BR STD DEV Check", STDDEVcheckBR);

    }

}
