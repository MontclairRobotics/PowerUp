package frc.team555.PowerUp;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.DoubleInput;

public class SprocketHardwareConfig extends HardwareConfig {

    public DriveModule dtL;
    public DriveModule dtR;
    public DoubleInput navXYawInput;
    public DoubleInput navXRollInput;
    public DoubleInput navXPitchInput;

    public SprocketHardwareConfig() {

        navXYawInput   = new DoubleInput(new AHRS(SPI.Port.kMXP).getYaw());
        navXRollInput  = new DoubleInput(new AHRS(SPI.Port.kMXP).getRoll());
        navXPitchInput = new DoubleInput(new AHRS(SPI.Port.kMXP).getPitch());

        dtL = new DriveModule(new XY(-1, 0), new XY(0, 1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        dtR = new DriveModule(new XY(1, 1),  new XY(0, 1), new Motor(drivetrainFR), new Motor(drivetrainBR));

    }
}
