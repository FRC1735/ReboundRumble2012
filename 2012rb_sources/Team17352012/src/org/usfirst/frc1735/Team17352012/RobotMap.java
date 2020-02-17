// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc1735.Team17352012;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController shootershooterMotor;
    public static SpeedController intakeintakeMotor;
    public static SpeedController deliverydeliveryMotor;
    public static SpeedController kachopkachopMotor;
    public static SpeedController drivelineLeftFront;
    public static SpeedController drivelineLeftRear;
    public static SpeedController drivelineRightFront;
    public static SpeedController drivelineRightRear;
    public static RobotDrive drivelineRobotDrive41;
    public static Solenoid lightgreenLight;
    public static AnalogChannel lightfenderFinder;
    public static Servo gearshifterServoRight;
    public static Servo gearshifterServoLeft;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        shootershooterMotor = new Jaguar(1, 10);
	LiveWindow.addActuator("Shooter", "shooterMotor", (Jaguar) shootershooterMotor);
        
        intakeintakeMotor = new Victor(1, 7);
	LiveWindow.addActuator("Intake", "intakeMotor", (Victor) intakeintakeMotor);
        
        deliverydeliveryMotor = new Victor(1, 8);
	LiveWindow.addActuator("Delivery", "deliveryMotor", (Victor) deliverydeliveryMotor);
        
        kachopkachopMotor = new Jaguar(1, 9);
	LiveWindow.addActuator("kachop", "kachopMotor", (Jaguar) kachopkachopMotor);
        
        drivelineLeftFront = new Victor(1, 1);
	LiveWindow.addActuator("Driveline", "LeftFront", (Victor) drivelineLeftFront);
        
        drivelineLeftRear = new Victor(1, 2);
	LiveWindow.addActuator("Driveline", "LeftRear", (Victor) drivelineLeftRear);
        
        drivelineRightFront = new Victor(1, 3);
	LiveWindow.addActuator("Driveline", "RightFront", (Victor) drivelineRightFront);
        
        drivelineRightRear = new Victor(1, 4);
	LiveWindow.addActuator("Driveline", "RightRear", (Victor) drivelineRightRear);
        
        drivelineRobotDrive41 = new RobotDrive(drivelineLeftFront, drivelineLeftRear,
              drivelineRightFront, drivelineRightRear);
	
        drivelineRobotDrive41.setSafetyEnabled(true);
        drivelineRobotDrive41.setExpiration(0.1);
        drivelineRobotDrive41.setSensitivity(0.5);
        drivelineRobotDrive41.setMaxOutput(1.0);
        lightgreenLight = new Solenoid(1, 1);
	LiveWindow.addActuator("Light", "greenLight", lightgreenLight);
        
        lightfenderFinder = new AnalogChannel(1, 1);
	LiveWindow.addSensor("Light", "fenderFinder", lightfenderFinder);
        
        gearshifterServoRight = new Servo(1, 6);
	LiveWindow.addActuator("Gear", "shifterServoRight", gearshifterServoRight);
        
        gearshifterServoLeft = new Servo(1, 5);
	LiveWindow.addActuator("Gear", "shifterServoLeft", gearshifterServoLeft);
        
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
