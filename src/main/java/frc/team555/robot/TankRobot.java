package frc.team555.robot;


import org.montclairrobotics.sprocket.SprocketRobot;

public class TankRobot extends SprocketRobot {

    TankDriveTrain driveTrain;

    @Override
    public void robotInit() {
        driveTrain.driveTrainBuild();
    }

    @Override
    public void update() {

    }


}