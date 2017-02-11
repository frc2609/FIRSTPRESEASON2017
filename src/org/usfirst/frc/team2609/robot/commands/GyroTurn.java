package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends Command {
	private SimPID gyroPID;
	private SimPID pivotPID;
	double turnP = 0;
	double turnI = 0;
	double turnD = 0;
	double turnMax = 0;
	double turnEps = 0;
	double maxPower = 0;
	double turnHeading = 0;
	
    public GyroTurn(double maxPower, double turnHeading) {
    	this.maxPower = maxPower;
    	this.turnHeading = turnHeading;
        System.out.println("GYROTURN CLASS INITED");
    }
 
    protected void initialize() {
        this.gyroPID = new SimPID();
    	gyroPID.resetPreviousVal();
        turnP = (double)SmartDashboard.getNumber("turn P: ",0);
        turnI = (double)SmartDashboard.getNumber("turn I: ",0);
        turnD = (double)SmartDashboard.getNumber("turn D: ",0);
        turnEps = (double)SmartDashboard.getNumber("turn Eps: ",0);
        this.gyroPID.setDoneRange(1);
        this.gyroPID.setMinDoneCycles(100);
        this.gyroPID.setDesiredValue(turnHeading);
        this.gyroPID.setConstants(turnP, turnI, turnD);
        this.gyroPID.setErrorEpsilon(turnEps);
        this.gyroPID.setMaxOutput(maxPower);
    }

    protected void execute() {
    	Robot.drivetrain.gyroTurn(gyroPID, gyroPID);
    }

    protected boolean isFinished() {
    	//System.out.println("gyroPID.isDone "+ gyroPID.isDone());
    	return gyroPID.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
