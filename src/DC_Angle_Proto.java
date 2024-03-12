package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name="Johnson")
public class DC_Angle_Proto extends LinearOpMode {
    boolean setPointIsNotReached;
    double Kp = 0.2;
    double Ki = 0.4;
    double Kd = 0.066;
    double error;
double out;
double derivative;
    DcMotor Front_Right_Wheel = null; //Dc Motor Variable for Front Right Wheel
    DcMotor Front_Left_Wheel  = null; //Dc Motor Variable for Front Left Wheel
    DcMotor Back_Right_Wheel  = null; //Dc Motor Variable for Back Right Wheel
    DcMotor Back_Left_Wheel   = null;


    double integralSum = 0;

    double lastError = 0;
    double desiredAngle;
    IMU.Parameters myIMUparameters;
    IMU imu;
    YawPitchRollAngles robotOrientation;


    double heading;
    double dr;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        imu = hardwareMap.get(IMU.class, "imu");
        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        imu.initialize(myIMUparameters);
        imu.resetYaw();
        Front_Right_Wheel = hardwareMap.get(DcMotor.class, "Fr_motor");
        Front_Right_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Front_Left_Wheel  = hardwareMap.get(DcMotor.class, "Fl_motor");
        Back_Right_Wheel  = hardwareMap.get(DcMotor.class, "Br_motor");
        Back_Right_Wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Back_Left_Wheel   = hardwareMap.get(DcMotor.class, "Bl_motor");
        waitForStart();

        while(opModeIsActive()){
            desiredAngle = Math.atan(gamepad1.right_stick_y/gamepad1.right_stick_x);
            robotOrientation = imu.getRobotYawPitchRollAngles();
            heading = robotOrientation.getYaw(AngleUnit.RADIANS);
            getAngle(angleWrap(desiredAngle - heading));
            dr = out;
            telemetry.addData("Out", String.valueOf(dr));
            telemetry.update();
            Front_Left_Wheel.setPower(-dr);
            Front_Right_Wheel.setPower(dr);
            Back_Left_Wheel.setPower(-dr);
            Back_Right_Wheel.setPower(dr);
        }


    }
    // This function normalizes the angle so it returns a value between -180° and 180° instead of 0° to 360°.
    public double angleWrap(double radians) {

        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI) {
            radians += 2 * Math.PI;
        }

        // keep in mind that the result is in radians
        return radians;
    }
    public void getAngle(double radians){
        while (setPointIsNotReached) {


            // obtain the encoder position


            // rate of change of the error
            derivative = (error - lastError) / timer.seconds();

            // sum of all error over time
            integralSum = integralSum + (error * timer.seconds());

            out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);


            lastError = error;

            // reset the timer for next time
            timer.reset();

        }
    }
}
