package frc.team555.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.montclairrobotics.sprocket.SprocketRobot;
import org.montclairrobotics.sprocket.control.Button;
import org.montclairrobotics.sprocket.control.ButtonAction;
import org.montclairrobotics.sprocket.control.JoystickButton;
import org.montclairrobotics.sprocket.control.SquaredDriveInput;
import org.montclairrobotics.sprocket.drive.*;
import frc.team555.robot.HardwareConfig.*;

//Status: Needs Testing
public class MecanumRobot extends SprocketRobot {


    //private final int frontLeftDeviceNumber  = 0; // Steamworks: 3
    //private final int frontRightDeviceNumber = 1; // Steamworks: 1
    //private final int backLeftDeviceNumber   = 2; // Steamworks: 4
    //private final int backRightDeviceNumber  = 3; // Steamworks: 2

    private final int driveStickDeviceNumber = 0;
    private final int auxStickDeviceNumber   = 1;

    private final int buttonModule1 = 1;

    SprocketHardwareConfig hw;

    @Override
    public void robotInit() {

        hw = new SprocketHardwareConfig();


        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);


        // in the case of a physical robot, this could be part of
        // the hardware config since it couldn't change reasonably
        DriveTrainBuilder dtBuilder = new DriveTrainBuilder()
                .addDriveModule(hw.frontL)
                .addDriveModule(hw.frontR)
                .addDriveModule(hw.backL)
                .addDriveModule(hw.backR)
                .setDriveTrainType(DriveTrainType.MECANUM);
                // the following line is not hardware
                //.setInput(new SquaredDriveInput(driveStick));
        DriveTrain driveTrain;
        try {
            driveTrain = dtBuilder.build();
            driveTrain.setDefaultInput(new SquaredDriveInput(driveStick));
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }
        // end of drivetrain hardware config


        // Module 1
        Button module1 = new JoystickButton(driveStick, buttonModule1);
        module1.setHeldAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });

        module1.setOffAction(new ButtonAction() {
            @Override
            public void onAction() {
            }
        });
    }

    @Override
    public void update() {
        checkCurrentDT();
    }

    private void checkCurrentDT(){

        double tempCurrentAvg = (hw.pdb.getCurrent(Devices.FrontLeftDriveMotor)+ hw.pdb.getCurrent(Devices.FrontRightDriveMotor)+
                hw.pdb.getCurrent(Devices.BackLeftDriveMotor)+ hw.pdb.getCurrent(Devices.BackRightDriveMotor))/4;

        double dtCurrentStdDev  =  Math.sqrt((
                Math.pow(Math.abs(hw.pdb.getCurrent(Devices.FrontLeftDriveMotor) - tempCurrentAvg),2) +
                Math.pow(Math.abs(hw.pdb.getCurrent(Devices.FrontRightDriveMotor) - tempCurrentAvg),2) +
                Math.pow(Math.abs(hw.pdb.getCurrent(Devices.BackLeftDriveMotor) - tempCurrentAvg),2) +
                Math.pow(Math.abs(hw.pdb.getCurrent(Devices.BackRightDriveMotor) - tempCurrentAvg),2) )/4.0f);

        if (Math.abs(hw.pdb.getCurrent(hw.drivetrainFL.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("FL within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("FL within STD Dev",false);
        }

        if (Math.abs(hw.pdb.getCurrent(hw.drivetrainFR.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("FR within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("FR within STD Dev",false);
        }

        if (Math.abs(hw.pdb.getCurrent(hw.drivetrainBL.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("BL within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("BL within STD Dev",false);
        }

        if (Math.abs(hw.pdb.getCurrent(hw.drivetrainBR.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("BR within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("BR within STD Dev",false);
        }
    }

}
