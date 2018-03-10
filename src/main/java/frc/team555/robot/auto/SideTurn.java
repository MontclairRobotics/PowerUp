package frc.team555.robot.auto;

import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.states.TurnGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Degrees;

public class SideTurn extends TurnGyro {

    public Side side;

    public SideTurn( GyroCorrection gyro, boolean relative, Side side) {
        super(Angle.ZERO, gyro, relative);
        this.side = side;
    }

    @Override
    public Angle getTgt(){
        if(side == Side.RIGHT){
            return new Degrees(90);
        }else{
            return new Degrees(-90);
        }
    }


}
