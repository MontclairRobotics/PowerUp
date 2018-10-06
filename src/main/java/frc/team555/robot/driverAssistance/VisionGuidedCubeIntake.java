package frc.team555.robot.driverAssistance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.components.IntakeLift;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.Togglable;

public class VisionGuidedCubeIntake implements Updatable, Togglable{

    public final Motor left;
    public final Motor right;

    public final Input<Vector> power;

    //TODO: Tune default x and y
    private final double defaultX = 140;
    private final double defaultY = 140;

    //TODO: Get thresholds
    private final double X_MAX = 150;
    private final double X_MIN = 130;
    private final double Y_MAX = 150;
    private final double Y_MIN = 130;


    //Vector States
    Vector intakeVector  = new XY(-1,0);
    Vector outtakeVector = new XY(1,0);
    Vector idleVector    = new XY(-0.1,0);


    public VisionGuidedCubeIntake(IntakeLift intakeLift) {
        this.left = new Motor(Hardware.motorIntakeL);
        this.right = new Motor(Hardware.motorIntakeR);
        right.setInverted(true);

        double cubeX = SmartDashboard.getNumber("Cube X",defaultX);
        double cubeY = SmartDashboard.getNumber("Cube Y", defaultY);

        //TODO: Make sure that intake lift encoder value is good
        if(Control.cubeAssistance.get()){
            this.power = new Input<Vector>() {
                @Override
                public Vector get() {
                    if(intakeLift.encoder.get() == 0){
                        if(cubeX < X_MAX && cubeX > X_MIN && cubeY < Y_MAX && cubeY > Y_MIN){
                            return idleVector;
                        }else if(!Control.autoOuttake.get()){
                            return outtakeVector;
                        }else{
                            return intakeVector;
                        }
                    }else{
                        return idleVector;
                    }
                }
            };
        }else{
            this.power = new Input<Vector>() {
                @Override
                public Vector get() {
                    double x=-Control.auxStick.getX();
                    if(Math.abs(x)<0.2)
                    {
                        x=0;
                    }
                    double y=-Control.auxStick.getY();
                    if(Math.abs(y)<0.2)
                    {
                        y=-.04;
                    }
                    return new XY(x,y);
                }
            };
        }


        Updater.add(this, Priority.CALC);
    }


    @Override
    public void update() {
        Vector p = power.get();
        left.set(p.getY() - p.getX());
        right.set(p.getY() + p.getX());

    }

    @Override
    public void enable() {
        left.set(1);
        right.set(1);
    }

    @Override
    public void disable() {
        left.set(0);
        right.set(0);
    }

}
