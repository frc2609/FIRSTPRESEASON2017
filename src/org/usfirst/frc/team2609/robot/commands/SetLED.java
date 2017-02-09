package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2609.robot.subsystems.*;
import org.usfirst.frc.team2609.robot.Robot;

/**
 *
 */
public class SetLED extends Command {

	int red;
	int green;
	int blue;
	
    public SetLED(int red, int green, int blue) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	this.red = red;
    	this.green = green;
    	this.blue = blue;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.LedControl.setLed(red, green, blue);
    }
    	
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
