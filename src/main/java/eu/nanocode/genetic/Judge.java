package eu.nanocode.genetic;

import jdk.jshell.spi.ExecutionControl;

import java.util.Comparator;

public abstract class Judge<E extends Individual> implements Comparator<E> {

    @Override
    public abstract int compare(E o1, E o2)  ;

    public double resultValue(Individual o) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("ResultValue method is not implemented");
    }

}
