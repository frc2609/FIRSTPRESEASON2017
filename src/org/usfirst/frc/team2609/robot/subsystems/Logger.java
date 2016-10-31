package org.usfirst.frc.team2609.robot.subsystems;
 
import java.io.*;
import java.util.Date;

import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
public class Logger {
   
    private BufferedWriter writer;
    private boolean logging =false; 
    private final String loggerBoolean = "Logging";
    private static Logger instance;
    private String fileName ="";
    private final String SDFileName = "File Name: ";
    DriverStation ds;
    
    private int max = 0;
    
    private String path;
    
    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }
        return instance;
    }
 
    private Logger() {
        this.ds = DriverStation.getInstance();
        SmartDashboard.putBoolean(this.loggerBoolean, this.logging);
        this.logging= SmartDashboard.getBoolean(this.loggerBoolean);
        SmartDashboard.putString(this.SDFileName, this.fileName);
        this.fileName = SmartDashboard.getString(SDFileName);
        File f = new File("/simlogs");
        if(!f.exists()) {
        	f.mkdir();
        }
        
    	File[] files = new File("/simlogs").listFiles();
    	if(files != null) {
	        for(File file : files) {
	            if(file.isFile()) {
	                System.out.println(file.getName());
	                try {
	                    int index = Integer.parseInt(file.getName().split("_")[0]);
	                    if(index > max) {
	                        max = index;
	                    }
	                } catch (Exception e){
	                    e.printStackTrace();
	                }
	            }
	        }
    	} else {
    		max = 0;
    	}
    }
	    
    public void openFile() {
    	if(this.wantToLog() || this.ds.isFMSAttached()){
	        try{
	            path = this.getPath();
	            this.writer = new BufferedWriter(new FileWriter(path));
	            this.writer.write("time, encLeft, encRight, rateLeft, rateRight, yaw, angle, victorLeft, victorRight");
	            this.writer.newLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    private String getPath() {
    	this.fileName = SmartDashboard.getString(SDFileName);
        if(this.ds.isFMSAttached()) {
            return String.format("/simlogs/%d_%s_%d_log.txt", ++this.max, this.ds.getAlliance().name(), this.ds.getLocation());
        }else if(this.fileName != null){ 
        	return String.format("/simlogs/%d_%s.txt",++this.max,this.fileName);
        }else {
            return String.format("/simlogs/%d_log.txt", ++this.max);
        }
    }
   
    public void logAll() {
    	if(this.wantToLog()){
	        try {
	        	//int ,%d
	        	//double ,%.3f
	        	this.writer.write(String.format("%d", new java.util.Date().getTime()));
	            this.writer.write(String.format(",%.3f", RobotMap.driveEncLeft.getDistance()));
	            this.writer.write(String.format(",%.3f", RobotMap.driveEncRight.getDistance()));
	            this.writer.write(String.format(",%.3f", RobotMap.driveEncLeft.getRate()));
	            this.writer.write(String.format(",%.3f", RobotMap.driveEncRight.getRate()));
	            
	            this.writer.write(String.format(",%.3f", (double)RobotMap.ahrs.getYaw()));
	            this.writer.write(String.format(",%.3f", (double)RobotMap.ahrs.getAngle()));
	            
	            this.writer.write(String.format(",%.3f",RobotMap.driveVictorLeft1.get()));
	            this.writer.write(String.format(",%.3f",RobotMap.driveVictorRight1.get()));
	            
	            
	            
	            
	           
	            
	            this.writer.newLine();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    public boolean wantToLog(){
    	this.logging= SmartDashboard.getBoolean(this.loggerBoolean);
    	return this.logging;
    }
    
    
    
    public void close() {
    	if(this.wantToLog()){
	    	if(this.writer != null) {
	            try {
	                this.writer.close();
	            }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	    	}
    	}
    }
}
