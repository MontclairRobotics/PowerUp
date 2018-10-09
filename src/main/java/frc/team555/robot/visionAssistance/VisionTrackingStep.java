package frc.team555.robot.visionAssistance;

import frc.team555.robot.core.Control;
import frc.team555.robot.utils.BangBang;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;

public class VisionTrackingStep implements Step<DTTarget> {

    private BangBang distCorrection;
    private BangBang angleCorrection;

    public VisionTrackingStep(BangBang distCorrection, BangBang angleCorrection){
        this.distCorrection = distCorrection;
        this.angleCorrection =  angleCorrection;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(Control.visionOn.get()){
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
