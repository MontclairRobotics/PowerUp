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

//Status: Needs Testing
public class MecanumRobot extends SprocketRobot {

    CANTalon drivetrainFL;
    CANTalon drivetrainFR;
    CANTalon drivetrainBL;
    CANTalon drivetrainBR;
    PowerDistributionPanel pdp;

    public final int frontLeftDeviceNumber  = 0; // Steamworks: 3
    public final int frontRightDeviceNumber = 1; // Steamworks: 1
    public final int backLeftDeviceNumber   = 2; // Steamworks: 4
    public final int backRightDeviceNumber  = 3; // Steamworks: 2

    public final int driveStickDeviceNumber = 0;
    public final int auxStickDeviceNumber   = 1;

    public final int buttonModule1 = 1;

    @Override
    public void robotInit() {

        Joystick driveStick = new Joystick(driveStickDeviceNumber);
        Joystick auxStick = new Joystick(auxStickDeviceNumber);
        pdp = new PowerDistributionPanel();

        // DRIVETRAIN
        drivetrainFL = new CANTalon(frontLeftDeviceNumber);
        drivetrainFR = new CANTalon(frontRightDeviceNumber);
        drivetrainBL = new CANTalon(backLeftDeviceNumber);
        drivetrainBR = new CANTalon(backRightDeviceNumber);

        DriveTrainBuilder dtBuilder = new DriveTrainBuilder();
        DriveModule frontL = new DriveModule(new XY(-1,1),new XY(-1,1),new Motor(drivetrainFL));
        DriveModule frontR = new DriveModule(new XY(1,1),new XY(-1,1),new Motor(drivetrainFR));
        DriveModule backL  = new DriveModule(new XY(-1,-1),new XY(-1,1),new Motor(drivetrainBL));
        DriveModule backR  = new DriveModule(new XY(1,-1),new XY(-1,1),new Motor(drivetrainBR));

        dtBuilder.addDriveModule(frontL)
                .addDriveModule(frontR)
                .addDriveModule(backL)
                .addDriveModule(backR)
                .setDriveTrainType(DriveTrainType.MECANUM)
                .setInput(new SquaredDriveInput(driveStick));

        try {
            dtBuilder.build();
        } catch (InvalidDriveTrainException e) {
            e.printStackTrace();
        }

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
        double tempCurrentAvg;
        tempCurrentAvg = (pdp.getCurrent(drivetrainFL.getDeviceID())+ pdp.getCurrent(drivetrainFR.getDeviceID())+
                pdp.getCurrent(drivetrainBL.getDeviceID())+ pdp.getCurrent(drivetrainBR.getDeviceID()))/4;

        double dtCurrentStdDev  =  Math.sqrt((Math.pow(Math.abs(pdp.getCurrent(drivetrainFL.getDeviceID()) - tempCurrentAvg),2) +
                Math.pow(Math.abs(pdp.getCurrent(drivetrainFR.getDeviceID()) - tempCurrentAvg),2) +
                Math.pow(Math.abs(pdp.getCurrent(drivetrainBL.getDeviceID()) - tempCurrentAvg),2) +
                Math.pow(Math.abs(pdp.getCurrent(drivetrainBR.getDeviceID()) - tempCurrentAvg),2))/4);

        if (Math.abs(pdp.getCurrent(drivetrainFL.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("FL within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("FL within STD Dev",false);
        }

        if (Math.abs(pdp.getCurrent(drivetrainFR.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("FR within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("FR within STD Dev",false);
        }

        if (Math.abs(pdp.getCurrent(drivetrainBL.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("BL within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("BL within STD Dev",false);
        }

        if (Math.abs(pdp.getCurrent(drivetrainBR.getDeviceID()) - tempCurrentAvg) < dtCurrentStdDev){
            SmartDashboard.putBoolean("BR within STD Dev",true);
        }else{
            SmartDashboard.putBoolean("BR within STD Dev",false);
        }
    }

}
