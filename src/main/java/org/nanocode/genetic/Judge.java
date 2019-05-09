package org.nanocode.genetic;

import jdk.jshell.spi.ExecutionControl;

public abstract class Judge<E extends Individual> {

    public int compare(E o1, E o2) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Method compare is not implemented");
    }
}
