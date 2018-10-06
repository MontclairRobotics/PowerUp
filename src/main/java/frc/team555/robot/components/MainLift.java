package frc.team555.robot.components;

import frc.team555.robot.auto.MoveLift;
import frc.team555.robot.core.Control;
import frc.team555.robot.core.Hardware;
import frc.team555.robot.core.PowerUpRobot;
import frc.team555.robot.utils.BangBang;
import frc.team555.robot.utils.TargetMotor;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveEncoderGyro;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.states.State;
import org.montclairrobotics.sprocket.states.StateMachine;
import org.montclairrobotics.sprocket.utils.PID;

import java.nio.channels.Pipe;

public class MainLift extends TargetMotor implements Lift {

    private final double speed = 1.0;
    public static final double TOP= 7809.0;


    private boolean auto=false;

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
                    if(encoder.getInches().get()>TOP*0.2||Control.auxStick.getRawButton(7)) {
                        set(-speed);
                    }
                    else {
                        set(-0.4);
                    }
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
        /* Control.mainLiftTop.setPressAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(encoder != null){
                    MainLift.super.setTarget(downPosition);
                }
            }
        });*/
        Control.mainLiftAutoUp.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(Hardware.liftEncoder.getInches().get()<TOP)
                {
                    Hardware.motorLiftMainFront.set(1);
                    Hardware.motorLiftMainBack.set(1);
                }
                else
                {
                    Hardware.motorLiftMainFront.set(0);
                    Hardware.motorLiftMainBack.set(0);
                }
            }
        });
        Control.mainLiftAutoUp.setReleaseAction(new ButtonAction() {
            @Override
            public void onAction() {
                Hardware.motorLiftMainFront.set(0);
                Hardware.motorLiftMainBack.set(0);
            }
        });
        setPower(0);
        // Lift Bottom



    }


    @Override
    public SEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void setAuto(double power) {
        setPower(power);
        auto=true;
    }
}
