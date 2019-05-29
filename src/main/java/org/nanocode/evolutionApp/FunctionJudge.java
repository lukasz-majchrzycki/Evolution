package org.nanocode.evolutionApp;

import org.nanocode.genetic.Individual;
import org.nanocode.genetic.Judge;

import java.util.Comparator;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.sin;

public class FunctionJudge extends Judge<Individual> implements Comparator<Individual> {
    public final double X_RANGE = 10.0, Y_RANGE = 100.0;
    private int index;

    public FunctionJudge(int index){
        this.index=index;
    }

    @Override
    public int compare(Individual o1, Individual o2){
        double y1=resultValue(o1);
        double y2=resultValue(o2);
      return (y1>y2) ? 1: ( (y1<y2)? -1:0 );
    }

    @Override
    public double resultValue(Individual o){

        double x=valueOfChromosome(o);
        return resultValue(x);
    }

    static public double resultValue(double x, int index){

        return functValue(index, x);
    }

    public double resultValue(double x){
        return functValue(this.index, x);
    }

    static private double functValue(int i,double x){
        double y;
        Random generator = new Random();
        switch(i)
        {
            case 0:
                y=-0.007*x*x*x*x+x*x+x+((double) generator.nextInt()%100)/100;
                break;
            case 1:
                y=60*exp(-0.05*x*x)*sin(5*x)-0.01*x*x*x*x-2*x*x+3*x-15;
                break;
            case 2:
                y=(x+5)<5?-0.5*x*x+5*(x+5)+2*sin(3*x):100*exp(-(x+5)+5)-75;
                break;
            case 3:
                y=(x>4&&x<6)?-50*(x-5)*(x-5)+50:-75-abs(x-5);
                break;
            case 4:
                y=125*sin(x)*sin(x)/(x*x)-75;
                break;
            default:
                y=0;
        }
        return y;
    }

    protected double valueOfChromosome(Individual o){
        int[] chromosome = o.getChromosome();
        long temp = (  (long)chromosome[0]) *(1L<<(FunctionProbe.WORD_SIZE-1))  + ((long)chromosome[1]);
        double y = ((double)temp)/(0.5*Long.MAX_VALUE);

        return y*this.X_RANGE;
    }
}
