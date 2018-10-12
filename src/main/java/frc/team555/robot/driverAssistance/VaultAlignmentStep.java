package frc.team555.robot.driverAssistance;

import Robot.NavRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Radians;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class VaultAlignmentStep implements Step<DTTarget> {

    private final NavRobot navigationSys;
    private final PID distPID;
    private final PID anglePID;
    private final XY vaultPos;
    private final Angle vaultAngle;
    private Button button;

    public VaultAlignmentStep(NavRobot navigationSys, Button button, PID distPID, PID anglePID, XY vaultPos, Angle vaultAngle){
        this.navigationSys = navigationSys;
        this.button = button;
        this.distPID = distPID;
        this.anglePID = anglePID;
        this.vaultPos = vaultPos;
        this.vaultAngle = vaultAngle;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(button.get()){
            double distance = Math.sqrt(Math.pow((navigationSys.getPositon().getX()-vaultPos.getY()),2)
                    + Math.pow((navigationSys.getPositon().getX()-vaultPos.getY()),2));

            Angle angle = new Degrees(new Radians(Math.tan(Math.abs((navigationSys.getPositon().getX()-vaultPos.getY()))
                    /Math.abs((navigationSys.getPositon().getX()-vaultPos.getY())))).toDegrees());

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
