package org.nanocode.evolutionApp;

import org.nanocode.genetic.Individual;
import org.nanocode.genetic.Judge;

public class FunctionJudge extends Judge<Individual> {
    public static final int X_RANGE = 10, Y_RANGE = 100;

    @Override
    public int compare(Individual o1, Individual o2){
        //double y1=
      return 0;
    }

    protected double value(Individual o){
        int[] chromosome = o.getChromosome();
        long temp = (  (long)chromosome[0]) *(1L<<(FunctionProbe.WORD_SIZE-1))  + ((long)chromosome[1]);
        double y = ((double)temp)/(0.5*Long.MAX_VALUE);

        return y*FunctionJudge.X_RANGE;
    }
}
