package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="Drone_Launcher")
public class DC_Drone_Launcher_Only extends LinearOpMode{
    DcMotor Drone_Motor  = null;
    CRServo Drone_Servo  = null;

        public void runOpMode() {
            Drone_Motor = hardwareMap.get(DcMotor.class, "Drone_Motor");
            Drone_Servo = hardwareMap.get(CRServo.class, "Drone_Servo");
            waitForStart();
            while(opModeIsActive()) {
                if (gamepad1.a) {
                    Drone_Motor.setPower(1);
                    sleep(500);

                    Drone_Servo.setPower(1);
                    sleep(300);
                    Drone_Motor.setPower(0);
                    Drone_Servo.setPower(0);
                }
            }
        }
}
