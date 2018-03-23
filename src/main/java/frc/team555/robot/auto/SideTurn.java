package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.TurnGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.loop.Updater;

public class SideTurn extends TurnGyro {

    public Side side;
    private boolean relative;
    private GyroCorrection gyro;

    public SideTurn( GyroCorrection gyro, boolean relative, Side side) {
        super(Angle.ZERO, gyro, relative);
        this.side = side;
        this.gyro=gyro;
    }
    public void userStart() {
        super.userStart();
        if (this.relative) {
            this.gyro.setTargetAngleRelative(getTgt());
        } else {
            this.gyro.setTargetAngleReset(getTgt());
        }
    }
    @Override
    public Angle getTgt(){
        if(side == Side.LEFT){
            return new Degrees(90);
        }else{
            return new Degrees(-90);
        }
    }


}
