package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightPRed extends CommandGroup {
	protected void initialize(){
	}
	
    public RightPRed() {
    	double point1 = -10;
    	double point2 = -60;
    	double point3 = -140;
    	double totalArc = -140;
    	
    	double heading1 = -60;
    	double heading2 = 0;
    	double heading3 = -45;
    	double heading4 = -45;
    	//left gear and ? auton with dashboard variables
    	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(77,1.0,0));
    	addSequential(new GyroTurn(1,-60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(65,1.0,-60));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoderCurveSimple(totalArc, 0.4, 0.2, heading1, heading2, heading3, heading4, point1, point2, point3));
//    	addParallel(new DriveEncoderCurveSimple(-135, 0.8,0.2, 60, 42, 42, 42, -20, -128, -128));
//    	addParallel(new BallLowGoalAuton(point3));
    	
    }
}
