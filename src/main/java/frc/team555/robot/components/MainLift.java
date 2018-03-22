package frc.team555.robot.components;

import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.PID;

import java.nio.channels.Pipe;

public class MainLift extends TargetMotor {

    private final double speed = 1.0;

    private int upPosition;
    private int downPosition;

    public MainLift(){
        super(Hardware.liftEncoder, new BangBang(1,1), new Motor(Hardware.motorLiftMainFront), new Motor(Hardware.motorLiftMainBack));

        mode = Mode.POWER;

        // Manual Up
        Control.mainLiftManualUp.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                set(speed);
            }
        });

        Control.mainLiftManualUp.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                set(0);
            }
        });

        // Manual Down
        Control.mainLiftManualDown.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(!Hardware.liftLimitSwitch.get()) {
                    set(-speed);
                }else{
                    set(0);
                    encoder.reset();
                }
            }
        });

        Control.mainLiftManualDown.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                set(0);
            }
        });

        // Lift Top
        Control.mainLiftTop.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(encoder != null){
                    MainLift.super.setTarget(downPosition);
                }
            }
        });
        setPower(0);
        // Lift Bottom



    }



}
