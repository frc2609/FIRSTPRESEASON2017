package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurnVariable3 extends Command {
	private SimPID gyroPID;
	double turnP = 0;
	double turnI = 0;
	double turnD = 0;
	double turnMax = 0;
	double turnEps = 0;
	double maxPower = 0;
	double autonAngle = 0;
	double turnDR = 0;
	int turnDC = 0;
	
    public GyroTurnVariable3(double maxPower, double turnHeading) {
    	this.maxPower = maxPower;
    }
 
    protected void initialize() {
        this.gyroPID = new SimPID();
    	gyroPID.resetPreviousVal();
		autonAngle = (double)SmartDashboard.getNumber("auton angle 3: ",0);
        turnP = (double)SmartDashboard.getNumber("turn P: ",0);
        turnI = (double)SmartDashboard.getNumber("turn I: ",0);
        turnD = (double)SmartDashboard.getNumber("turn D: ",0);
        turnMax = (double)SmartDashboard.getNumber("turn Max: ",0);
        turnEps = (double)SmartDashboard.getNumber("turn Eps: ",0);
        turnDR = (double)SmartDashboard.getNumber("turn DR: ",0);
        turnDC = (int)SmartDashboard.getNumber("turn DC: ",0);
        this.gyroPID.setDoneRange(turnDR);
        this.gyroPID.setMinDoneCycles(turnDC);
        this.gyroPID.setDesiredValue(autonAngle);
        this.gyroPID.setConstants(turnP, turnI, turnD);
        this.gyroPID.setErrorEpsilon(turnEps);
        this.gyroPID.setMaxOutput(maxPower*turnMax);
    }

    protected void execute() {
    	Robot.drivetrain.gyroTurn(gyroPID, gyroPID);
    }

    protected boolean isFinished() {
    	return gyroPID.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
