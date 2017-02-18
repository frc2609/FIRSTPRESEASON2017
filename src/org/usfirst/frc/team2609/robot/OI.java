package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2609.robot.commands.*;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanBallMode;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearMode;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearScore;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.toggleClaw;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.toggleDeployClaw;

public class OI {
    public static Joystick driverStick;
    public static JoystickButton button1;
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
    public JoystickButton autoGear;

    public static Joystick opStick;
    public JoystickButton TsunamiUp;
    public JoystickButton TsunamiDown;
    
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
    	driverStick = new Joystick(0);
    	opStick = new Joystick(1);
        
        button1 = new JoystickButton(driverStick, 1);
        button1.whenPressed(new GearSwerve());
        
        shift = new JoystickButton(driverStick, 2);
        shift.whenReleased(new ShifterCommand());
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
        
        toggleLED = new JoystickButton(driverStick, 3);
        toggleLED.whenPressed(new toggleLED());
//        
        autoGear = new JoystickButton(driverStick, 4);
        //autoGear.whenPressed(new AutoGear(0));
		//toggleLED.whenReleased(new toggleLED());
//        
//        xxx = new JoystickButton(driverStick, 5);
        
        toggleClaw = new JoystickButton(driverStick, 6);
        toggleClaw.whenReleased(new toggleClaw());
//        
//        VulcanGearMode = new JoystickButton(driverStick, 8);
//    	VulcanGearMode.whenPressed(new VulcanGearMode());
//
//        VulcanBallMode = new JoystickButton(driverStick, 7);
//    	VulcanBallMode.whenPressed(new VulcanBallMode());
//    	
//        VulcanGearScore = new JoystickButton(driverStick, 6);
//    	VulcanGearScore.whenPressed(new VulcanGearScore());
        VulcanBallMode = new JoystickButton(driverStick, 7);
    	VulcanBallMode.whenPressed(new VulcanBallMode());
    	
        VulcanGearMode = new JoystickButton(driverStick, 8);
    	VulcanGearMode.whenPressed(new VulcanGearMode());

        VulcanGearScore = new JoystickButton(driverStick, 9);
    	VulcanGearScore.whenPressed(new VulcanGearScore());
    	
		opButton1 = new JoystickButton(opStick, 1);
		opButton1.whenPressed(new GearAutonSpline());
		opButton2 = new JoystickButton(opStick, 2);
		opButton2.whenPressed(new LaunchMotionProfile());
		opButton3 = new JoystickButton(opStick, 3);
		opButton3.whenPressed(new MotionProfileEStop());
        TsunamiDown = new JoystickButton(opStick, 4);
        TsunamiDown.whileHeld(new TsunamiControl(TsunamiDirection.DOWN));
		opButton5 = new JoystickButton(opStick, 5);
		opButton5.whenPressed(new GyroPathFollower());
        
        toggleDeployClaw = new JoystickButton(driverStick, 10);
        toggleDeployClaw.whenReleased(new toggleDeployClaw());
        

    }
    public Joystick getdriverStick() {
        return driverStick;
    }
}