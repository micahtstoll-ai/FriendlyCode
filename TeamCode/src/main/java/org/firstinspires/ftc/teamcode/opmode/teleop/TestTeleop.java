//package org.firstinspires.ftc.teamcode.opmode.teleop;
//
//import com.pedropathing.api.PoseFactory;
//import com.pedropathing.drivetrain.DrivePowers;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.follower.ManualDrive;
//import com.pedropathing.math.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.nordicStorm.Globals;
//import org.firstinspires.ftc.teamcode.nordicStorm.NordicConstants;
//import org.firstinspires.ftc.teamcode.nordicStorm.subsystems.Langskip;
//
//
//@TeleOp(name = "Test Teleop")
//public class TestTeleop extends OpMode {
//    private Follower follower;
//
//    private Langskip langskip;
//    public static Pose startingPose;
//    private final PoseFactory p = PoseFactory.degrees();
//
//    private boolean slowMode = true;
//    private final double slowModeMultiplier = .35;
//
//    private NordicConstants.AllianceColor allianceColor;
//
//
//    @Override
//    public void init() {
//        langskip = new Langskip(hardwareMap, Globals.ALLIANCE_COLOR, true);
//        langskip.innerSubsystem.setAtPoint(false);
//        follower = langskip.getFollower();
//        follower.setPose(p.of(72,72,90));
//        follower.update();
//
//        allianceColor = Globals.ALLIANCE_COLOR;
//    }
//
//    @Override
//    public void start() {
//        langskip.intake.turnOnLight();
//    }
//
//    @Override
//    public void loop() {
//        DrivePowers powers = ManualDrive.fieldCentric(
//                -gamepad1.left_stick_y,
//                gamepad1.left_stick_x,
//                gamepad1.right_stick_x,
//                follower.pose().heading()
//        );
//
//        ManualDrive.driveOrHold(follower, powers);
//        follower.update();
//
//
//        //Slow Mode
//        if (gamepad1.dpadLeftWasPressed()) {
//            slowMode = !slowMode;
//        }
//
//        // Pose Reset
//        if (gamepad1.dpadDownWasPressed()) {
//            follower.setPose(p.of(72,72,90));
//        }
//
//        // Intake
//        if (gamepad1.right_trigger > .5 && !langskip.intake.isRunning()) {
//            langskip.intake.runIntake(true);
//        }
//
//        if (gamepad1.left_trigger > .5 && !langskip.intake.isRunning()) {
//            langskip.intake.runIntakeReverse(true);
//        }
//
//        if (gamepad1.right_trigger < .1 && gamepad1.left_trigger < .1 && langskip.currentState == Langskip.State.IDLE && langskip.intake.isRunning()) {
//            langskip.intake.runIntake(false);
//        }
//
//        if (gamepad1.rightBumperWasPressed()) {
//        }
//
//        if (gamepad1.rightBumperWasReleased()) {
//        }
//
//        if (gamepad1.leftBumperWasPressed()) {
//        }
//
//        if (gamepad1.leftBumperWasReleased()) {
//        }
//
//        if (gamepad1.dpadRightWasPressed()) {
//        }
//
//        if (gamepad1.dpadRightWasReleased()) {
//        }
//
//        if (gamepad1.yWasPressed()) {
//        }
//
//        if (gamepad1.yWasReleased()) {
//        }
//
//        if (gamepad1.aWasPressed()) {
//        }
//
//        if (gamepad1.aWasReleased()) {
//        }
//
//        if (gamepad1.bWasPressed()) {
//        }
//
//        if (gamepad1.bWasReleased()) {
//        }
//    }
//}