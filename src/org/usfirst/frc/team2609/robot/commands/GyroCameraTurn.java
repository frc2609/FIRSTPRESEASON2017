package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroCameraTurn extends Command {
	private SimPID cameraPID;
	private SimPID pivotPID;
	double turnP = 0;
	double turnI = 0;
	double turnD = 0;
	double turnMax = 0;
	double turnEps = 0;
	double gyroCameraP = 0;
	double gyroCameraI = 0;
	double gyroCameraD = 0;
	double gyroCameraMax = 0;
	double gyroCameraEps = 0;
	double maxPower = 0;
	double centerXlocal;
	double errorX;
	
    public GyroCameraTurn(double maxPower, double centerXlocal) {
        gyroCameraP = (double)SmartDashboard.getNumber("gyroCamera P: ");
        gyroCameraI = (double)SmartDashboard.getNumber("gyroCamera I: ");
        gyroCameraD = (double)SmartDashboard.getNumber("gyroCamera D: ");
        gyroCameraMax = (double)SmartDashboard.getNumber("gyroCamera Max: ");
        gyroCameraEps = (double)SmartDashboard.getNumber("gyroCamera Eps: ");
        this.centerXlocal = centerXlocal;
        System.out.println("gyroCameraTURN CLASS INITED");
    	this.cameraPID = new SimPID();
        this.pivotPID = new SimPID();
        this.cameraPID.setMaxOutput(gyroCameraMax);
        this.cameraPID.setDoneRange(1);
        errorX = (centerXlocal - 320);//*(0.084375)); //why can't java divide
        this.cameraPID.setDesiredValue(errorX);
    }
 
    protected void initialize() {
        this.pivotPID.setDesiredValue(0);
        this.pivotPID.setConstants(0.01, 0, 0);
        this.pivotPID.setMaxOutput(1);
    	cameraPID.resetPreviousVal();
        this.cameraPID.setConstants(gyroCameraP, gyroCameraI, gyroCameraD);
        this.cameraPID.setErrorEpsilon(gyroCameraEps);
    }

    protected void execute() {
    	Robot.drivetrain.cameraTurn(cameraPID, pivotPID, errorX);
    	System.out.println(Robot.centerX);
    }

    protected boolean isFinished() {
    	System.out.println("CameraPID.isDone "+ cameraPID.isDone());
    	return cameraPID.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
