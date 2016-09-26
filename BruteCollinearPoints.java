/**
 * Created by zhanlgu on 16/9/24.
 */
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        checkException(points);
        ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();
        Arrays.sort(points);
        int n = points.length;
        for (int i = 0; i < n-3; i++)
            for (int j = i + 1; j < n-2; j++)
                for (int k = j + 1; k < n-1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k]))
                        continue;
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))
                            storeSegments.add(new LineSegment(points[i], points[l]));
                    }
                }
        lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);
    }

    private void checkException(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new java.lang.NullPointerException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
//        return Arrays.copyOf(lineSegments, numberOfSegments());
//        LineSegment[] copySegment = new LineSegment[numberOfSegments()];
//        System.arraycopy(lineSegments, 0, copySegment, 0, numberOfSegments());
//        return copySegment;
        return lineSegments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
