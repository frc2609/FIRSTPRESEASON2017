package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightPBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public RightPBlue(double Move1,double Angle1,double Move2,double Angle2,double Move3) {
    	//right gear and balls auton with dashboard variables	
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(70,1.0,0));
    	addSequential(new GyroTurn(1,-60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(75,1.0,-60));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addParallel(new DriveEncoderCurveSimple(-125, 0.8, 0.2, -60, -42, -42, -42, -10, -120, -120));
    	addParallel(new BallLowGoalAuton());
    	
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addSequential(new DriveEncoder(-20,1.0,-60));
//    	addSequential(new GyroTurn(1,-42));
//    	addSequential(new EncReset());
//    	addSequential(new TimerDelay(0.2));
//    	addParallel(new DriveEncoder(-100,1.0,-42));

    }
}
