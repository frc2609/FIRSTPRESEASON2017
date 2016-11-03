package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.subsystems.SimPID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraTurn extends Command {
	private SimPID pivotPID;
	private SimPID cameraPID;
	double cameraP = 0;
	double cameraI = 0;
	double cameraD = 0;
	double cameraMax = 0;
	double cameraEps = 0;
	double maxPower = 0;
	double[] defaultval = new double[0];
	double[] centerXarray = new double[0];
	double errorX = 0;
	NetworkTable table = NetworkTable.getTable("GRIP/target");;
	
    public CameraTurn(double maxPower) {
        System.out.println("GYROTURN CLASS INITED");
    }
 
    protected void initialize() {
        this.pivotPID = new SimPID();
        this.pivotPID.setDesiredValue(0);
        this.pivotPID.setConstants(0.01, 0, 0);
        this.pivotPID.setMaxOutput(1);
        cameraP = (double)SmartDashboard.getNumber("camera P: ");
        cameraI = (double)SmartDashboard.getNumber("camera I: ");
        cameraD = (double)SmartDashboard.getNumber("camera D: ");
        cameraMax = (double)SmartDashboard.getNumber("camera Max: ");
        cameraEps = (double)SmartDashboard.getNumber("camera Eps: ");
        this.cameraPID = new SimPID();
        this.cameraPID.setConstants(cameraP, cameraI, cameraD);
        this.cameraPID.setMaxOutput(cameraMax);
        this.cameraPID.setDesiredValue(0);
    }

    protected void execute() {
    	try{
    		centerXarray = table.getNumberArray("centerX", defaultval);
        	double centerX = centerXarray[0];
        	SmartDashboard.putNumber("centerX", centerX);
        	errorX = centerX - 320;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
        	System.out.println(e.toString());
        }
    	Robot.drivetrain.cameraTurn(cameraPID, pivotPID, errorX);
    	
    }

    protected boolean isFinished() {
    	//System.out.println("gyroPID.isDone "+ gyroPID.isDone());
    	return cameraPID.isDone();
    }

    protected void end() {
    	Robot.drivetrain.stopDrive();
    }

    protected void interrupted() {
    	Robot.drivetrain.stopDrive();
    }
}
