package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.autoClawOpen;
import org.usfirst.frc.team2609.robot.commands.BallDoorOpenAuton;
import org.usfirst.frc.team2609.robot.commands.BallLowGoalAuton;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimpleReverse;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.GyroTurnReverse;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.autoClawClose;
import org.usfirst.frc.team2609.robot.commands.gearRollerSetSpeed;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class doubleDragon extends CommandGroup {

	protected void initialize(){
	}
	
    public doubleDragon() {
    	double point1 = 15;
    	double point2 = 80;
    	double point3 = 80;
    	double totalArc = 80;
    	
    	double heading1 = 0;
    	double heading2 = 30;
    	double heading3 = 30;
    	double heading4 = 30;
    	
    	double point1a = 65;
    	double point2a = 80;
    	double point3a = 80;
    	double totalArca = 80;
    	
    	double heading1a = -30;
    	double heading2a = 0;
    	double heading3a = 0;
    	double heading4a = 0;
    	
//    	addSequential(new autoClawClose());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-84,1.0,0));
    	addSequential(new autoClawOpen());
    	addSequential(new EncReset());
    	addSequential(new VulcanGearMode());
    	addSequential(new gearRollerSetSpeed(0.8));
    	addSequential(new DriveEncoderCurveSimple(totalArc, 0.6, 0.4, heading1, heading2, heading3, heading4, point1, point2, point3));
    	addSequential(new autoClawClose());
    	addSequential(new gearRollerSetSpeed(0));
    	addSequential(new GyroTurnReverse(1,-30));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoderCurveSimpleReverse(totalArca, 0.6, 0.4, heading1a, heading2a, heading3a, heading4a, point1a, point2a, point3a));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(-84,1.0,0));
    }
}
