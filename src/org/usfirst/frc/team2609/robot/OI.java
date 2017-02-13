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

    public static Joystick opStick;
    public JoystickButton TsunamiUp;
    public JoystickButton TsunamiDown;
    
    public OI() {
    	driverStick = new Joystick(0);
    	opStick = new Joystick(1);
        
        button1 = new JoystickButton(driverStick, 1);
        button1.whenPressed(new GearSwerve());
        
        shift = new JoystickButton(driverStick, 2);
        shift.whenReleased(new ShifterCommand());
        
//        xxx = new JoystickButton(driverStick, 3);
//        
        toggleLED = new JoystickButton(driverStick, 4);
		toggleLED.whenReleased(new toggleLED());
//        
//        xxx = new JoystickButton(driverStick, 5);
        
        toggleClaw = new JoystickButton(driverStick, 6);
        toggleClaw.whenReleased(new toggleClaw());

        VulcanBallMode = new JoystickButton(driverStick, 7);
    	VulcanBallMode.whenPressed(new VulcanBallMode());
    	
        VulcanGearMode = new JoystickButton(driverStick, 8);
    	VulcanGearMode.whenPressed(new VulcanGearMode());

        VulcanGearScore = new JoystickButton(driverStick, 9);
    	VulcanGearScore.whenPressed(new VulcanGearScore());
        
        toggleDeployClaw = new JoystickButton(driverStick, 10);
        toggleDeployClaw.whenReleased(new toggleDeployClaw());
        
        TsunamiUp = new JoystickButton(opStick, 1);
        TsunamiUp.whileHeld(new TsunamiControl(TsunamiDirection.UP));
        
        TsunamiDown = new JoystickButton(opStick, 2);
        TsunamiDown.whileHeld(new TsunamiControl(TsunamiDirection.DOWN));

    }
    public Joystick getdriverStick() {
        return driverStick;
    }
}