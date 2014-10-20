package edu.gromholl.math;


import java.util.Arrays;

public interface PolynomialSolver {
    public double[] solve(Polynomial[] polynomials, Polynomial.Variable[] variables);

    public default double norm(double values[]) {
        assert values != null;
        return Math.sqrt(Arrays.stream(values).map(x -> x * x).sum());
    }
}
