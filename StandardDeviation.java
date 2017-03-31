import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Samantha on 19/03/2017.
 */
public class StandardDeviation {

    static DecimalFormat df = new DecimalFormat("#.00");

    private static double sum (ArrayList<Double> costs){
        if (costs.size() > 0) {
            double sum = 0;
            for (Double i : costs) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public static double getMean(ArrayList<Double> costs) {
        Double sum = sum(costs);
        double mean = 0;
        mean = sum / (costs.size() * 1.0);
        return Double.valueOf(df.format(mean));
    }

    public static double getStdDeviation(ArrayList<Double> costs){
        double mean = getMean(costs);
        double temp = 0;
        for (int i = 0; i < costs.size(); i++) {
            double val = costs.get(i);
            double squrDiffToMean = Math.pow(val - mean, 2);
            temp += squrDiffToMean;
        }
        double meanOfDiffs = (double) temp / (double) (costs.size());
        double sd = Math.sqrt(meanOfDiffs);
        return Double.valueOf(df.format(sd));
    }
}
