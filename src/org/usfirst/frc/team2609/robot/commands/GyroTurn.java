package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends Command {
	private SimPID gyroPID;
	double turnP = 0;
	double turnI = 0;
	double turnD = 0;
	double turnMax = 0;
	double turnEps = 0;
	
    public GyroTurn(double maxPower, double turnHeading) {
        turnP = (double)SmartDashboard.getNumber("turn P: ");
        turnI = (double)SmartDashboard.getNumber("turn I: ");
        turnD = (double)SmartDashboard.getNumber("turn D: ");
        turnMax = (double)SmartDashboard.getNumber("turn Max: ");
        turnEps = (double)SmartDashboard.getNumber("turn Eps: ");
        this.gyroPID = new SimPID();
        this.gyroPID.setDesiredValue(turnHeading);
        this.gyroPID.setConstants(turnP, turnI, turnD);
        this.gyroPID.setMaxOutput(maxPower);
        this.gyroPID.setDoneRange(1);}
 
    protected void initialize() {
    	gyroPID.resetPreviousVal();
    }

    protected void execute() {
    	Robot.drivetrain.gyroTurn(gyroPID);
    }

    protected boolean isFinished() {
    	return gyroPID.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    }
}
