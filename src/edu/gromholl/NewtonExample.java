package edu.gromholl;


import edu.gromholl.math.*;

import java.util.Arrays;

import static edu.gromholl.DataExample.firstFunction;
import static edu.gromholl.DataExample.secondFunction;
import static edu.gromholl.math.Polynomial.Variable;

public class NewtonExample {
    public static void main(String[] args) {
        Variable x1 = new Variable("x");
        Variable x2 = new Variable("y");

        Polynomial[] polynomials = {firstFunction(x1, x2), secondFunction(x1, x2)};

        LinearSystemSolver linearSystemSolver = new GaussLinearSystemSolver();
        PolynomialSolver polynomialSolver = new NewtonPolynomialSolver(linearSystemSolver, 0.00001);
        double[] result = polynomialSolver.solve(polynomials, new Variable[] {x1, x2});
        System.out.println(Arrays.toString(result));
    }
}
