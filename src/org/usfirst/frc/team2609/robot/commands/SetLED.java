package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2609.robot.Robot;

public class SetLED extends Command {
	int red;
	int green;
	int blue;
	
    public SetLED(int red, int green, int blue) {
    	this.red = red;
    	this.green = green;
    	this.blue = blue;
    }

    protected void initialize() {
    	Robot.LedControl.setLed(red, green, blue);
    }
    	
    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {
    }
    
    protected void interrupted() {
    }
}
