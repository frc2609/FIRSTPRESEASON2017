package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class RobotMap {
	public static Victor driveVictorLeft1;
	public static Victor driveVictorLeft2;
	public static Victor driveVictorRight1;
	public static Victor driveVictorRight2;
	public static Encoder driveEncLeft;
	public static Encoder driveEncRight;

	public static void init() {
		// DONT DEFINE THE OBJECT TYPE HERE!!1111!ONE ex. Victor  driveVictorLeft1 = new Victor(0);
		driveVictorLeft1 = new Victor(0);
		driveVictorLeft2 = new Victor(1);
		driveVictorRight1 = new Victor(2);
		driveVictorRight2 = new Victor(3);
		driveEncLeft = new Encoder(0, 2);
		driveEncRight = new Encoder(1, 3);
		driveEncLeft.setDistancePerPulse(1);
		driveEncRight.setDistancePerPulse(1.4);
	}
}
