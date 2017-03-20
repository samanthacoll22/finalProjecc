/**
 * Created by Samantha on 29/02/2017.
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Simulator {

    public static String place = "new york";    // To change the destination
    public static String type = "default";      // "app" for taking users accurate result from java app, "default" for first recorded result in ArrayList
    public static String descriptions = "off";   // Whether     or not the user see's the step-by-step of each state. "on" or "off" respectively.
    public static String sort = "P";            // 'A' for alphabetical or 'P' population. Snorting of suggestions.
    public static String histogram = "off";     // Whether or not the user wants a "*" histogram at the bottom of the console. Can be unsightly with a large amount of simulations.

    public static int simulations = 100;        // How many times the simulation is run.
    public static int noOfSuggestions = 5;      // The number of suggestions presented to the user to choose.
    public static int noOfUserWords = 1;        // In java app, the amount of times the user types in a word. The higher, the more accurate and realistic.
    public static int showSuggestionsAfter = 1;

    public static double typingCost = 2;          // If not using java app: 2s for slow typers, 0.2 for fast typers. Can evaluate different results.
    public static double pickingCost = 5;         // Assumption made.

    public static void main(String args[]) throws IOException {
        StateTransition.main(args);
    }
}
