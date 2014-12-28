package edu.gromholl;

import edu.gromholl.math.Matrix2;
import edu.gromholl.math.Polynomial;
import edu.gromholl.math.Vector2;

import static edu.gromholl.DataExample.*;
import static java.lang.Math.abs;

/**
 * @author GromHoll
 */
public class ImplicitMethodExample {

    public static Polynomial.Variable X1 = new Polynomial.Variable("x");
    public static Polynomial.Variable X2 = new Polynomial.Variable("y");
    public static Polynomial FIRST_FUNCTION = firstFunction(X1, X2);
    public static Polynomial SECOND_FUNCTION = secondFunction(X1, X2);


    public static Vector2 RESULT_F = new Vector2(F1, F2);

    public static double ERROR = 0.01;
    public static double TAO  = 0.7;

    public static void main(String[] args) {

        int iterations = 0;

        Vector2 diff;
        Vector2 solution = new Vector2(0,0);
        Vector2 previousV;
        Vector2 nextV = new Vector2(0,0);

        int methodIteration = 1;
        do {
            do {
                previousV = nextV;
                iterations++;

                Vector2 bMinusModifiedF = operatorB(previousV, methodIteration).minus(modifiedFunction(solution, RESULT_F, methodIteration));
                Matrix2 inverseModifiedB = modifiedOperatorB(previousV, methodIteration).inverse();
                Vector2 column = inverseModifiedB.multOnColumn(bMinusModifiedF);
                nextV = previousV.minus(column);

                diff = nextV.minus(previousV);
            } while(abs(diff.x) > ERROR || abs(diff.y) > ERROR);
            solution = nextV;
            methodIteration++;

            diff = function(solution).minus(RESULT_F);
        } while (abs(diff.x) > ERROR || abs(diff.y) > ERROR);

        System.out.println("Iteration count: " + iterations);
        System.out.println("Final Solution: " + solution);
    }

    public static double tao(int iteration) {
        return 1d/Math.pow(iteration, TAO/2);
    }

    public static Vector2 operatorB(Vector2 point, int iteration) {
        return function(point).mult(tao(iteration)).sum(point);
    }

    public static Vector2 function(Vector2 point) {
        Vector2 result = new Vector2();
        result.x = FIRST_FUNCTION.calculate(point.toArray()) + F1;
        result.y = SECOND_FUNCTION.calculate(point.toArray()) + F2;
        return result;
    }

    public static Vector2 modifiedFunction(Vector2 previousU, Vector2 functionResult, int iteration) {
        return functionResult.mult(tao(iteration)).sum(previousU);

    }

    public static Matrix2 modifiedOperatorB(Vector2 point, int iteration) {
        // B' = (E  + Tn * A')
        Matrix2 result = new Matrix2();
        Matrix2 temp = Matrix2.CreateE();
        temp.setValueAt(0, 0, point.x);
        temp.setValueAt(1, 1, point.y);

        //A'Un
        result.setValueAt(0, 0, FIRST_FUNCTION.derivative(X1).calculate(point.toArray()));
        result.setValueAt(0, 1, FIRST_FUNCTION.derivative(X2).calculate(point.toArray()));
        result.setValueAt(1, 0, SECOND_FUNCTION.derivative(X1).calculate(point.toArray()));
        result.setValueAt(1, 1, SECOND_FUNCTION.derivative(X2).calculate(point.toArray()));

        //Tn * A'Un
        result.mult(tao(iteration));

        // A'Tn + E*Un
        return result.sum(temp);
    }

}
