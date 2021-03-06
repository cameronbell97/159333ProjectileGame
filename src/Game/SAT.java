package Game;

import Game.Entities.Collision.CollisionBox;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cameron Bell - 03/04/2018
 * SAT Class
 * Contains SAT Algorithms for Checking Collision Between two Rotated Squares
 */
public class SAT {
    // Method - Check if Two Rotated Squares are Colliding //
    public static boolean isColliding(CollisionBox c1, CollisionBox c2) {
        List<Point2D> axes = new ArrayList<>();
        axes.addAll(getAxes(c1.getDirection()));
        axes.addAll(getAxes(c2.getDirection()));
        axes = axes.stream().map(Point2D::normalize).collect(Collectors.toList());

        List<Point2D> corns1 = getCorners(c1);
        List<Point2D> corns2 = getCorners(c2);

        // Check for overlap
        for (Point2D ax : axes) {
            // Get min & max values
            double min1 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
            double max1 = corns1.stream().mapToDouble(p -> p.dotProduct(ax)).max().getAsDouble();

            double min2 = corns2.stream().mapToDouble(p -> p.dotProduct(ax)).min().getAsDouble();
            double max2 = corns2.stream().mapToDouble(p -> p.dotProduct(ax)).max().getAsDouble();

            if(max1 < min2 || max2 < min1) return false; // Check for collision
        }

        return true;
    }

    // Method - Get Axes of square at a rotation //
    public static List<Point2D> getAxes(double rotation){
        return Arrays.asList(
                new Point2D(Math.cos(rotation), Math.sin(rotation)),
                new Point2D(Math.cos(rotation+(Math.PI/2)), Math.sin(rotation+(Math.PI/2)))
        );
    }

    // Method - Get Corner Vectors of Rotated Square //
    private static List<Point2D> getCornerVs(CollisionBox e) {
        return Arrays.asList(
                new Point2D(e.getXpos(), e.getYpos()),
                new Point2D(e.getXpos() + e.getWidth(), e.getYpos() + e.getHeight()),
                new Point2D(e.getXpos() + e.getWidth(), e.getYpos()),
                new Point2D(e.getXpos(), e.getYpos() + e.getHeight())
        ).stream().map(p -> p.subtract(e.getCentre())).collect(Collectors.toList());
        // p -> p.subtract(e.getCentre()) // is where the y axis gets fucked // turns to 17
    }

    // Method - Get Corners of Rotated Square //
    public static List<Point2D> getCorners(CollisionBox e) {
        return getCornerVs(e).stream().map(p -> new Point2D(
                p.getX() * Math.cos(e.getDirection()) - p.getY() * Math.sin(e.getDirection()),
                p.getX() * Math.sin(e.getDirection()) + p.getY() * Math.cos(e.getDirection())
                )).map(p -> p.add(e.getCentre())).collect(Collectors.toList());
    }
}
