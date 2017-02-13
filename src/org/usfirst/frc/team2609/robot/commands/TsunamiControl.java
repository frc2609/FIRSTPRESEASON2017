package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.TsunamiDirection;
import org.usfirst.frc.team2609.robot.subsystems.Tsunami;

import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;

public class TsunamiControl extends Command {
	TsunamiDirection direction;
    public TsunamiControl(TsunamiDirection direction) {
    	//requires(Robot.tsunami);
    	this.direction = direction;
    }

    protected void initialize() {
    	RobotMap.tsunamiMotor.changeControlMode(TalonControlMode.PercentVbus);
    }

    protected void execute() {
    	if(direction == TsunamiDirection.STOP){
    		Tsunami.pullUp(0);
//    		RobotMap.tsunamiMotor.set(0);
    	}
    	else if(direction == TsunamiDirection.UP){
    		Tsunami.pullUp(1);
//    		RobotMap.tsunamiMotor.set(1);
    	}
    	else if(direction == TsunamiDirection.DOWN){
    		Tsunami.pullUp(-1);
//    		RobotMap.tsunamiMotor.set(-1);
    	}
    	else{
    		System.out.println("NO DIRECTION FOUND!!");
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	RobotMap.tsunamiMotor.set(0);
    }

    protected void interrupted() {
    	RobotMap.tsunamiMotor.set(0);
    }
}
