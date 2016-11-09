package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2609.robot.commands.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick driverStick;
    public static JoystickButton button1;// Button 1 is used in Drivetrain.java to 1/2 speed of driving/turning of the robot while held
    public static JoystickButton button2;
    public static JoystickButton button3;
    public static JoystickButton button4;
    public static JoystickButton button5;
    public static JoystickButton button6;
    public static JoystickButton button7;
    public static JoystickButton button8;
    public static JoystickButton button9;
    public static JoystickButton button10;
    //public static JoystickButton opbutton9;
    //public static JoystickButton opbutton10;
    
    public OI() {
    	driverStick = new Joystick(0);
        
        // Button 1 is used in Drivetrain.java to 1/2 speed of driving/turning of the robot while held
        //button1 = new JoystickButton(driverStick, 1);

        button1 = new JoystickButton(driverStick, 1);
        //button1.whenPressed(new GyroCameraTurn(.5));
        
        
        
    }
    public Joystick getdriverStick() {
        return driverStick;
    }
}