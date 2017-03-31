/**
 * Created by Samantha on 02/03/2017.
 */

import java.util.ArrayList;

public class Histogram {

    static StateTransition s = new StateTransition();

    private static String makeStarRow(Double time) {
        while (time > 0) {
            System.out.print("\033[33m*\033[0m");
            time--;
        }
        if (time < 1) {
            System.out.println();
        }
        return null;
    }

    public static String getHistogram() {
        ArrayList<Double> histogram = StateTransition.getCosts();
        for (int i = 0; i < s.costs.size(); i++) {
            Double time = histogram.get(i);
            makeStarRow(time);
        }
        return "";
    }
}