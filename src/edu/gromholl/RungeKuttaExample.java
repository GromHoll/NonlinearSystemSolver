package edu.gromholl;

import edu.gromholl.math.Polynomial;
import edu.gromholl.math.Vector2;

import static edu.gromholl.DataExample.firstFunction;
import static edu.gromholl.DataExample.secondFunction;

/**
 * @author GromHoll
 */
public class RungeKuttaExample {

    public static Polynomial.Variable X1 = new Polynomial.Variable("x");
    public static Polynomial.Variable X2 = new Polynomial.Variable("y");
    public static Polynomial FIRST_FUNCTION = firstFunction(X1, X2);
    public static Polynomial SECOND_FUNCTION = secondFunction(X1, X2);

    public static void main(String[] args) {
        double error = 0.01;
        double time = 0.0;
        double step = 0.01;
        Vector2 solution = new Vector2();
        Vector2 newSolution;

        int iter = 0;
        while (time < 1) {
            Vector2 k1 = function(solution, time).mult(step);
            Vector2 k2 = function(solution.sum(k1), time + step*(1d/3d)).mult(step).mult(1d/3d);
            Vector2 k3 = function(solution.sum(k1.mult(0.5)).sum(k2.mult(0.5)), time + step*(1d/3d)).mult(step).mult(1d/3d);
            Vector2 k4 = function(solution.sum(k1.mult(3d/8d)).sum(k3.mult(9d/8d)), time + step*0.5).mult(step).mult(1d/3d);
            Vector2 k5 = function(solution.sum(k1.mult(1.5)).minus(k3.mult(4.5)).sum(k4.mult(6)), time + step).mult(step).mult(1d / 3d);

            newSolution = solution.sum(k1.sum(k4.mult(4)).sum(k5).mult(0.5));

            Vector2 exc = k1.minus(k3.mult(4.5)).sum(k4.mult(4)).minus(k5.mult(0.5));
            if (exc.x > 5*error || exc.y > 5*error) {
                step /= 2d;
                iter++;
                continue;
            }

            double extendError = (5d/32d)*error;
            if (Math.abs(exc.x) < extendError || Math.abs(exc.y) < extendError) {
                step *= 2d;
            }

            solution = newSolution;
            time += step;
        }
        System.out.println(iter);
        System.out.println(solution);
    }

    public static Vector2 function(Vector2 solution, double time) {
        Vector2 result = new Vector2();
        result.x = FIRST_FUNCTION.calculate(solution.toArray());
        result.y = SECOND_FUNCTION.calculate(solution.toArray());
        return result.mult(-time * 2d);
    }
}
