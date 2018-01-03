package frc.team555.robot;

import edu.wpi.first.wpilibj.SPI;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;

public class SprocketHardwareConfig extends HardwareConfig {

    public DriveModule frontL;
    public DriveModule frontR;
    public DriveModule backL;
    public DriveModule backR;
    public NavXYawInput navX;

    public SprocketHardwareConfig() {
        navX = new NavXYawInput(SPI.Port.kMXP);

        frontL = new DriveModule(new XY(-1, 1), new XY(-1, 1), new Motor(drivetrainFL));
        frontR = new DriveModule(new XY(1, 1), new XY(-1, 1), new Motor(drivetrainFR));
        backL = new DriveModule(new XY(-1, -1), new XY(-1, 1), new Motor(drivetrainBL));
        backR = new DriveModule(new XY(1, -1), new XY(-1, 1), new Motor(drivetrainBR));

    }
}
