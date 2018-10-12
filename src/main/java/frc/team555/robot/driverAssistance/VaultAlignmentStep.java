package frc.team555.robot.driverAssistance;

import Robot.NavRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class VaultAlignmentStep implements Step<DTTarget> {

    private final NavRobot navigationSys;
    private final PID distPID = new PID(0,0,0); //TODO: TUNE PID
    private final PID anglePID = new PID(0,0,0); //TODO: TUNE PID
    private final XY vaultPos = new XY(0,0); //TODO: GET LOCATION
    private final Angle vaultAngle = new Degrees(0); //TODO: GET ANGLE
    private Button button;

    public VaultAlignmentStep(NavRobot navigationSys, Button button){
        this.navigationSys = navigationSys;
        this.button = button;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(button.get()){
            double distance = Math.sqrt(Math.pow((navigationSys.getPositon().getX()-vaultPos.getY()),2)
                    + Math.pow((navigationSys.getPositon().getX()-vaultPos.getY()),2));

            Angle angle = new Degrees(
                        Math.toDegrees(
                                Math.atan2(
                                    Math.abs(navigationSys.getPositon().getX()-vaultPos.getY()),
                                    Math.abs(navigationSys.getPositon().getX()-vaultPos.getY())
                                )
                        )
            );

            distPID.setTarget(distance);
            anglePID.setTarget(angle.toDegrees());

            DTTarget out = new DTTarget(dtTarget.getDirection().add(new XY(0,distPID.get())),
                    new Degrees(dtTarget.getTurn().toDegrees() - anglePID.get()));

            Debug.msg("Vault Align Out: ", out);
            return out;
        }else{
            return null;
        }
    }
}
