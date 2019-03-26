package sample;

import java.util.Comparator;

public class Population {
    protected double x, y;

    public Population(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static class valueComparator implements Comparator<Population>{

        @Override
        public int compare(Population p1, Population p2) {
            return p1.y < p2.y ? -1 : p1.y == p2.y ? 0 : 1;
        }
    }

}
