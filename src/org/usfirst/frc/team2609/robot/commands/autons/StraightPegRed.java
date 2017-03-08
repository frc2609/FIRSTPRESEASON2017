package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpen;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpenAuton;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StraightPegRed extends CommandGroup {

	protected void initialize(){
	}
	
    public StraightPegRed() {

    	/*
		addSequential(new DriveEncoder(-6.8, 0.3, 0));
    	addSequential(new ClawOpen());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(3, 0.3, 0));
    	*/
    	
    	double point1 = -25;
    	double point2 = -165;
    	double point3 = -200;
    	double totalArc = -200;
    	
    	double heading1 = 0;
    	double heading2 = -90; //inverted after first match
    	double heading3 = -45;
    	double heading4 = -45;
    	
    	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(82,1.0,0));
//    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoderCurveSimple(totalArc, 0.4, 0.2, heading1, heading2, heading3, heading4, point1, point2, point3));
//    	addParallel(new BallDoorOpen());
//    	addParallel(new BallLowGoalAuton(point3));
    }
}
