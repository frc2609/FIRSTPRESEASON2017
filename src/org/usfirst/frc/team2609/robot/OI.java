package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2609.robot.commands.*;
import org.usfirst.frc.team2609.robot.subsystems.Shifter;


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
    public static JoystickButton button9;
    public static JoystickButton button10;
    //public static JoystickButton opbutton9;
    //public static JoystickButton opbutton10;
    public JoystickButton shift;
    public JoystickButton toggleDeployClaw;
    public JoystickButton toggleClaw;
    public JoystickButton toggleLED;
    public JoystickButton VulcanGearScore;
    public JoystickButton VulcanGearMode;
    public JoystickButton VulcanBallMode;
    public JoystickButton TsunamiUp;

    public static Joystick opStick;
    
    public static JoystickButton opButton1;
    public static JoystickButton opButton2;
    public static JoystickButton opButton3;
    public static JoystickButton opButton4;
    public static JoystickButton opButton5;
    public static JoystickButton opButton6;
    public static JoystickButton opButton7;
    public static JoystickButton opButton9;
    public static JoystickButton opButton10;
    
    public OI() {
//    	driverStick = new Joystick(0);
    	opStick = new Joystick(0);
        
        // Button 1 is used in Drivetrain.java to 1/2 speed of driving/turning of the robot while held
        //button1 = new JoystickButton(driverStick, 1);

//        button1 = new JoystickButton(driverStick, 1);
//        //button1.whenPressed(new GyroCameraTurn(.5));
//        //toggleLED = new JoystickButton(driverStick, 7);
////        toggleLED.whenReleased(new toggleLED());
//        toggleDeployClaw = new JoystickButton(driverStick, 10);
//        toggleDeployClaw.whenReleased(new toggleDeployClaw());
//        toggleClaw = new JoystickButton(driverStick, 9);
//        toggleClaw.whenReleased(new toggleClaw());
//        
//        shift = new JoystickButton(driverStick, 2);
//        shift.whenReleased(new ShifterCommand());
//        
//        VulcanGearMode = new JoystickButton(driverStick, 8);
//    	VulcanGearMode.whenPressed(new VulcanGearMode());
//
//        VulcanBallMode = new JoystickButton(driverStick, 7);
//    	VulcanBallMode.whenPressed(new VulcanBallMode());
//    	
//        VulcanGearScore = new JoystickButton(driverStick, 6);
//    	VulcanGearScore.whenPressed(new VulcanGearScore());

		opButton1 = new JoystickButton(opStick, 1);
		opButton1.whenPressed(new GearAutonSpline());
		opButton2 = new JoystickButton(opStick, 2);
		opButton2.whenPressed(new LaunchMotionProfile());
		opButton3 = new JoystickButton(opStick, 3);
		opButton3.whenPressed(new MotionProfileEStop());
    	
        
    }
    public Joystick getdriverStick() {
        return driverStick;
    }
}