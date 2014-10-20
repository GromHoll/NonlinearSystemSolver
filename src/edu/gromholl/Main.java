package edu.gromholl;


import edu.gromholl.math.*;

import static edu.gromholl.math.Polynomial.Variable;

public class Main {

    private static final double ALPHA = 3;
    private static final double BETA  = 7;

    private static final double A1 =  3;
    private static final double B1 =  8;
    private static final double C1 = -4;
    private static final double D1 = -2;

    private static final double A2 =  1;
    private static final double B2 =  4;
    private static final double C2 =  4;
    private static final double D2 = -4;

    private static final double F1 =  9;
    private static final double F2 = 13;

    public static void main(String[] args) {

        Variable x1 = new Variable("x");
        Variable x2 = new Variable("y");

        Polynomial[] polynomials = {firstFunction(x1, x2), secondFunction(x1, x2)};

        LinearSystemSolver linearSystemSolver = new GaussLinearSystemSolver();
        PolynomialSolver polynomialSolver = new NewtonPolynomialSolver(linearSystemSolver, 0.00001);
        polynomialSolver.solve(polynomials, new Variable[] {x1, x2});
    }

    private static Polynomial firstFunction(Variable x1, Variable x2) {
        Polynomial polynomial = new Polynomial(x1, x2);
        return polynomial.addPart(A1, x1, ALPHA).addPart(B1, x1).addPart(C1, x2).addPart(D1).addPart(-F1);
    }

    private static Polynomial secondFunction(Variable x1, Variable x2) {
        Polynomial polynomial = new Polynomial(x1, x2);
        return polynomial.addPart(A2, x2, BETA).addPart(B2, x1).addPart(C2, x2).addPart(D2).addPart(-F2);
    }

}
