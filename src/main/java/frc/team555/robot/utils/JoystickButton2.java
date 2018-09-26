package frc.team555.robot.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;

public class JoystickButton2 extends JoystickButton implements Updatable {
    String name;
    int id;

    public JoystickButton2(Joystick stick, int buttonId) {
        super(stick, buttonId);
        id = buttonId;
        name = stick.getName();
        Updater.add(this, Priority.LOW);
    }

    @Override
    public void update(){
        SmartDashboard.putBoolean("Stick: " + name + "ID: " + id, get());
    }

}
