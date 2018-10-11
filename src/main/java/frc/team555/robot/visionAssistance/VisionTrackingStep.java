package frc.team555.robot.visionAssistance;

import frc.team555.robot.core.Control;
import frc.team555.robot.utils.BangBang;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class VisionTrackingStep implements Step<DTTarget> {

    private PID distCorrection;
    private PID angleCorrection;
    private Button button;

    public VisionTrackingStep(PID distCorrection, PID angleCorrection, Button button){
        this.distCorrection = distCorrection;
        this.angleCorrection =  angleCorrection;
        this.button = button;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(button.get()){
            Debug.msg("Correcting", true);
            Debug.msg("Distance Correction", distCorrection.get());
            Debug.msg("Angle Correction", angleCorrection.get());
            DTTarget out = new DTTarget(dtTarget.getDirection().add(new XY(0,distCorrection.get())),
                    new Degrees(dtTarget.getTurn().toDegrees() - angleCorrection.get()));
            Debug.msg("Vision Out: ", out);

            return out;
        }else{
            Debug.msg("Correcting", false);
            Debug.msg("Distance Correction", distCorrection.get());
            Debug.msg("Angle Correction", angleCorrection.get());
            Debug.msg("Vision out: ", dtTarget);
            return dtTarget;
        }
    }

}
