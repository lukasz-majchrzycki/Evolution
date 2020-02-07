package eu.nanocode.evolutionApp;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Random;

public class FunctionGenetic {

    private Random generator = new Random();

    public final int mutationLevel=10;       //from 0 to 256
    public final int mutationRange=256;
    private ArrayList<Integer> probability;

    private ArrayList<FunctionProbe> pop = new ArrayList<>();
    protected FunctionJudge judge;

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

    protected void evolve(){
        int parent1, parent2;
        FunctionProbe child;
        ArrayList<FunctionProbe> childPop = new ArrayList<>();

        pop.sort(judge);

        do {
            parent1 = rndIndividual();

            do {
                parent2 = rndIndividual();
            } while (parent1 == parent2);

            child = new FunctionProbe(pop.get(parent1), pop.get(parent2));
            child = mutation(child);
            childPop.add(child);
        }while(childPop.size()<pop.size());

        pop=childPop;
    }

    public FunctionProbe mutation(FunctionProbe individual){
        int rnd=Math.abs(generator.nextInt()%mutationRange);
        if(rnd < mutationLevel){
            individual = new FunctionProbe(individual, new FunctionProbe());
        }
        return individual;
    }
}
