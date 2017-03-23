package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.enums.BallDoorState;
import org.usfirst.frc.team2609.enums.VulcanClawState;
import org.usfirst.frc.team2609.enums.VulcanDeployState;
import org.usfirst.frc.team2609.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem to move the arm/door while staying inside our volume.
 */
// TODO: Set the solenoids in a better way. See: VulcanClaw.java todo comments
// TODO: !!!Add cases for neutral!!!
public class R03 extends Subsystem {
	BallDoorState currentBallDoorState;
	VulcanClawState currentVulcanClawState;
	VulcanDeployState currentVulcanDeployState;
	
   
	public void StayInsideR03(BallDoorState desiredState){
		getStates();

	}
	public void StayInsideR03(VulcanClawState desiredState){
		getStates();
		if(this.currentVulcanClawState == desiredState){
			// if the current state matches the desired state, dont do anything
			// can setting a solenoid again help get to it's actual place? if so, uncomment next line
			//Robot.vulcanclaw.forceSetClaw(Robot.vulcanclaw.setClaw(desiredState));
			return;
		}

		Robot.vulcanclaw.forceSetClaw(Robot.vulcanclaw.setClaw(desiredState));
	}
	public void StayInsideR03(VulcanDeployState desiredState){
		getStates();
		// Everytime we move the arm up/down, we are going outside of our volume at the front.
		// No matter what state the door is in, close it.
		// If the door is open, print out that it needed to be closed

		Robot.vulcanclaw.forceSetDeployClaw(Robot.vulcanclaw.setClaw(desiredState));
		
	}
	public void getStates(){
		this.currentVulcanClawState = Robot.vulcanclaw.getClawState();
		this.currentVulcanDeployState = Robot.vulcanclaw.getDeployState();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

