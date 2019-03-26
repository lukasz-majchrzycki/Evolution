package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static java.lang.Math.*;

public class Evolution {
    public int index, step=0;
    public final int ilepop=20;
    public final int ileT=2870;
    public final int mx=10,my=100;

    Random generator = new Random();

    ArrayList<Population> pop = new ArrayList<>(ilepop);
    ArrayList<Double> T = new ArrayList<>(ileT);

    public Evolution(int index) {
        this.index = index;
    }

    protected double funkcja(double x){
        double y;

        switch(index)
        {
            case 0:
                y=-0.007*x*x*x*x+x*x+x+(generator.nextInt()%100)/30;
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

    protected void first()
    {
        double x;
        int i,j,k;
        for(i=0;i<ilepop;i++)
        {
            x=((double)(abs(generator.nextInt())%(200*mx))/100)-(double)mx;
            pop.add(new Population(x,  this.funkcja(x))  );
        }

        for(i=0;i<ilepop;i++)
            for(k=0;k<=((i+1)*(i+1)-1);k++)
            {
                T.add(pop.get(i).x);
            }
    }

    protected void krok()
    {
        int i,j,k;
        double x1,x2,proc, temp;
        if(step!=0)
        {
            Collections.sort(pop,new Population.valueComparator());

            for(i=0,j=0;i<ilepop;i++)
                for(k=0;k<=((i+1)*(i+1)-1);k++)
                {
                    T.set(j, pop.get(i).x);
                    j++;
                }



            for(i=0;i<ilepop;i++)
            {
                x1=T.get(abs(generator.nextInt()%ileT));
                x2=T.get(abs(generator.nextInt()%ileT));
                proc=(double)(abs(generator.nextInt())%1000)/1000;

               temp= x1+(x1-x2)*proc;
                pop.set(i, new Population(temp,funkcja(temp)));
            }

            k=abs(generator.nextInt())%(ilepop*6);
            if(k<ilepop) pop.get(i).x+=(abs(generator.nextInt())%(14*mx))-7*mx;

        }
        else { first(); }

        step++;
    }

    protected Population maks()
    {
        StringBuilder str = new StringBuilder();
        int i;
        int m=0;

        for(i=1;i<ilepop;i++)
            if(pop.get(i).y>pop.get(m).y)    m=i;

        //sprintf(str,"Ekstremum :\n\tx=%f\ty=%f\n\nZnalezione :\n\tx=%f\ty=%f\n\nRó?nica :\n\tdx=%f\tdy=%f\n\nKrok nr. %d",maxx,maxy,pop[m].x,pop[m].y,maxx-pop[m].x,maxy-pop[m].y,step);

        return new Population(pop.get(m).x, pop.get(m).y);
    }

    protected String osobniki()
    {
        StringBuilder str = new StringBuilder();

        for (Population p :
                this.pop) {
            str.append("x="+p.x+"y="+p.y);
        }
        return str.toString();
    }
}
