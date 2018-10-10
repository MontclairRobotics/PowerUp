package frc.team555.robot.driverAssistance;

import frc.team555.robot.auto.CubeOuttake;
import frc.team555.robot.core.PowerUpRobot;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.states.StateMachine;

public class VaultAlignment extends StateMachine {

    private XY currentPos;
    private double currentAngle;

    private XY vaultPos;
    private double vaultAngle;

    public VaultAlignment(XY currentPos, double currentAngle, XY vaultPos, double vaultAngle){

        super(
                new DriveEncoderGyro(Math.sqrt(Math.pow(currentPos.getX()-vaultPos.getX(),2)+
                        Math.pow(currentPos.getY()-vaultPos.getY(),2)),
                        1,
                        Angle.ZERO,
                        true,
                        PowerUpRobot.correction)

        );
    }
}
