package org.usfirst.frc.team2609.robot.commands.vulcanClaw;

import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClawDownWithoutGear extends Command {

	boolean isFinished;
	
    public ClawDownWithoutGear() {
        //requires(Robot.vulcanclaw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	/*if (!RobotMap.gearSensor.get()){
        	Robot.vulcanclaw.downClaw(); // Move down if you do not have a gear
    	}
    	else{
        	isFinished = true;
    	}*/
    	Robot.vulcanclaw.downClaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !RobotMap.clawDownSensor.get() || timeSinceInitialized()>1;// || isFinished;
    } //Check if you already have a gear in the up position

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
