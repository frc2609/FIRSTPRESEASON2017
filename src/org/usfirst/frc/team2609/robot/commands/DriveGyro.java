package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveGyro extends Command {
    public double moveValue = 0;
    public double rotateValue = 0;
	private SimPID steeringPID;

    public DriveGyro() {
        this.steeringPID = new SimPID();
        this.steeringPID.setDesiredValue(0);
        this.steeringPID.setConstants(0.0005, 0.0001, 0);
        this.steeringPID.setMaxOutput(0.6);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    public void gyroTurn(double turnHeading, double gyroPvalue)
    {
    	
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
