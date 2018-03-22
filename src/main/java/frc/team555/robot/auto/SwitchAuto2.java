package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.AutoState;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.auto.states.ResetGyro;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.states.MultiState;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.Input;

public class SwitchAuto2 extends StateMachine {

    static Input<Boolean> startSide;
    public static SendableChooser<Side> startSidesChooser;


    public static void init() {
        startSidesChooser = new SendableChooser<>();
        for (Side side : Side.values()) {
            startSidesChooser.addObject(side.toString(), side);
        }
        SmartDashboard.putData(startSidesChooser);
        startSide = new Input<Boolean>() {
            @Override
            public Boolean get() {
                return Side.fromDriverStation()[0] == startSidesChooser.getSelected();
            }
        };
    }


    public static void disabled(){
        SmartDashboard.putData("My Side",startSidesChooser);
    }

    public SwitchAuto2(GyroCorrection correction){
        super(new ResetGyro(correction),
                new MoveLiftTime(.5,.5),
                new MoveLiftTime(-.5,.5),
                //new MoveIntake(-.8,0.5),
                new MultiState(
                        new MoveLiftTime(.5, 2),
                        new DriveEncoderGyro(150, .75, Angle.ZERO, false, correction)),
                new ConditionalState(new RotateAndDropCube(correction),startSide));

    }

    public static class RotateAndDropCube extends StateMachine
    {
        public RotateAndDropCube(GyroCorrection correction) {
            super(
                    new SideTurn(correction, false, startSidesChooser.getSelected()),
                    new DriveTime(3, .3));
                    //new MoveIntake(.8,0.5));
        }
    }

    public static class MoveLiftTime extends Delay
    {
        private double power;
        public MoveLiftTime(double power, double time) {
            super(time);
            this.power=power;
        }
        @Override
        public void userStart() {
            Hardware.motorLiftMainBack.set(power);
            Hardware.motorLiftMainFront.set(power);
        }

        @Override
        public void userStop() {

            Hardware.motorLiftMainBack.set(0);
            Hardware.motorLiftMainFront.set(0);
        }
    }

    /*public static class MoveIntake extends Delay
    {
        private double power;
        public MoveIntake(double power,double time)
        {
            super(time);
            this.power=power;
        }
        @Override
        public void userStart() {
            Hardware.motorIntakeClamp.set(power);
        }

        @Override
        public void userStop() {
            Hardware.motorIntakeClamp.set(0);
        }

    }*/

}
