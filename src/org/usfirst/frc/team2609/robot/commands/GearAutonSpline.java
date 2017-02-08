package org.usfirst.frc.team2609.robot.commands;

import java.io.File;

import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class GearAutonSpline extends Command {
	long time, fastGenTime, fastModTime;
	boolean generated;
    public GearAutonSpline() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	generated = false;
    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	time = System.currentTimeMillis();
//        Trajectory.Config configHigh = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 10, 8.5, 50.0);
//        Trajectory.Config configLow = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.01, 10, 8.5, 50.0);
        Trajectory.Config configFast = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.01, 5, 8.5, 50.0);
        Waypoint[] points = new Waypoint[] {
                new Waypoint(0, 0, Pathfinder.d2r(90)),
//                new Waypoint(0, -7, Pathfinder.d2r(90))
                new Waypoint(76.3/12, 114/12, Pathfinder.d2r(30))
        };
        fastGenTime = System.currentTimeMillis();
        Trajectory Fasttrajectory = Pathfinder.generate(points, configFast);
        fastGenTime = System.currentTimeMillis() - fastGenTime;
        fastModTime = System.currentTimeMillis();
        TankModifier Fastmodifier = new TankModifier(Fasttrajectory).modify(27.75/12);
        fastModTime = System.currentTimeMillis()-fastModTime;
        Trajectory left = Fastmodifier.getLeftTrajectory();
        Trajectory right = Fastmodifier.getRightTrajectory();
        double[][] rightpos = right.getPositions();
        double[][] leftpos = left.getPositions();
        RobotMap.leftPath = leftpos;
        RobotMap.rightPath = rightpos;
        System.out.println(RobotMap.leftPath.length);
        generated = true;
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return generated;
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("fastGenTime: " + fastGenTime);
        System.out.println("fastModTime: " + fastModTime);
        System.out.println("fastSumTime: " + (fastModTime+fastGenTime));
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
