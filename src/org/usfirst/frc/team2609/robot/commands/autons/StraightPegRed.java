package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderCurveSimple;
import org.usfirst.frc.team2609.robot.commands.DriveEncoderUltra;
import org.usfirst.frc.team2609.robot.commands.DriveTimer;
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
    	addSequential(new DriveTimer(0.2,5));
    	addSequential(new ClawOpen());
    	addSequential(new DriveTimer(-0.3,1));
    }
}
