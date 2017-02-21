package org.usfirst.frc.team2609.robot.commands.autons;
import org.usfirst.frc.team2609.robot.commands.DriveEncoder;
import org.usfirst.frc.team2609.robot.commands.EncReset;
import org.usfirst.frc.team2609.robot.commands.GyroTurn;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpen;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftPBlue extends CommandGroup {

	protected void initialize(){
	}
	
    public LeftPBlue() {

    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(75,1.0,0));
    	addSequential(new GyroTurn(1,60));
    	addSequential(new EncReset());
    	addSequential(new DriveEncoder(63,1.0,60));
    	addSequential(new ClawOpen());
//    	addSequential(new EncReset());
//    	addSequential(new DriveEncoder(-63,1.0,60));
//    	addSequential(new GyroTurn(1,0));
//    	addSequential(new EncReset());
//    	addSequential(new DriveEncoder(100,1.0,0));
    }
}
