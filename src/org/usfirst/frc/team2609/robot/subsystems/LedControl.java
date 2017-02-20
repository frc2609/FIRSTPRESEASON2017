package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LedControl extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setLed(int red, int green, int blue){
    	RobotMap.frameLights.showRGB(red, green, blue);
    }
    
    public void flash(boolean On){
    	if (On){
    		RobotMap.frameLights.blinkLED(1);
    	}
    	else{
    		RobotMap.frameLights.blinkLED(0);
    	}
    	
    }
    
	public void toggleLED(){
    	if (RobotMap.ringLED.get() == Relay.Value.kOff){
    		RobotMap.ringLED.set(Relay.Value.kReverse);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
	public void trackLED(boolean state){
    	if (state){
    		RobotMap.ringLED.set(Relay.Value.kReverse);
    	}
    	else{
    		RobotMap.ringLED.set(Relay.Value.kOff);
    	}
	}
}

