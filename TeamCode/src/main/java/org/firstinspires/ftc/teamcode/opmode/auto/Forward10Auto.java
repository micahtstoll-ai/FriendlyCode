package org.firstinspires.ftc.teamcode.opmode.auto;

import static com.pedropathing.api.Paths.line;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Forward 10in Auto")
public class Forward10Auto extends LinearOpMode {
    private static final double DISTANCE_INCHES = 10.0;

    @Override
    public void runOpMode() {
        Follower follower = Constants.create(hardwareMap);

        // Robot-relative: start at the origin, +x is forward, heading held at 0.
        Pose startPose = Pose.zero();
        Pose endPose = new Pose(DISTANCE_INCHES, 0, 0);
        Path forward = line(startPose, endPose).constant(0);

        waitForStart();

        follower.setPose(startPose);
        follower.update();
        follower.follow(forward);

        while (opModeIsActive() && !follower.atParametricEnd()) {
            follower.update();
            telemetry.addData("Pose", follower.pose());
            telemetry.update();
        }

        // Stop the drive instead of holding: hold(endPose) overshot back and
        // oscillated on the robot. Zero manual power, same calls PedroTeleop uses.
        DrivePowers stop = new DrivePowers(0, 0, 0);
        while (opModeIsActive()) {
            follower.manual(stop);
            follower.update();
            telemetry.addData("Pose", follower.pose());
            telemetry.update();
        }
    }
}
