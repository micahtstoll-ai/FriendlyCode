//package org.firstinspires.ftc.teamcode.nordicStorm.subsystems;
//
//import com.arcrobotics.ftclib.command.SubsystemBase;
//import com.pedropathing.api.PoseFactory;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.math.Pose;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.nordicStorm.NordicConstants;
//import org.firstinspires.ftc.teamcode.pedro.Constants;
//
///**
// * Drivetrain Subsystem.
// * Most drivetrain functions are handled naturally by PP
// */
//public class DriveTrain extends SubsystemBase {
//
//    public final Follower follower;
//    private final PoseFactory p = PoseFactory.degrees();
//    private final Pose shootingPose;
//
//    public DriveTrain(final HardwareMap hardwareMap, NordicConstants.AllianceColor allianceColor) {
//        follower = Constants.create(hardwareMap);
//        shootingPose = allianceColor == NordicConstants.AllianceColor.RED ? NordicConstants.redGoalPose : NordicConstants.blueGoalPose;
//    }
//
//    /**
//     * Find the angle the robot needs to face the goal.
//     */
//    public double getAngleToGoal() {
//        Pose position = follower.pose();
//        double angle = Math.atan((shootingPose.x() - position.x()) / (shootingPose.y() - position.y()));
//        return (Math.PI / 2 - angle);
//    }
//}
