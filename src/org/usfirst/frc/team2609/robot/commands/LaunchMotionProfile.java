
package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.MotionProfileSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class LaunchMotionProfile extends Command {
	int endcount;
    public LaunchMotionProfile() {
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap._MotionPLeft.reset();
    	RobotMap._MotionPRight.reset();
    	if(RobotMap.MPLeftDisabled && RobotMap.MPRightDisabled && !RobotMap.drivetrainMPActive){
        	RobotMap.MPLeftDisabled = false;
        	RobotMap.MPRightDisabled = false;
        	System.out.println("EStop override");
    	}
    	RobotMap.drivetrainMPActive = true;
    	RobotMap._MotionPLeft.startMotionProfile();
    	RobotMap._MotionPRight.startMotionProfile();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(RobotMap.MPLeftDisabled && RobotMap.MPRightDisabled){
    		endcount++;
    	}
    	else{
    		endcount = 0;
    	}
    	if(endcount==2)
    	{
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.eStopMP();
    	System.out.println("Launch ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
