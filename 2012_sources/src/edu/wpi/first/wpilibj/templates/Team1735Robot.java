/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team1735Robot extends SimpleRobot {

    //Joysticks
    Joystick leftdrivestick = new Joystick(1);
    Joystick rightdrivestick = new Joystick(2);
    Joystick operatorstick = new Joystick(3);

    //Motors outputs
    Victor LeftDrive = new Victor(1);
    Victor LeftDrive2 = new Victor(2);
    Victor RightDrive = new Victor(3);
    Victor RightDrive2 = new Victor(4);
    Servo leftdriveservo = new Servo(5);
    Servo rightdriveservo = new Servo(6);
    Victor bottomrollers = new Victor(7);
    Victor toprollers = new Victor(8);
    Jaguar kachop = new Jaguar(9);
    Jaguar shooter = new Jaguar(10);
    
    

    //Sensors
    Encoder shooterEncoder = new Encoder(1, 2); //digital inputs
    AnalogChannel fenderfinder = new AnalogChannel(1);
    AnalogChannel USFront = new AnalogChannel(2); 

    Solenoid light = new Solenoid(1);
    //Solenoid light2 = new Solenoid(2);
    
    //Driveline
    RobotDrive drive = new RobotDrive(LeftDrive, LeftDrive2, RightDrive, RightDrive2);
    
    //Smart Dashboard
    SendableChooser autoChooser;
    SendableChooser autoDelay;

    //Misc Variables
    Timer speedtimer = new Timer();
    Timer matchTime = new Timer();
    boolean lowspeed = false;
    double shooterSpeed = 0.0;
    
    int samples[] = new int[5];
    
    public void robotInit() {
        //shooterEncoder.start();
        light.set(false);
        //light2.set(false);
        
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Dead Rechoning", "0");
        autoChooser.addObject("Do Nothing", "1");
        autoChooser.addObject("Unload", "2");
        autoChooser.addObject("US Forward Auto", "3");
        autoChooser.addObject("US Full Auto", "4");
        SmartDashboard.putData("Autonomous", autoChooser);
        
        autoDelay = new SendableChooser();
        autoDelay.addDefault("0s", new Double(0.0));
        autoDelay.addObject("1s", new Double(1.0));
        autoDelay.addObject("2s", new Double(2.0));
        autoDelay.addObject("3s", new Double(3.0));
        autoDelay.addObject("4s", new Double(4.0));
        autoDelay.addObject("5s", new Double(5.0));
        autoDelay.addObject("6s", new Double(6.0));
        autoDelay.addObject("7s", new Double(7.0));
        autoDelay.addObject("8s", new Double(8.0));
        autoDelay.addObject("9s", new Double(9.0));
        autoDelay.addObject("10s", new Double(10.0));
        SmartDashboard.putData("Auto Delay", autoDelay);

        SmartDashboard.putString("Gear", "Low");
    }
    
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        //Go to low gear
        leftdriveservo.set(.2);
        rightdriveservo.set(.2);
        //System.out.println(autoChooser.getSelected().toString());
        if (autoChooser.getSelected().toString() == "0") {
            //disable watchdog
            drive.setSafetyEnabled(false);
            //Delay
            Timer.delay( ((Double) autoDelay.getSelected()).doubleValue() );
            //drive straight
            drive.arcadeDrive(-.5, 0.0);
            Timer.delay(2.5);
            //Turn
            drive.arcadeDrive(.40, 1.0);
            Timer.delay(0.4);
            //drive straight
            drive.arcadeDrive(-.5, 0.0);
            Timer.delay(0.5);
            //reverse temporarily to stop -- try to fake brake mode
            drive.arcadeDrive(.1, 0.0);
            //turn shooter on
            shooter.set(-1.0);
            Timer.delay(0.03);
            //stop driving
            drive.arcadeDrive(-.5, 0.0);
            //let the shooter get momentum
            Timer.delay(1.1);
            //turn on rollers
            drive.arcadeDrive(0.0, 0.0);
            Timer.delay(.5);
            toprollers.set(1.0);
            Timer.delay(.5);
            toprollers.set(0.0);
            Timer.delay(2.0);
            toprollers.set(1.0);
            Timer.delay(2.25);
            //turn everything off
            drive.arcadeDrive(0.0, 0.0);
            shooter.set(0.0);
            toprollers.set(0.0);
            //while (true) {
            //    SmartDashboard.putDouble("Distance", USFront.getValue());
            //    Timer.delay(.1);
            //}
        } else if (autoChooser.getSelected().toString() == "2") {
            //disable watchdog
            drive.setSafetyEnabled(false);
            //Delay
            Timer.delay( ((Double) autoDelay.getSelected()).doubleValue() );
            toprollers.set(-1.0);
            bottomrollers.set(1.0);
            Timer.delay(4.0);
            toprollers.set(0.0);
            bottomrollers.set(0.0);
        } else if (autoChooser.getSelected().toString() == "3" || autoChooser.getSelected().toString() == "4") {
                        //disable watchdog
            drive.setSafetyEnabled(false);
            //Delay
            Timer.delay( ((Double) autoDelay.getSelected()).doubleValue() );
            //drive straight
            resetDist();
            while (getFrontDist() > 43 && isAutonomous() && isEnabled()) {
                //SmartDashboard.putDouble("Distance", USFront.getValue());
                drive.arcadeDrive(-.5, 0.0);
            }
            //Timer.delay(2.5);
            //Turn
            drive.arcadeDrive(.4, 1.0);
            Timer.delay(0.4);
            //drive straight
            if (autoChooser.getSelected().toString() == "4") {
                resetDist();
                while (getFrontDist() > 240 && isAutonomous() && isEnabled()) {
                    drive.arcadeDrive(-.5, 0.0);
                }
            } else {
                drive.arcadeDrive(-.6, 0.0);
                Timer.delay(0.45);
            }
            //reverse temporarily to stop -- try to fake brake mode
            drive.arcadeDrive(.1, 0.0);
            //turn shooter on
            shooter.set(-1.0);
            Timer.delay(0.03);
            //stop driving
            drive.arcadeDrive(-.5, 0.0);
            //let the shooter get momentum
            Timer.delay(1.1);
            //turn on rollers
            drive.arcadeDrive(0.0, 0.0);
            Timer.delay(.5);
            toprollers.set(1.0);
            Timer.delay(.5);
            toprollers.set(0.0);
            Timer.delay(2.0);
            toprollers.set(1.0);
            Timer.delay(2.25);
            //turn everything off
            drive.arcadeDrive(0.0, 0.0);
            shooter.set(0.0);
            toprollers.set(0.0);
            //while (true) {
            //    SmartDashboard.putDouble("Distance", USFront.getValue());
            //    Timer.delay(.1);
            //}
        }
    }

    int counter = 0;

    public int getFrontDist() {
        samples[counter] = USFront.getValue();
        counter++;
        if(counter>4) counter = 0;
        int sum = 0;
        for (int i = 0; i<5; i++) sum += samples[i];
        //SmartDashboard.putInt("Distance", (sum/5));
        //SmartDashboard.putInt("Raw Dist", USFront.getValue());
        return sum / 5;
    }

    public void resetDist() {
        for (int i = 0; i<5; i++) samples[i] = 300;
    }
    
    /**
     * This function is called once each time the robot enters operator control.
     */
    
    int ucount = 0;
    
    public void operatorControl() {
        //enable watchdog
        drive.setSafetyEnabled(true);
        speedtimer.reset();
        matchTime.start();
        matchTime.reset();
        while (isOperatorControl() && isEnabled()) {
            if (operatorstick.getRawButton(10)) {
                drive.tankDrive(leftdrivestick.getY()/2, rightdrivestick.getY()/2);
            } else if (operatorstick.getRawButton(11)) {
                drive.tankDrive(leftdrivestick.getY()/4*3, rightdrivestick.getY()/4*3);
            } else {
                drive.tankDrive(leftdrivestick, rightdrivestick);
            }
            //Calculate Gear Speed
            if (leftdrivestick.getTrigger()) {
                //Use a timer to check if it is the same button press or not
                if (speedtimer.get() > 0) {
                    if (speedtimer.get() > .5) {
                        speedtimer.stop();
                        speedtimer.reset();
                    }
                } else {
                    speedtimer.start();
                    if (lowspeed) {
                        lowspeed = false;
                    } else {
                        lowspeed = true;
                    }
                }
            }
            
            if (lowspeed) {
                leftdriveservo.set(0.2);
                rightdriveservo.set(0.2);
                SmartDashboard.putString("Gear", "High");
            } else {
                leftdriveservo.set(0.75);
                rightdriveservo.set(0.75);
                SmartDashboard.putString("Gear", "Low");
            }
            
            if (rightdrivestick.getTrigger() || operatorstick.getRawButton(4)) {
                bottomrollers.set(-1.0);
                SmartDashboard.putString("Pickup", "Forward");
            } else if (rightdrivestick.getRawButton(2)) {
                bottomrollers.set(1.0);
                SmartDashboard.putString("Pickup", "Reverse");
            } else {
                bottomrollers.set(0.0);
                SmartDashboard.putString("Pickup", "Off");
            }
            
            
            if (rightdrivestick.getRawButton(4) || operatorstick.getRawButton(4)) {
                toprollers.set(1.0);
                SmartDashboard.putString("Delivery", "Forward");
            } else if (rightdrivestick.getRawButton(5) || operatorstick.getRawButton(5)) {
                toprollers.set(-1.0);
                SmartDashboard.putString("Delivery", "Reverse");
            } else {
                toprollers.set(0.0);
                SmartDashboard.putString("Delivery", "Off");
            }

//            SmartDashboard.putDouble("Distance", fenderfinder.getRangeInches());
//            if (fenderfinder.getDistanceUnits().value < 14) {
            //light.set(true);
                //light2.set(true);
//            } else {
//                light.set(false);
                //light2.set(false);
//            }

            //SmartDashboard.putDouble("Distance", fenderfinder.getValue());
            if ((Math.floor(matchTime.get()) > 90) && (Math.floor(matchTime.get()) < 111)) {
                light.set((Math.floor(matchTime.get()) % 2) == 0);
            } else if (fenderfinder.getValue() < 15) {
                light.set(true);
            } else {
                light.set(false);
            }
                
            //shooterSpeed = getShooterSpeed();
            //SmartDashboard.putDouble("Shooter Speed", shooterSpeed);
            if (operatorstick.getTrigger()) {
                //if (shooterSpeed > 55) {
                //    shooter.set(-0.95);
                //} else {
                    shooter.set(-1.0);                    
                //}
                SmartDashboard.putString("Shooter", "Forward");
            } else if (operatorstick.getRawButton(2)) {
                shooter.set(1.0);
                SmartDashboard.putString("Shooter", "Reverse");
            } else {
                shooter.set(0.0);
                SmartDashboard.putString("Shooter", "Off");
            }
            
            if (operatorstick.getRawButton(6)) {
                kachop.set(1.0);
            } else if (operatorstick.getRawButton(7)) {
                kachop.set(-1.0);
            } else {
                kachop.set(0.0);
            }
            
            
            
            Timer.delay(0.005);
        }
    }
}
