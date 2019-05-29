package org.nanocode.evolutionApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    protected   Evolution functionEvolution = new Evolution(0);
    XYChart.Series<Double, Double> actualGraph = new XYChart.Series<>();
    XYChart.Series<Double, Double> popItems = new XYChart.Series<>();

    FunctionGenetic functionGenetic;

    public static final int populationSize=20;

    @FXML
    private LineChart<Double, Double> lineChart;

    @FXML
    public ComboBox<FuncID> comboBox;
    ObservableList<FuncID> funcNames = FXCollections.observableArrayList();

    @FXML
    public Canvas canvasGraph;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        funcNames.add(new FuncID(0, "f.dwukwadratowa + szum"));
        funcNames.add(new FuncID(1, "sin. modulowana"));
        funcNames.add(new FuncID(2, "f.piłokształtna"));
        funcNames.add(new FuncID(3, "pik1"));
        funcNames.add(new FuncID(4, "pik2"));

        comboBox.setItems(funcNames);
        comboBox.setPromptText(funcNames.get(0).toString());
        comboBox.setValue(funcNames.get(0));

        functionGenetic = new FunctionGenetic(populationSize,0);
        initFunctionGraph(0);

        lineChart.getData().addAll(actualGraph,popItems);
    }

    public void changedFunctionName(){
        functionGenetic = new FunctionGenetic(populationSize,comboBox.getValue().getID());
        initFunctionGraph(comboBox.getValue().getID());
        popItems.getData().clear();
        initFunctionGraph(comboBox.getValue().getID());
    }

    public void initFunctionGraph(int i  ){
       actualGraph.getData().clear();

        for (Double j = -functionGenetic.judge.X_RANGE; j <= functionGenetic.judge.X_RANGE; j += 0.02) {
            actualGraph.getData().add(new XYChart.Data<>(  j, FunctionJudge.resultValue(j,i))  );
        }
    }

    public void Reset(){
        changedFunctionName();
    }

    public void step(){

        popItems.getData().clear();
        functionGenetic.evolve();
        popItems.setData(functionGenetic.getPopItems().getData());
       }

    public void step10(){
        for(int i=0;i<10;i++)
           step();
    }

    public void result(){
        double maxx, maxy,j;
        maxy=functionEvolution.funkcja(-functionEvolution.mx);
        maxx=-functionEvolution.mx;
        for(double i=-functionEvolution.mx-1;i<=functionEvolution.mx+1;i+=0.001)
        {
            j=functionEvolution.funkcja(i);
            if(j>maxy) { maxy=j; maxx=i; }
        }
        Population maxRes = functionEvolution.maks();
        AlertBox.display("Wynik", "Ekstremum funkcji: x="+maxx+" y="+
                "\nZnalezione: x="+maxRes.x+" y="+maxRes.y+
                "\nRóżnica: dx="+(maxx-maxRes.x)+" dy="+(maxy-maxRes.y));
    }

}
