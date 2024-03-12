package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="test_launcher")
public class test_launcher extends LinearOpMode{

    DcMotor motor1;
    boolean ButtonA;
    boolean ButtonB;
    boolean ButtonX;
    float power = 0;

    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("motor1");
        waitForStart();
        while (opModeIsActive()) {
            ButtonA = gamepad1.a;
            ButtonB = gamepad1.b;
            ButtonX = gamepad1.x;

            if (ButtonA) {
                power += 0.05;
                motor1.setPower(power);
                sleep(500);
            }
            if (ButtonB) {
                power -= 0.05;
                motor1.setPower(power);
                sleep(500);
            }
            if (ButtonX){
                motor1.setPower(0);
            }
            telemetry.addData("Motor Speed:", motor1.getPower());
            telemetry.update();
        }
        motor1.setPower(0);
    }

}