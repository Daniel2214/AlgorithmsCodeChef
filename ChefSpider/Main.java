import java.util.Scanner;
class Point {
    private double x;
    private double y;
    public Point(){
        this.x = this.y = 0.0;
    }
    public Point(double x, double y) {
        this.x = x; this.y = y;
    }
    public Point(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void setLocation(double x, double y) {
        this.x = x; this.y = y;
    }
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}

public class ChrisSolution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfSwingPoints = scanner.nextInt();
        Point[] swingPoints = new Point[numberOfSwingPoints];
        Point[] visitedPoints = new Point[swingPoints.length + 1];
        for (int i = 0; i < swingPoints.length; i++) { // Get all points from input
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            swingPoints[i] = new Point(x, y);
        }
        double[] timeSegments = calculateTimeSegments(swingPoints, visitedPoints);
        int numberOfQueries = scanner.nextInt();
        for (int i = 0; i < numberOfQueries; i++) {
            int batX = scanner.nextInt();
            int batY = scanner.nextInt();
            Point bat = new Point(batX, batY);
            int circleIndex = findNearestCircleIndex(visitedPoints, timeSegments, bat);
            Point swingCircle = swingPoints[circleIndex];
            Point swingOrigin = visitedPoints[circleIndex];
            double time = timeSegments[circleIndex];
            System.out.println(findMinimumDistance(swingCircle, swingOrigin, bat, time));
        }
    }

    public static double[] calculateTimeSegments(Point[] swingPoints, Point[] visitedPoints) {
        double[] timeSegments = new double[swingPoints.length + 1];
        visitedPoints[0] = new Point(0, 0);
        for (int i = 0; i < swingPoints.length; i++) {
            double swingX = swingPoints[i].getX();
            double swingY = swingPoints[i].getY();
            double distance = getDistance(swingPoints[i], visitedPoints[i]);
            timeSegments[i + 1] = timeSegments[i] + distance * (Math.PI / 2); // When i = 0, timeSegments[i] = 0.0
            double angle = getAngle(swingPoints[i], visitedPoints[i]) + (Math.PI / 2); //Get angle and add 90°
            double newX = distance * Math.cos(angle) + swingX;
            double newY = distance * Math.sin(angle) + swingY;
            Point newVisited = new Point(newX, newY);
            visitedPoints[i + 1] = newVisited;
        }
        return timeSegments;
    }

    public static int findNearestCircleIndex(Point[] visitedPoints, double[] timeSegments,  Point bat) {
        int left = 0;
        int right = visitedPoints.length - 1;
        while (right - left > 1) {
            int mid = (left + right) / 2; //midpoint
            double distance = getDistance(visitedPoints[mid], bat);
            if (distance <= timeSegments[mid]) {
                right = mid; //Search at left
            } else {
                left = mid; //Search at right
            }
        }
        return left;
    }

    public static double findMinimumDistance(Point swingCircle, Point swingOrigin, Point bat, double time) {
        double radius = getDistance(swingCircle, swingOrigin);
        double angle = getAngle(swingCircle, swingOrigin);
        double minAngle = 0;
        double maxAngle = Math.PI / 2;
        double distance = 0;
        double prevDistance;
        do  {
            double midAngle = (minAngle + maxAngle) / 2;
            double xOnCircle = radius * Math.cos(angle + midAngle) + swingCircle.getX();
            double yOnCircle = radius * Math.sin(angle + midAngle) + swingCircle.getY();
            Point testPoint = new Point(xOnCircle, yOnCircle);
            prevDistance = distance;
            distance = getDistance(testPoint, bat);
            double timeSegment = time + midAngle * radius;
            if (distance <= timeSegment) {
                maxAngle = midAngle;
            } else {
                minAngle = midAngle;
            }
        } while (Math.abs(prevDistance - distance) > 1e-08);
        return distance;
    }

    public static double getAngle(Point a, Point b) {
        double displacedY = b.getY() - a.getY();
        double displacedX = b.getX() - a.getX();
        return Math.atan2(displacedY, displacedX);
    }

    public static double getDistance(Point a, Point b) {
        return Math.sqrt(square(b.getX()- a.getX()) + square(b.getY() - a.getY()));
    }

    public static double square(double x) {
        return x * x;
    }
}
