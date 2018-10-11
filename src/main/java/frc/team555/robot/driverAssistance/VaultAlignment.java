package frc.team555.robot.driverAssistance;

import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;

public class VaultAlignment implements Step<DTTarget> {

    private XY vaultPos;
    private Angle vaultAngle;
    private Button button;

    public VaultAlignment(XY vaultPos, Angle vaultAngle, Button button){
            this.vaultPos = vaultPos;
            this.vaultAngle = vaultAngle;
            this.button = button;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(button.get()){
            // drive to thing
        }

        return null;
    }
}
