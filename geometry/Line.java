/**
 * @author Kunal
 * Last revised: 2013
 */

package geometry;

import java.awt.geom.Point2D;

/**
 * Line in standard form: <br>
 * Ax + By + C = 0, B = 1 <br>
 * <br>
 * OR<br>
 * <br>
 * y = -(A/B)x - (C/B)
 */
public class Line {

	double A;	// x-coefficient
	double B;	// y-coefficient
	double C;	// constant term

	public static final double EPSILON = 0.0000000000000001;	// accuracy

	public Line() {
		A = 0;
		B = 0;
		C = 0;
	}

	public Line(Point2D.Double p1, Point2D.Double p2) {
		if (p1.x == p2.x) {
			/* Vertical Line */
			A = 1;
			B = 0;
			C = -p1.x;
		} else {
			double m = slope(p1, p2);
			B = 1;
			A = -m;
			C = -(A * p1.x) - (B * p1.y);	// C = -Ax1 - By1
		}
	}

	public Line(Point2D.Double p, double slope) {
		A = -slope;
		B = 1;
		C = -(A * p.x) - (B * p.y);	// C = -Ax1 - By1
	}

	public double slope() {
		return (-A / B);
	}

	public double x_int() {
		return (-C / A);
	}

	public double y_int() {
		return (-C / B);
	}

	public double y(int x) {
		return (-A * x - C) / B;
	}

	public double x(int y) {
		return -(B * y + C) / A;
	}

	public boolean parallel(Line l) {
		return equal(this.slope(), l.slope());
	}

	public boolean same_line(Line l) {
		return parallel(l) && equal(this.C, l.C);
	}

	public boolean perpendicular(Line l2) {
		return (-A / B) * (-l2.A / l2.B) == -1;
	}

	public boolean contains(Point2D.Double p) {
		return equal((A * p.x + B * p.y + C), 0);
	}

	/**
	 * The intersection point of lines l1 : y = m1x + b1 and l2 : y2 = m2x + b2 is
	 * the point where they are equal
	 * x = (b2 - b1) / (m1 - m2)
	 * y = m1x + b1
	 * 
	 * In standard form
	 * x = (B2C1 - B1C2) / (A2B1 - A1B2)
	 * y = -(Ax + C) / B
	 */
	public Point2D.Double intersection(Line l2) {
		if (same_line(l2)) {
			return new Point2D.Double(0, this.y(0));
		}

		if (parallel(l2)) {
			return new Point2D.Double(Double.NaN, Double.NaN);
		}

		Point2D.Double p = new Point2D.Double();
		p.x = (l2.B * this.C - this.B * l2.C) / (l2.A * this.B - this.A * l2.B);

		/* Vertical line test */
		if (isVertical(this))
			p.y = -(l2.A * p.x + l2.C) / l2.B;
		else
			p.y = -(this.A * p.x + this.C) / this.B;

		return p;
	}

	/**
	 * Basic idea: <br>
	 * theta - angle between line and horizontal <br>
	 * theta1 = atan(m1) <br>
	 * theta2 = atan(m2) <br>
	 * <br>
	 * Lines l1 : A1x + B1y + C1 = 0 and l2 : A2x + B2y + C2 = 0, <br>
	 * intersect at the angle theta given by: <br>
	 * tan(theta) = (A1B2 - A2B1) / (A1A2 + B1B2) <br>
	 * <br>
	 * For lines in slope-intercept form, this reduces to: <br>
	 * tan(theta) = (m2 - m1) / (1 + m1m2)
	 * 
	 * @return angle returned is in radians
	 */
	public double angle_of_intersection(Line l2) {
		if (same_line(l2))
			return 0;
		if (parallel(l2)) {
			return Double.NaN;
		}

		return Math.atan((this.A * l2.B - l2.A * B) / (A * l2.A + B * l2.B));
	}

	public double distance(Point2D.Double p) {
		return Math.abs(A * p.x + B * p.y + C) / Math.sqrt(A * A + B * B);
	}

	public Point2D.Double closest_point_on_line(Point2D.Double p) {
		Point2D.Double closest = new Point2D.Double();
		Line perpendicular;

		if (isVertical(this)) {
			closest.x = -C / A;
			closest.y = p.y;
		} else if (isHorizontal(this)) {
			closest.x = p.x;
			closest.y = -C / B;
		} else {
			perpendicular = new Line(p, (-1 / this.slope()));
			closest = intersection(perpendicular);
		}

		return closest;
	}

	/**
	 * The distance d between two parallel lines: <br>
	 * <h4>Point-slope form</h4> y = mx + b1 and y = mx + b2 <br>
	 * d = |b1 - b2| / sqrt(1 + m^2) <br>
	 * <br>
	 * <h4>Standard form</h4> Ax + By + C1 = 0 and Ax + By + C2 = 0 <br>
	 * d = |C1 - C2| / sqrt(A^2 + B^2)
	 */
	public double distance(Line l2) {
		if (!equal(this.slope(), l2.slope())) {
			System.out.println("Not parallel lines");
			return Double.NaN;
		}
		// TODO
		return 0;
	}

	public boolean equals(Line l2) {
		return (equal(A, l2.A) && equal(B, l2.B) && equal(C, l2.C));
	}

	public String toString() {
		return "Equation: " + A + "x + " + B + "y + " + C;
	}

	/* **********************************
	 * ********* STATIC METHODS *********
	 * **********************************/
	public static boolean equal(double d1, double d2) {
		return Math.abs(d1 - d2) <= EPSILON;
	}

	public static boolean isVertical(Line l) {
		return equal(l.B, 0);
	}

	public static boolean isHorizontal(Line l) {
		return equal(l.A, 0);
	}

	/**
	 * Slope of line containing two points <br>
	 * NOTE: Vertical line slope is returned as NaN
	 */
	public static double slope(Point2D.Double p1, Point2D.Double p2) {
		if (equal(p1.x, p2.x))
			return Double.NaN;
		double m = (p2.y - p1.y) / (p2.x - p1.x);
		return m;
	}

	public static Line point_to_line(Point2D.Double p1, Point2D.Double p2, Line line) {
		if (equal(p1.x, p2.x)) {
			/* Vertical Line */
			line.A = 1;
			line.B = 0;
			line.C = -p1.x;
		} else {
			double m = slope(p1, p2);
			line.B = 1;
			line.A = -m;
			line.C = -(line.A * p1.x) - (line.B * p1.y);	// c = -ax1 - by1
		}

		return line;
	}

	public static Line point_and_slope_to_line(Point2D.Double p, double m, Line line) {
		line.A = -m;
		line.B = 1;
		line.C = -(line.A * p.x) - (line.B * p.y);	// c = -ax1 - by1

		return line;
	}

	public static boolean parallel(Line l1, Line l2) {
		return equal(l1.slope(), l2.slope());
	}

	public static boolean same_line(Line l1, Line l2) {
		return parallel(l1, l2) && equal(l1.C, l2.C);
	}

}
