package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.TimerDelay;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RightPRed extends CommandGroup {
	double autonDistance1 = 0;
	double autonDistance2 = 0;
	double autonDistance3 = 0;
	double autonHeading1 = 0;
	double autonHeading2 = 0;
	double autonHeading3 = 0;
	double autonAngle1 = 0;
	double autonAngle2 = 0;
	double autonAngle3 = 0;
	protected void initialize(){
//		autonDistance1 = (double)SmartDashboard.getNumber("auton distance 1: ",0);
//		autonDistance2 = (double)SmartDashboard.getNumber("auton distance 2: ",0);
//		autonDistance3 = (double)SmartDashboard.getNumber("auton distance 3: ",0);
//		autonHeading1 = (double)SmartDashboard.getNumber("auton heading 1: ",0);
//		autonHeading2 = (double)SmartDashboard.getNumber("auton heading 2: ",0);
//		autonHeading3 = (double)SmartDashboard.getNumber("auton heading 3: ",0);
//		autonAngle1 = (double)SmartDashboard.getNumber("auton angle 1: ",0);
//		autonAngle2 = (double)SmartDashboard.getNumber("auton angle 2: ",0);
//		autonAngle3 = (double)SmartDashboard.getNumber("auton angle 3: ",0);
	}
	
    public RightPRed() {
		autonDistance1 = (double)SmartDashboard.getNumber("auton distance 1: ",0);
		autonDistance2 = (double)SmartDashboard.getNumber("auton distance 2: ",0);
		autonDistance3 = (double)SmartDashboard.getNumber("auton distance 3: ",0);
		autonHeading1 = (double)SmartDashboard.getNumber("auton heading 1: ",0);
		autonHeading2 = (double)SmartDashboard.getNumber("auton heading 2: ",0);
		autonHeading3 = (double)SmartDashboard.getNumber("auton heading 3: ",0);
		autonAngle1 = (double)SmartDashboard.getNumber("auton angle 1: ",0);
		autonAngle2 = (double)SmartDashboard.getNumber("auton angle 2: ",0);
		autonAngle3 = (double)SmartDashboard.getNumber("auton angle 3: ",0);
    	//left gear auton with dashboard variables
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(75,1.0,0));
    	addSequential(new GyroTurn(1,-60));
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	addSequential(new DriveEncoder(63,1.0,-60));
    	addSequential(new ClawOpen());
    	addSequential(new EncReset());
    	addSequential(new TimerDelay(0.2));
    	//addSequential(new DriveEncoder(autonDistance3,1.0,autonHeading3)); 	
    	
    }
}
