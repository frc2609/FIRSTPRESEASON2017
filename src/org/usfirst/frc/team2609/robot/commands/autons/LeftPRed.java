package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPRed extends CommandGroup {
	protected void initialize(){
	}
	
    public LeftPRed(double Move1,double Angle1,double Move2,double Angle2,double Move3) {
		// POINT TURNS

//    	//left gear and balls auton with dashboard variables
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(Move1,1.0,0));
    	addSequential(new GyroTurn(1,Angle1));
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(Move2,1.0,Angle1));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addParallel(new DriveEncoderCurveSimple(Move3, 1.0, Angle1, Angle2, Angle2, Angle2, -20, Move3, Move3));
    	addParallel(new BallLowGoalAuton());
		
		// Curves
    	//left gear auton with dashboard variables
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(75,1.0,0));
//    	addSequential(new GyroTurn(1,-60));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(65,1.0,-60));
//    	addSequential(new ClawOpen());
//    	addSequential(new GyroTurn(1,-40));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addParallel(new DriveEncoder(-118,1.0,-40));
//    	addParallel(new BallLowGoalAuton());
    	
    	
    	
    }
}
