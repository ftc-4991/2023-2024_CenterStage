package org.firstinspires.ftc.teamcode;

import static java.lang.Math.round;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous(name = "Place Pixel on Backdrop")
public class placepixelonbackdrop extends LinearOpMode{
    //Declare motors and other variables
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor lift;
    Servo pixelDropper;
    private ElapsedTime runtime = new ElapsedTime();
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("Fl_motor");
        frontRight = hardwareMap.dcMotor.get("Fr_motor");
        backLeft = hardwareMap.dcMotor.get("Bl_motor");
        backRight = hardwareMap.dcMotor.get("Br_motor");
        lift = hardwareMap.dcMotor.get("Lift_1");
        pixelDropper = hardwareMap.servo.get("pixelDropper");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        PlacePixel();
    }
    void Movement(float fL, float fR, float bL, float bR, float l, int timeoutS, double speed) {
        int ntfl;
        int ntfr;
        int ntbl;
        int ntbr;
        int ntl;

        if(opModeIsActive()){
            ntfl = frontLeft.getCurrentPosition() + round(fL*1000);
            ntfr = frontRight.getCurrentPosition() + round(fR*1000);
            ntbl = backLeft.getCurrentPosition() + round(bL*1000);
            ntbr = backRight.getCurrentPosition() + round(bR*1000);
            ntl = lift.getCurrentPosition() + round(l*1000);
            frontLeft.setTargetPosition(ntfl);
            frontRight.setTargetPosition(ntfr);
            backLeft.setTargetPosition(ntbl);
            backRight.setTargetPosition(ntbr);
            lift.setTargetPosition(ntl);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);
            lift.setPower(speed);


            runtime.reset();
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeft.isBusy() && frontRight.isBusy() &&
                            backLeft.isBusy() && backRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Goal",  "Running to %7d :%7d :%7d :%7d", ntfl,  ntfr, ntbl, ntbr);
                telemetry.addData("Actual distance moved",  "Running at %7d :%7d :%7d :%7d :%7d" ,
                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(), backLeft.getCurrentPosition(), backRight.getCurrentPosition(), lift.getCurrentPosition());
                telemetry.addData("Speed", speed);
                telemetry.update();
            }


            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
            lift.setPower(0);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    void PlacePixel(){
        initAprilTag();

        if (opModeIsActive()) {
            Movement(1.7f, 1.7f, 1.7f, 1.7f, 0,5, 0.5);
            Movement(1.51f, -1.51f, 1.51f, -1.51f, 0,5, 0.5);
            Movement(1f, 1f, 1f, 1f,  0,5, 0.5);
            //if(findAprilTag(2)){
                placePixelOnBackdrop();
            //}
            /*Movement(-0.25f,0.25f,0.25f,-0.25f,0,5,0.5);
            if(findAprilTag(2)){
                placePixelOnBackdrop();
            }
            Movement(0.5f,-0.5f,-0.5f,0.5f,0,5,0.5);
            if(findAprilTag(2)){
                placePixelOnBackdrop();
            } else {
                Movement(0.5f,-0.5f,-0.5f,0.5f,0,5,0.5);
                Movement(0.5f,0.5f,0.5f,0.5f,0,5,0.5);
            }*/
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();
    }
    void placePixelOnBackdrop(){
        Movement(0,0,0,0,-10,5,1);
        pixelDropper.setPosition(1);
        sleep(500);
        pixelDropper.setPosition(0);
        Movement(0,0,0,0,10,5,1);
    }
    private void initAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        //aprilTag.setDecimation(3);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()


    /**
     * Add telemetry about AprilTag detections.
     *
     * @return
     */
    public boolean findAprilTag(int id) {

        boolean detected = false;

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
            if(detection.id == id){
                detected = true;
                break;
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

        return detected;
    }
}
