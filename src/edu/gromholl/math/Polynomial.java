package edu.gromholl.math;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Polynomial {

    public static class Variable {

        private final String name;

        public Variable(String name) {
            this.name = name != null ? name : "unknown";
        }

        @Override
        public String toString() {
            return name;
        }
    }


    private static class Part {
        private final double coefficient;
        private final Variable variable;
        private final double pow;

        public Part(double coefficient, Variable variable, double pow) {
            this.coefficient = coefficient;
            this.variable = variable;
            this.pow = pow;
        }

        public double calculate(double value) {
            return variable == null ? coefficient : coefficient*Math.pow(value, pow);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (coefficient != 1) {
                if (coefficient > 0) {
                    sb.append(coefficient);
                } else {
                    sb.append('(').append(coefficient).append(')');
                }
                if (variable != null && pow != 0) {
                    sb.append('*');
                }
            }
            if (variable != null && pow != 0) {
                sb.append(variable);
                if (pow != 1) {
                    sb.append('^').append(pow);
                }
            } else if (coefficient == 1) {
                sb.append(1);
            }
            return sb.toString();
        }
    }

    private List<Variable> variables;
    private List<Part> parts;

    public Polynomial(Variable ... variables) {
        this(Arrays.asList(variables));
    }

    public Polynomial(List<Variable> variables) {
        this.variables = new ArrayList<>(variables);
        this.parts = new ArrayList<>();
    }

    public Polynomial addPart(double coefficient, Variable variable, double pow) {
        assert variables.contains(variable);

        if (coefficient != 0) {
            Part part = new Part(coefficient, variable, pow);
            parts.add(part);
        }
        return this;
    }

    public Polynomial addPart(double coefficient, Variable variable) {
        return addPart(coefficient, variable, 1);
    }

    public Polynomial addPart(double coefficient) {
        return addPart(coefficient, null);
    }

    public double calculate(double ... values) {
        assert values.length == variables.size();
        return parts.stream().mapToDouble(part -> calculatePart(part, values)).sum();
    }

    private double calculatePart(Part part, double ... values) {
        return part.calculate(getValueByVariable(values, part.variable));
    }

    private double getValueByVariable(double[] values, Variable variable) {
        int index = variables.indexOf(variable);
        return index >= 0 ? values[index] : 0;
    }

    public Polynomial derivative(Variable variable) {
        assert variables.contains(variable);

        Polynomial derivative = new Polynomial(variables);
        parts.forEach(part -> {
            if (part.variable == variable && part.pow != 0) {
                derivative.addPart(part.coefficient*part.pow, part.variable, part.pow - 1);
            }
        });
        return derivative;
    }

    @Override
    public String toString() {
        return parts.stream().map(Part::toString).collect(Collectors.joining(" + "));
    }
}
