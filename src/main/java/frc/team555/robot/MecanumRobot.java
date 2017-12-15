package frc.team555.robot;

import org.montclairrobotics.sprocket.SprocketRobot;

public class MecanumRobot extends SprocketRobot {

    MecanumDriveTrain driveTrain;

    @Override
    public void robotInit() {
        driveTrain.driveTrainBuild();
    }
}
