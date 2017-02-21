package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StraightPegBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public StraightPegBlue() {

    	/*
		addSequential(new DriveEncoder(-6.8, 0.3, 0));
    	addSequential(new ClawOpen());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(3, 0.3, 0));
    	*/
    	double point1 = -5;
    	double point2 = -160;
    	double point3 = -180;
    	double totalArc = -190;
    	
    	double heading1 = 0;
    	double heading2 = -90;
    	double heading3 = -45;
    	double heading4 = -45;
    	
    	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(80,1.0,0));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addParallel(new DriveEncoderCurveSimple(totalArc, 0.7, 0.3, heading1, heading2, heading3, heading4, point1, point2, point3));
    	addParallel(new BallLowGoalAuton(point3));
    	
    	
//    	addSequential(new EncReset());
//    	addSequential(new DriveEncoder(80,1.0,0));
//    	addSequential(new ClawOpen());
//    	addSequential(new EncReset());
//    	addParallel(new DriveEncoderCurveSimple(-190, 0.5, 0.5, 0, -90, -135, -135, 50, 160, 190));
//    	addParallel(new BallLowGoalAuton());
    }
}
