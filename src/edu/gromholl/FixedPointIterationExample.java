package edu.gromholl;

import edu.gromholl.math.Polynomial;
import edu.gromholl.math.Vector2;

import static edu.gromholl.DataExample.*;
import static java.lang.Math.abs;

/**
 * @author GromHoll
 */
public class FixedPointIterationExample {

    public static Polynomial.Variable X1 = new Polynomial.Variable("x");
    public static Polynomial.Variable X2 = new Polynomial.Variable("y");
    public static Polynomial FIRST_FUNCTION = firstFunction(X1, X2);
    public static Polynomial SECOND_FUNCTION = secondFunction(X1, X2);

    public static double error = 0.01;

    public static void main(String[] args) {

        double multilayer = getMultilayer();

        int iterations = 0;

        Vector2 diff;
        Vector2 previousSolution;
        Vector2 solution = new Vector2(0,0);

        do {
            iterations++;
            previousSolution = solution;
            solution = previousSolution.minus(function(solution).mult(multilayer));
            diff = function(solution).minus(function(previousSolution));
        } while (abs(diff.x) > error || abs(diff.y) > error);



        System.out.println("Iteration count: " + iterations);
        System.out.println("Solution: " + solution);
    }

    public static Vector2 function(Vector2 solution) {
        Vector2 result = new Vector2();
        result.x = FIRST_FUNCTION.calculate(solution.toArray());
        result.y = SECOND_FUNCTION.calculate(solution.toArray());
        return result;
    }

    public static double getMultilayer() {
        double Ro = 2;
        Vector2 firstRow = new Vector2();
        firstRow.x = FIRST_FUNCTION.derivative(X1).calculate(Ro, Ro);
        firstRow.y = FIRST_FUNCTION.derivative(X2).calculate(Ro, Ro);

        Vector2 secondRow = new Vector2();
        secondRow.x = SECOND_FUNCTION.derivative(X1).calculate(Ro, Ro);
        secondRow.y = SECOND_FUNCTION.derivative(X2).calculate(Ro, Ro);

        return 1/Math.sqrt(2*Math.max(firstRow.norm(), secondRow.norm()));
    }

}
