package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    protected Evolution functionEvolution = new Evolution(0);
    GraphicsContext gc;
    ArrayList<Population> funcGraph = new ArrayList<>();

    final static int xmin = 50, w = 400, ymin = 50, h=400;

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

        gc = canvasGraph.getGraphicsContext2D();

        //gc.setFill(Color.BLACK);
        //gc.fillRect(xmin  , ymin, w   , h);
        functionEvolution.first();
        gc.setStroke(Color.BLACK);
        initFunctionGraph();
        paint();
    }

    public void changedFunctionName(){
        functionEvolution = new Evolution(comboBox.getValue().getID());
    }

    public void initFunctionGraph(){
        for(double i=-functionEvolution.mx;i<functionEvolution.mx;i+=0.1){
            funcGraph.add(new Population(i,functionEvolution.funkcja(i)));
        }
    }

    public double transformX(double x){
        return ((x/2*functionEvolution.mx)+0.5)*(w-xmin)+xmin;
    }

    public double transformY(double y){
        return ((y/2*functionEvolution.my)+0.5)*(h-ymin)+ymin;
    }

    public void paint(){
        gc.moveTo(transformX(funcGraph.get(0).x),transformY(funcGraph.get(0).y));
        for (int i = 1; i <funcGraph.size() ; i++) {
            gc.lineTo(transformX(funcGraph.get(i).x),transformY(funcGraph.get(i).y));
        }
    }

    public void step(){
        functionEvolution.krok();
    }

    public void step10(){
        for(int i=0;1<10;i++)
           functionEvolution.krok();
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
