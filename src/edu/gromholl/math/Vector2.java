package edu.gromholl.math;

/**
 * @author GromHoll
 */
public class Vector2 {

    public double x;
    public double y;

    public Vector2() {
        this(0, 0);
    }
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double norm() {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2 mult(double scalar) {
        return new Vector2(x*scalar, y*scalar);
    }

    public Vector2 sum(Vector2 second) {
        return new Vector2(x + second.x, y + second.y);
    }

    public Vector2 minus(Vector2 second) {
        return new Vector2(x - second.x, y - second.y);
    }

    @Override
    public String toString() {
        return "[" + x + " ," + y + "]";
    }

    public double[] toArray() {
        return new double[] {x, y};
    }
}
