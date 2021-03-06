package eu.nanocode.evolutionApp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private XYChart.Series<Double, Double> actualGraph = new XYChart.Series<>();
    private XYChart.Series<Double, Double> popItems = new XYChart.Series<>();
    private Timeline timer;
    private boolean animOn=false;

    private FunctionGenetic functionGenetic;

    public static final int populationSize=20;

    @FXML
    private LineChart<Double, Double> lineChart;

    @FXML
    private Button animButton;

    @FXML
    public ComboBox<FuncID> comboBox;
    private ObservableList<FuncID> funcNames = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        funcNames.add(new FuncID(0, "two-square function with noise"));
        funcNames.add(new FuncID(1, "modulated sinus"));
        funcNames.add(new FuncID(2, "sawtooth function"));
        funcNames.add(new FuncID(3, "peak 1"));
        funcNames.add(new FuncID(4, "peak 2"));

        comboBox.setItems(funcNames);
        comboBox.setPromptText(funcNames.get(0).toString());
        comboBox.setValue(funcNames.get(0));

        functionGenetic = new FunctionGenetic(populationSize,0);
        initFunctionGraph(0);

        lineChart.getData().addAll(actualGraph,popItems);
        popItems.setData(functionGenetic.getPopItems().getData());
    }

    public void changedFunctionName(){
        functionGenetic = new FunctionGenetic(populationSize,comboBox.getValue().getID());
        popItems.getData().clear();
        popItems.setData(functionGenetic.getPopItems().getData());
        initFunctionGraph(comboBox.getValue().getID());
    }

    private void initFunctionGraph(int i  ){
       actualGraph.getData().clear();

        for (double j = -functionGenetic.judge.X_RANGE; j <= functionGenetic.judge.X_RANGE; j += 0.02) {
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
        double maxx, maxy,j, tmp;
        double popXmax, popYmax;
        int i=comboBox.getValue().getID();
        maxx=-functionGenetic.judge.X_RANGE;
        maxy=FunctionJudge.resultValue(maxx, i);
        for (j = -functionGenetic.judge.X_RANGE; j <= functionGenetic.judge.X_RANGE; j += 0.02) {
            tmp=FunctionJudge.resultValue(j, i);
            if(tmp>maxy){
                maxx=j;
                maxy=tmp;
            }
        }
        popYmax=Double.POSITIVE_INFINITY;
        popXmax=Double.NEGATIVE_INFINITY;
        for (XYChart.Data<Double, Double> x : functionGenetic.getPopItems().getData()){
           if( Math.abs(x.getYValue()-maxy) < Math.abs(popYmax-maxy) ){
               popYmax = x.getYValue();
               popXmax = x.getXValue();
           }
        }

                AlertBox.display("Result", String.format(
                        "Extreme of function:%nx=%.2f%ny=%.2f" +
                         "%n%nFound:%nx=%.2f%ny=%.2f" +
                        "%n%nDifference:%ndx=%.2f%ndy=%.2f",
                        maxx,maxy,popXmax,popYmax,(maxx-popXmax), (maxy-popYmax) ) );
    }

    public void animate(){
        if(animOn) {
            animOn=false;
            timer.stop();
            animButton.getStyleClass().removeAll("button-on");
        }

        else {
            animOn=true;
            animButton.getStyleClass().add("button-on");
            timer = new Timeline(new KeyFrame( Duration.millis(1000), e->step())  );
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

        }
    }

    public void values(){
        StringBuilder str = new StringBuilder();
        for (XYChart.Data<Double, Double> x : functionGenetic.getPopItems().getData()){
            str.append(String.format("x=%.2f\tx=%.2f\t%n",x.getXValue(),x.getYValue()));
        }
        AlertBox.display("Individuals", str.toString());
    }

}
