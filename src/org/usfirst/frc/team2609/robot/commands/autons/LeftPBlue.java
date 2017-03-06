package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpen;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public LeftPBlue() {
    	double point1 = -10;
    	double point2 = -120;
    	double point3 = -120;
    	double totalArc = -125;
    	
    	double heading1 = 60;
    	double heading2 = 42;
    	double heading3 = 42;
    	double heading4 = 42;

    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(77,1.0,0));
    	addSequential(new GyroTurn(1,60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(65,1.0,60));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
//    	addSequential(new DriveEncoder(-10,1.0,60)); // Go back if we dont want to do balls
    	addParallel(new DriveEncoderCurveSimple(totalArc, 0.8, 0.2, heading1, heading2, heading3, heading4, point1, point2, point3));
//    	addParallel(new DriveEncoderCurveSimple(-125, 0.8, 0.2, -60, -42, -42, -42, -10, -120, -120));
    	addParallel(new BallDoorOpen());
//    	addParallel(new BallLowGoalAuton(point3));
    }
}
