package frc.team555.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team555.robot.utils.Side;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.states.State;

import java.util.ArrayList;
import java.util.TreeMap;

public class SimpleAuto extends AutoMode{

    TreeMap<String,SendableChooser> choosers;
    public SimpleAuto() {
        super("Auto Mode", null);
        SendableChooser<Side> mySide=new SendableChooser<Side>();
        mySide.addObject(Side.LEFT.toString(),Side.LEFT);
        mySide.addObject(Side.RIGHT.toString(),Side.RIGHT);
        Side[] sides={Side.LEFT,Side.RIGHT};

        choosers=new TreeMap<String,SendableChooser>();
        choosers.put("My Side",mySide);
        for(Side sw:sides)
        {
            for(Side sc:sides)
            {
                SendableChooser<String> temp=new SendableChooser<String>();
                temp.addObject("Switch: "+sw.toString(),"Switch: "+sw.toString());
                temp.addObject("Scale: "+sc.toString(),"Scale: "+sc.toString());
                temp.addObject("Baseline","Baseline");
                choosers.put("Switch: "+sw.toString()+" Scale:"+sc.toString(),temp);
            }
        }
    }
    @Override
    public void start()
    {
        ArrayList<State> states;

        Side[] actualSides=Side.fromDriverStation();
        String control=(String)(((SendableChooser)SmartDashboard.getData("Switch: "+actualSides[0].toString()+" Scale:"+actualSides[1].toString())).getSelected());
        String mySide=(String)((SendableChooser)SmartDashboard.getData("My Side")).getSelected();

    }
    @Override
    public void stop()
    {

    }

    public void disabledUpdate()
    {
        for(String key:choosers.keySet())
        {
            SmartDashboard.putData(key,choosers.get(key));
        }
    }
}
