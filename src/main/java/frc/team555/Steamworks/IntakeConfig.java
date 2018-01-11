package frc.team555.Steamworks;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import frc.team555.hardware.DeviceEnum;
import frc.team555.hardware.HardwareConfigBase;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.motors.Motor;

public class IntakeConfig{


    Motor intakeL;
    Motor intakeR;

    DigitalInput openLeftSwitch;
    DigitalInput closeLeftSwitch;
    DigitalInput openRightSwitch;
    DigitalInput closeRightSwitch;

    public IntakeConfig(Joystick joystick, int buttonID) {
        intakeL  = new Motor(new VictorSP(0));
        intakeR = new Motor(new WPI_TalonSRX(5));

        openLeftSwitch   = new DigitalInput(1);
        closeLeftSwitch  = new DigitalInput(0);
        openRightSwitch  = new DigitalInput(6);
        closeRightSwitch = new DigitalInput(7);

        double intakePow=0.3;
        Button intake = new JoystickButton(joystick, buttonID);

        // Intake Open
        intake.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(openLeftSwitch.get()){intakeL.set(0);}else {intakeL.set(intakePow);}
                if(!openRightSwitch.get()){intakeR.set(0);}else {intakeR.set(intakePow);}
            }
        });
        // Intake Close
        intake.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
                if(closeLeftSwitch.get()){intakeL.set(0);}else {intakeL.set(-intakePow);}
                if(!closeRightSwitch.get()){intakeR.set(0);}else {intakeR.set(-intakePow);}
            }
        });
    }



}