package org.nanocode.evolutionApp;
import org.nanocode.genetic.Individual;

import java.util.Random;

public class FunctionProbe implements Individual {

    public static final int WORD_SIZE = 32;
    private int chromosome[]=null;

    @Override
    public int[] getChromosome() {
        return this.chromosome;
    }

    @Override
    public void setChromosome(int[] chromosome) {
        this.chromosome=chromosome;
    }

    public FunctionProbe() {
        chromosome = new int[2];
        Random generator = new Random();
        chromosome[0] = generator.nextInt();
        chromosome[1] = generator.nextInt();
    }

}
