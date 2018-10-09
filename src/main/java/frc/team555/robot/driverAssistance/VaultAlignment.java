package frc.team555.robot.driverAssistance;

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



    public VaultAlignment(){
        super(
                new DriveEncoderGyro(Math.sqrt(x*2+y*2),
                        0.4,
                        Angle.ZERO,
                        true,
                        PowerUpRobot.correction
                )







        );
    }
}
