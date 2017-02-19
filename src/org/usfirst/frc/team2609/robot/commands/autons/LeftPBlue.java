package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public LeftPBlue(double Move1,double Angle1,double Move2,double Angle2,double Move3) {

        	//left gear and ? auton with dashboard variables	
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
    }
}
