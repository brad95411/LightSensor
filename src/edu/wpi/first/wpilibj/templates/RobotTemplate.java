/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    DigitalInput lightSensor = new DigitalInput(1);
    DigitalInput lightSensor1 = new DigitalInput(2);
    
    DigitalInput autoswitch = new DigitalInput(3);
    DigitalInput autoswitch2 = new DigitalInput(4);
    
    DriverStationLCD dsLCD = DriverStationLCD.getInstance();
    DriverStation ds = DriverStation.getInstance();
    
    Counter lightCounter = new Counter();
    
    Joystick drive = new Joystick(1);
    Joystick second = new Joystick(2);
    
    Jaguar lt1 = new Jaguar(1);
    Jaguar rt1 = new Jaguar(2);
    
    RobotDrive driveTrain = new RobotDrive(lt1, rt1);
    
    Victor intake1 = new Victor(5);
    Victor intake2 = new Victor(6);
    
    Jaguar shoot1 = new Jaguar(3);
    Jaguar shoot2 = new Jaguar(4);
    
    Victor fisherRight = new Victor(8);
    Victor fisherLeft = new Victor(7);
    
    Victor poly = new Victor(9);
    
    Timer time = new Timer();
    
    boolean shoot = false, ON = false, OFF = false;
    
    private int count = 0, count1 = 0;
    
    private boolean tape = false, tape1 = false;
    
    private boolean init = true, init1 = true;
    
    private double loopTime = 0, loopTime1 = 0;
    
    private double val = 0, val1 = 0;
    
    private double shootVal1 = 0, shootVal2 = 0;
    
    private int teleop = 0, autoCount = 0, auto = 0;
    
    PIDSource pids = new PIDSource() {

        public double pidGet() {
            return val;
        }
    };
    
    PIDSource pids1 = new PIDSource() {

        public double pidGet() {
            return val1;
        }
    };
    
    PIDController pid = new PIDController(.7, 0, 0, pids, shoot2);
    PIDController pid1 = new PIDController(.7, 0, 0, pids1, shoot1);
    
    private boolean two;
    private boolean one;
    private boolean three;
    
    private double valueZ11 = 40;
    private double valueZ12 = 30;
    private double valueZ21 = 80;
    private double valueZ22 = 30;
    private double valueZ31 = 60;
    private double valueZ32 = 20;
    
    private boolean backwheel = false, frontwheel = false;
    
    private boolean onTarget = false;
    
    Timer time1 = new Timer();

    public void robotInit() {
    }

    public void disabledContinuous() {
        while (isDisabled()) {
            printLCD(5, "" + autoswitch.get() + " " + autoswitch2.get());
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit() {
        time1.reset();
        time1.start();
        pid1.enable();
        pid.enable();
    }

    public void autonomousPeriodic() {
//        if (autoswitch.get() == true) {
//            pid.setOutputRange(.78, .82);
//            pid1.setOutputRange(.28, .33);
//            pid.setSetpoint(30);
//            pid1.setSetpoint(30);
//        }
//        if (autoswitch.get() == false) {
//            pid.disable();
//            pid1.disable();

//            if (autoswitch.get() == true) {
//                while (isAutonomous() && isEnabled()) {
//                    if (time.get() > 15) {
//                        fisherRight.set(-.6);
//                        fisherLeft.set(.6);
//                        Timer.delay(.7);
//                        fisherRight.set(0);
//                        fisherLeft.set(0);
//                        Timer.delay(6);
//                        intake1.set(-1);
//                        intake2.set(0);
//                        poly.set(1);
//                        Timer.delay(5);
//                    }
//                }

        if (autoswitch.get() == false) {
            pid.setOutputRange(.65, .75);
            pid1.setOutputRange(.28, .33);
            pid.setSetpoint(70);
            pid1.setSetpoint(30);

        }
        if (autoswitch.get() == true) {
            pid.setOutputRange(.78, .82);
            pid1.setOutputRange(.28, .33);
            pid.setSetpoint(30);
            pid1.setSetpoint(30);
        }
//            if (autoswitch.get() == true && autoswitch2.get() == false) {
//                //add
//            }
        //pid for auto

        if (init == true) {
            loopTime = Timer.getFPGATimestamp();
            tape = lightSensor.get();
            init = false;
        }
        if (Math.abs(Timer.getFPGATimestamp() - loopTime) < .5) {
            if (lightSensor.get() && !tape) {
                tape = true;
                count++;
            } else if (!lightSensor.get() && tape) {
                tape = false;
                count++;
            }
        } else if (Math.abs(Timer.getFPGATimestamp() - loopTime) > .5) {
            val = count / .5;

            pids = new PIDSource() {

                public double pidGet() {
                    return val;
                }
            };
            count = 0;
            init = true;
        }

        ///lol

        if (init1 == true) {
            loopTime1 = Timer.getFPGATimestamp();
            tape1 = lightSensor1.get();
            init1 = false;
        }
        if (Math.abs(Timer.getFPGATimestamp() - loopTime1) < .5) {
            if (lightSensor1.get() && !tape1) {
                tape1 = true;
                count1++;
            } else if (!lightSensor1.get() && tape1) {
                tape1 = false;
                count1++;
            }
        } else if (Math.abs(Timer.getFPGATimestamp() - loopTime1) > .5) {
            val1 = count1 / .5;

            pids1 = new PIDSource() {

                public double pidGet() {
                    return val1;
                }
            };
            count1 = 0;
            init1 = true;

        }


        if (time1.get() > 5) {
            intake1.set(.8);//run lift up
            intake2.set(.8);
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
        tape = lightSensor.get();
        tape1 = lightSensor1.get();
        two = true;
        one = false;
        three = false;
    }

    public void teleopContinuous() {
        while (isOperatorControl() && isEnabled()) {


            if (ds.getBatteryVoltage() <= 8) {
                dsLCD.println(DriverStationLCD.Line.kUser5, 1, "WARNING BATTERY LOW");
            }

            if (one) {
                dsLCD.println(DriverStationLCD.Line.kUser3, 1, "Zone One   ");
            } else if (two) {
                dsLCD.println(DriverStationLCD.Line.kUser3, 1, "Zone Two   ");
            } else if (three) {
                dsLCD.println(DriverStationLCD.Line.kUser3, 1, "Zone Three");
            }

            driveTrain.arcadeDrive(drive.getY(), drive.getX());
            driveTrain.setSafetyEnabled(false);
            if (teleop % 5 == 0) {
                if (second.getRawButton(1)) {
                    pid.enable();
                    if (init == true) {
                        loopTime = Timer.getFPGATimestamp();
                        tape = lightSensor.get();
                        init = false;
                    }
                    if (Math.abs(Timer.getFPGATimestamp() - loopTime) < .5) {
                        if (lightSensor.get() && !tape) {
                            tape = true;
                            count++;
                        } else if (!lightSensor.get() && tape) {
                            tape = false;
                            count++;
                        }
                    } else if (Math.abs(Timer.getFPGATimestamp() - loopTime) > .5) {
                        val = count / .5;

                        pids = new PIDSource() {

                            public double pidGet() {
                                return val;
                            }
                        };
                        count = 0;
                        init = true;
                    }

                    ///lol
                    pid1.enable();
                    if (init1 == true) {
                        loopTime1 = Timer.getFPGATimestamp();
                        tape1 = lightSensor1.get();
                        init1 = false;
                    }
                    if (Math.abs(Timer.getFPGATimestamp() - loopTime1) < .5) {
                        if (lightSensor1.get() && !tape1) {
                            tape1 = true;
                            count1++;
                        } else if (!lightSensor1.get() && tape1) {
                            tape1 = false;
                            count1++;
                        }
                    } else if (Math.abs(Timer.getFPGATimestamp() - loopTime1) > .5) {
                        val1 = count1 / .5;

                        pids1 = new PIDSource() {

                            public double pidGet() {
                                return val1;
                            }
                        };
                        count1 = 0;
                        init1 = true;
                    }

                    ///lololol

//                if (tape && !lightSensor.get()) {
//                    count++;
//                } else if (!tape && lightSensor.get()) {
//                    count++;
//                }


                    dsLCD.println(DriverStationLCD.Line.kMain6, 1, "Count: " + pid.onTarget());
                    dsLCD.println(DriverStationLCD.Line.kUser2, 1, "Light:" + val);




                } else {
                    pid1.disable();
                    pid.disable();
                    shoot1.set(0);
                    shoot2.set(0);
                }



                if (drive.getRawButton(7) || second.getRawButton(7)) {
                    one = true;
                    two = false;
                    three = false;
                    pid.setOutputRange(.3, .6);
                    pid1.setOutputRange(.2, .6);
                    pid.setSetpoint(valueZ11);
                    pid1.setSetpoint(valueZ12);
                    shootVal1 = valueZ11;
                    shootVal2 = valueZ12;
                } else if (drive.getRawButton(9) || second.getRawButton(9)) {
                    one = false;
                    two = true;
                    three = false;
                    pid.setOutputRange(.3, .8);
                    pid1.setOutputRange(.25, .6);
                    pid.setSetpoint(valueZ21);
                    pid1.setSetpoint(valueZ22);
                    shootVal1 = valueZ21;
                    shootVal2 = valueZ22;
                } else if (drive.getRawButton(11) || second.getRawButton(11)) {
                    one = false;
                    two = false;
                    three = true;
                    pid.setOutputRange(.9, 3);
                    pid1.setOutputRange(.9, 3);
                    pid.setSetpoint(150);
                    pid1.setSetpoint(150);
                    shootVal1 = valueZ31;
                    shootVal2 = valueZ32;

                }
            }


            if (second.getRawButton(6)) {
                System.out.println("downerator down");
                fisherRight.set(.4);
                fisherLeft.set(-.4);
            } else if (second.getRawButton(4)) {
                System.out.println("downerator up");
                fisherRight.set(-.4);
                fisherLeft.set(.4);
            } else {
                System.out.println("downeratorstop");
                fisherLeft.set(0);
                fisherRight.set(0);
            }

            if ((second.getRawButton(5) || second.getRawButton(2))) {
                System.out.println("lift up");
                intake1.set(.8);//run lift up
                intake2.set(.8);
                poly.set(-.8);

            } else if (second.getRawButton(3)) {//run it down
                System.out.println("lift down");
                intake1.set(-1);
                intake2.set(-1);
                poly.set(.8);
            } else {//dont run lifts
                System.out.println("Stopped lift");
                intake1.set(0);
                intake2.set(0);
                poly.set(0);
            }

            dsLCD.updateLCD();
            teleop++;
        }
    }

    public void printLCD(int line, String text) {
        if (line == 1) {
            dsLCD.println(DriverStationLCD.Line.kMain6, 1, text);
        } else if (line == 2) {
            dsLCD.println(DriverStationLCD.Line.kUser2, 1, text);
        } else if (line == 3) {
            dsLCD.println(DriverStationLCD.Line.kUser3, 1, text);
        } else if (line == 4) {
            dsLCD.println(DriverStationLCD.Line.kUser4, 1, text);
        } else if (line == 5) {
            dsLCD.println(DriverStationLCD.Line.kUser5, 1, text);
        } else if (line == 6) {
            dsLCD.println(DriverStationLCD.Line.kUser6, 1, text);
        }
        dsLCD.updateLCD();
    }
}
