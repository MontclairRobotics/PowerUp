package frc.team555.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.motors.Motor;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.PID;

public class Robot extends SprocketRobot {

    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;

    PowerDistributionPanel powerDistributionPanel;

    public final int frontLeftDeviceNumber  = 0;
    public final int frontRightDeviceNumber = 1;
    public final int backLeftDeviceNumber   = 2;
    public final int backRightDeviceNumber  = 3;

    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber   = 1;

    @Override
    public void robotInit() {
        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);

        // DRIVETRAIN
        drivetrainFL = new CANTalon(frontLeftDeviceNumber);
        drivetrainFR = new CANTalon(frontRightDeviceNumber);
        drivetrainBL = new CANTalon(backLeftDeviceNumber);
        drivetrainBR = new CANTalon(backRightDeviceNumber);

        DriveModule dtLeft  = new DriveModule(new XY(-1,0), new XY(0,1), new Motor(drivetrainFL), new Motor(drivetrainBL));
        DriveModule dtRight = new DriveModule(new XY( 1,0), new XY(0,1), new Motor(drivetrainFR), new Motor(drivetrainBR));

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        dtBuilder.addDriveModule(dtLeft);
        dtBuilder.addDriveModule(dtRight);
        dtBuilder.setDriveTrainType(DriveTrainType.TANK);
        dtBuilder.setInput(new SquaredDriveInput(driveStick));

        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

        //Buttons
        Button trigger = new JoystickButton(driveStick, 1);
        trigger.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

        trigger.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });
    }

    @Override
    public void update() {

        // CURRENT OUTPUT FOR MOTORS
        Debug.msg("DT FL Current", powerDistributionPanel.getCurrent(frontLeftDeviceNumber));
        Debug.msg("DT FR Current", powerDistributionPanel.getCurrent(frontRightDeviceNumber));
        Debug.msg("DT BL Current", powerDistributionPanel.getCurrent(backLeftDeviceNumber));
        Debug.msg("DT BR Current", powerDistributionPanel.getCurrent(backRightDeviceNumber));

    }


}
