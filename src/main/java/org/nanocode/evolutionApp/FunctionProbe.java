package org.nanocode.evolutionApp;
import org.nanocode.genetic.Individual;

import java.util.Random;

public class FunctionProbe implements Individual {

    public static final int WORD_SIZE = 32;
    private int chromosome[];

    public enum MaskMethod{
        MASK_PARTITION,
        MASK_SIEVE;
    }

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

    protected int[] maskPartition(){
        Random generator = new Random();
        double normal;
        int div;
        long n=0;
        int[] mask = new int[2];

        div = Math.abs(generator.nextInt());
        normal = -0.2 * Math.log(((double) div) / Integer.MAX_VALUE);
        normal = (generator.nextBoolean()) ? normal : -normal;

        normal = (normal+1)*32;
        div=(int) normal;
        if (div < 1) div = 1;
        if (div > 62) div = 62;

        for (int i = 0; i < div; i++) {
            n+= (1L<<i);
        }

        mask[0]=(int)n;
        mask[1]=(int) (n>>32);

        return mask;
    }

    protected int[] maskSieve(){
        Random generator = new Random();
        int[] mask = new int[2];
        long n=0;

        for (int i = 0; i < 64; i++) {
            if(generator.nextBoolean()){
                n+= (1L<<i);
            }
        }

        mask[0]=(int)n;
        mask[1]=(int) (n>>32);

        return mask;
    }

    public FunctionProbe(Individual parent1, Individual parent2){
        this(parent1, parent2, MaskMethod.MASK_SIEVE);
    }

    public FunctionProbe(Individual parent1, Individual parent2, MaskMethod maskMethod){
        int[] mask;
        int[]p1;
        int[]p2;
        int[] result = new int[2];

        switch (maskMethod){
            case MASK_PARTITION:
                mask = maskPartition();
                break;
            case MASK_SIEVE:
            default:
                mask = maskSieve();
        }

        p1=parent1.getChromosome();
        p2=parent2.getChromosome();

        result[0]= (p1[0]&mask[0]) | (p2[0]& (~mask[0]));
        result[1]= (p1[1]&mask[1]) | (p2[1]& (~mask[1]));
        setChromosome(result);
    }

}
