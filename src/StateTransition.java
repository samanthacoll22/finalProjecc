/**
 * Created by Samantha on 25/02/2017.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;


public class StateTransition implements Simulator{

    public static String place_sub_str = "";
    private static String nextState;
    private static String currentState;
    private static String a_letter;

    public static double totalCost;
    private static double mean;
    private static double sd;

    private static int position;
    public static int attempt = 1;

    private static double typeCost, lookCostWithPicking, lookCostWithoutPicking, pickCost, searchCost = 0;

    private static ArrayList<Integer> attempts = new ArrayList<Integer>();
    public static ArrayList<Double> costs = new ArrayList<Double>();
    public static ArrayList<String> places = new ArrayList<String>();
    public static ArrayList<String> shownSuggestions = new ArrayList<String>();

    private static ArrayList<Double> pType = new ArrayList<Double>
            (Arrays.asList(1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.8, 0.85, 0.9, 1.0));
    private static ArrayList<Double> pPick = new ArrayList<Double>
            (Arrays.asList(0.1, 0.1, 0.2, 0.4, 0.6, 0.6, 0.7, 0.5, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.7, 0.8, 0.9, 0.9));
    private static ArrayList<Integer> cPick = new ArrayList<Integer>
            (Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));


    public static void main(String args[]) throws IOException {

            Scanner s = new Scanner(new File("population.txt"));  // A text file sorted alphabetically
            String pl;

            while (s.hasNext()) {
                pl = s.nextLine().toLowerCase();
                places.add(pl);
            }
            s.close();

        if (!places.contains(place)) {
            System.out.println("The place you are trying to simulate is not known to the program, please check spelling!");
        } else {
            System.out.println("Attempt			Place			Total Cost");
            for (int i = 0; i < simulations; i++) {
                run();
            }
            mean = StandardDeviation.getMean(costs);
            sd = StandardDeviation.getStdDeviation(costs);

            System.out.println();
            System.out.println("The mean cost for the search is: " + mean);
            System.out.println("The standard deviation for the search is: " + sd);
            System.out.println();

            if (histogram.equals("on")) {
                System.out.println(Histogram.getHistogram());
            }
            createOutputFile();
            GraphPanel g = new GraphPanel(costs);
            g.main(args);
        }
    }

    public static void run() throws FileNotFoundException{
        while(!place.equals(place_sub_str) ) {
            nextState = getNextState();
            if(nextState == "type"){
                typeState();
            } else if (nextState == "look") {
                lookState();
            } else if (nextState == "pick") {
                pickState();
            } else {
                searchState();
            }
        }
        totalCost = typeCost + lookCostWithPicking + lookCostWithoutPicking + pickCost;

        System.out.println();
        System.out.println(attempt +"	    		" + place.toUpperCase() + "			" + totalCost);
        System.out.println();

        costs.add(totalCost);
        place_sub_str = "";

        attempts.add(attempt);
        attempt = attempt + 1;

        typeCost = 0;
        lookCostWithPicking = 0;
        lookCostWithoutPicking = 0;
        pickCost = 0;
        searchCost = 0;
    }

    private static String getNextState() {
        int i = place_sub_str.length();
        Random random = new Random();
        if(i<=2){
            nextState = "type";
        } else {
            if(currentState == "type"){
                 Double probType = pType.get(random.nextInt(pType.size()));
//               Double probType = pType.get(i);
                if (probType > 0.6) {
                    nextState = "type";
                } else {
                    nextState = "look";
                }
            } else if (currentState == "look") {
                Double probPick = pPick.get(random.nextInt(pType.size()));
//                Double probPick = pPick.get(i);
                if(shownSuggestions.contains(place)) {
                    nextState = "pick";
//                    lookCostWithPicking = (lookCostWithPicking + (shownSuggestions.indexOf(place) + 1)); //1s for how far down the list they have to get
                    lookCostWithPicking = (lookCostWithPicking + (((shownSuggestions.indexOf(place) + 1)) + (pPick.get(i) * cPick.get(i)))); //1s for how far down the list they have to get
                }else{
                    nextState = "type";
                    lookCostWithoutPicking = (lookCostWithoutPicking + ((1-pType.get(i)) * 5));
                }
            } else {
                nextState = "search";
            }
        }
        return nextState;
    }

    private static String typeState() {
        currentState = "type";
        shownSuggestions.clear();
        position = (place_sub_str.length());
        a_letter = Character.toString(place.charAt(position));
            if (descriptions.equals("on")){
                System.out.println("\033[34m      User has typed " + "'"+a_letter.toUpperCase()+"'\033[0m");
            }
        place_sub_str = place_sub_str + a_letter;
        if(position > showSuggestionsAfter) {
            Suggestions.getSuggestions();
        }
        if(type.equals("app")){
            typeCost = typeCost + getTypeCost();        // Plugged in straight from app
        } else if (type.equals("default")) {
            typeCost = typeCost + (typingCost * pType.get(position));                    // In case simulations is ran without the user result getting taken. Stops crashing.
//            typeCost =  typeCost + typingCost;
        }

        if (place.equals(place_sub_str)) {
            searchState();
        }
        return nextState;
    }

    private static double getTypeCost() {
        double userTypeCost = 0.0;
        userTypeCost = Login.averageLetterTimes.get(Login.position);

        return userTypeCost;
    }


    private static String lookState() {
        currentState = "look";
            if(descriptions.equals("on")){
                System.out.println("\033[34m      User is looking at the list of suggestions. \033[0m");
            }
        return nextState;
    }

    private static String pickState() {
        currentState = "pick";
            if(descriptions.equals("on")){
                System.out.println("\033[34m      User picks '" + place.toUpperCase() + "' from the list of suggestions. \033[0m");
            }
        pickCost = pickCost + pickingCost;
        return currentState;
    }

    private static void searchState() {
        place_sub_str = place;
    }


    public  static ArrayList<Integer> getAttempts() {
        return attempts;
    }

    public static ArrayList<Double> getCosts() {
        return costs;
    }

    public static void createOutputFile() throws IOException{
        File file = new File("output.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
       // bw.write("Attempt" + " Total Cost");
//        bw.newLine();
            for (int i = 0; i < costs.size(); i++) {
       //         bw.write(attempts.get(i).toString() + "         ");
                bw.write(costs.get(i).toString());
                bw.newLine();
        }
            bw.flush();
            bw.close();

    }


}