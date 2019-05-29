package org.nanocode.evolutionApp;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FunctionGenetic {

    private Random generator = new Random();

    public final int mutationLevel=5;       //from 0 to 256
    private ArrayList<Integer> probability;

    private ArrayList<FunctionProbe> pop = new ArrayList<>();
    public FunctionJudge judge;

    public FunctionGenetic(int size, int index){
        judge = new FunctionJudge(index);
        probability = new ArrayList<>();
        int j;

        for (int i = 0; i < size; i++) {
            pop.add(new FunctionProbe());
            j=(i+1) * (i+1);
            probability.add(j);
        }
    }

    public XYChart.Series<Double, Double> getPopItems(){
        XYChart.Series<Double, Double> result = new XYChart.Series<>();

        for (FunctionProbe x :
                pop) {
            result.getData().add(new XYChart.Data<>(judge.valueOfChromosome(x) , judge.resultValue(x)  ) );
        }
        return result;
    }
    private int rndIndividual(){
        int rnd;
        rnd = generator.nextInt(probability.get(probability.size()-1));

        for (int i =probability.size()-1;i>0; i--) {
            if( probability.get(i-1)<=rnd ) {
                return i;
            }
        }
        return 0;
    }

    public void evolve(){
        int parent1, parent2;
        FunctionProbe child;
        ArrayList<FunctionProbe> childPop = new ArrayList<>();

        Collections.sort(pop, judge);

        do {
            parent1 = rndIndividual();

            do {
                parent2 = rndIndividual();
            } while (parent1 == parent2);

            child = new FunctionProbe(pop.get(parent1), pop.get(parent2));
            childPop.add(child);
        }while(childPop.size()<pop.size());

        pop=childPop;

        double min=judge.valueOfChromosome(childPop.get(0)), max=min;
        double sum=0, best=judge.resultValue(childPop.get(0));
        double x,y;

        for (int i = 0; i < childPop.size(); i++) {
            x=judge.valueOfChromosome(childPop.get(i));
            y=judge.resultValue(childPop.get(i));

            if(min>x) min=x;
            else if (max<x) max=x;

            if(best<y) best=y;
        }
    }

    public void mutation(){

    }

    public String result(){
        return new String("");
    }


}
