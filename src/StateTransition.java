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

    public static String place;
    public static String place_sub_str = "";
    public static String rndPlace;
    private static String nextState;
    private static String currentState;
    private static String a_letter;
    private static String descriptions = "on";
    private static String sort = "P";              // 'A' for alphabetical and 'P' popularity

    private static double average;
    private static Random randomGenerator;

    private static long totalCost;


    private static int position;
    public static int attempt = 1;
    private static int noOfSuggestions = 6;

    private static int typeCost, lookCostWithPicking, lookCostWithoutPicking, pickCost, searchCost = 0;

    private static ArrayList<Integer> attempts = new ArrayList<Integer>();
    private static ArrayList<Long> costs = new ArrayList<Long>();
    private static ArrayList<String> places = new ArrayList<String>();
    private static ArrayList<String> populations = new ArrayList<String>();

    private static ArrayList<Double> pType = new ArrayList<Double>
            (Arrays.asList(1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.8, 0.85, 0.9, 1.0));
    private static ArrayList<Double> pPick = new ArrayList<Double>
            (Arrays.asList(0.1, 0.1, 0.2, 0.4, 0.6, 0.6, 0.7, 0.5, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.7, 0.8, 0.9, 0.9));
    private static ArrayList<Integer> cPick = new ArrayList<Integer>
            (Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));

    private static Scanner input = new Scanner(System.in);


    public static void main(String args[]) throws IOException {

        getRandomPlace();
        System.out.println(places);

        System.out.println("Please enter a destination from the list above:");
        place = input.nextLine();

        while(!places.contains(place)){
            System.out.println("\033[31m You have not entered a valid destination, please type another. \033[0m");
            place = input.nextLine();
        }

        System.out.println("Attempt			Place			Total Cost");

        for(int i =0 ; i < 5; i++){
            run();
        }

        average = calculateAverage();
        System.out.println();
        System.out.println("The average cost per search is: " + average);
        System.out.println();

//        Histogram graph = new Histogram();
//        graph.histogramGraph();
        System.out.println(getHistogram());

        createOutputFile();

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
        totalCost = typeCost + lookCostWithPicking + lookCostWithoutPicking + pickCost + searchCost;

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
 //               System.out.println(probType);
                if (probType > 0.5) {
                    nextState = "type";
                } else {
                    nextState = "look";
                }
            } else if (currentState == "look") {
                Double probPick = pPick.get(random.nextInt(pType.size()));
//                Double probPick = pPick.get(i);
//                System.out.println(probPick);
                if(probPick > 0.5){
                    nextState = "pick";
                    lookCostWithPicking = lookCostWithPicking + noOfSuggestions;        //1s to look at each suggestions
                } else {
                    nextState = "type";
                    lookCostWithoutPicking = (lookCostWithoutPicking + cPick.get(position));
                }
            } else {
                nextState = "search";
            }
        }
        return nextState;
    }


    private static String typeState() {
        currentState = "type";
        position = (place_sub_str.length());
        a_letter = Character.toString(place.charAt(position));
            if (descriptions.equals("on")){
                System.out.println("\033[34m      User has typed " + "'"+a_letter.toUpperCase()+"'\033[0m");
            }
        place_sub_str = place_sub_str + a_letter;
            if((descriptions.equals("on")) & (position >= 0)){
                getSuggestions();
            }
        typeCost = typeCost + 2;
            if (place.equals(place_sub_str)) {
                searchState();
            }
        return nextState;

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
        pickCost = pickCost + 5;
        return currentState;
    }

    private static void searchState() {
        searchCost = searchCost + 10;
        place_sub_str = place;
    }

    private static double calculateAverage() {
            if (costs == null || costs.isEmpty()) {
                return 0;
            }
        double sum = 0;
            for (Long t : costs) {
                sum += t;
            }
        return sum / costs.size();
    }

    public ArrayList<Integer> getAttempts() {

        return attempts;
    }

    public static ArrayList<Long> getCosts() {

        return costs;
    }

    private static String makeStarRow(Long time) {
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
        ArrayList<Long> histogram = StateTransition.getCosts();
            for (int i = 0; i < costs.size(); i++) {
                Long time = histogram.get(i);
                StateTransition.makeStarRow(time);
            }
        return "";
    }

    public static void createOutputFile() throws IOException{
        File file = new File("output.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Attempt" + " Total Cost");
        bw.newLine();
            for (int i = 0; i < costs.size(); i++) {
                bw.write(attempts.get(i).toString() + "         ");
                bw.write(costs.get(i).toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();

    }

    public static String getSuggestions() {
//        System.out.println("Typed word: " + place_sub_str);
        ArrayList<String> suggestions = new ArrayList<String>();
            for (String word : places) {
                if (word.startsWith(place_sub_str)){
                    suggestions.add(word);
                }
            }
            if(sort.equals("A")){
                Collections.sort(suggestions, String.CASE_INSENSITIVE_ORDER);
            }

            for (int i = 0; i < noOfSuggestions; i++) {
                if(suggestions.size() < noOfSuggestions){
                    suggestions.add("");
                }
                System.out.println("\033[33m                                    " + suggestions.get(i)+ "\033[0m");
            }

        return place_sub_str;
    }

    public static String getRandomPlace() throws FileNotFoundException {
        Scanner s = new Scanner(new File("population2.txt"));
        s.useDelimiter(",");
        String pl, pop;

        while(s.hasNext()){
            pl = s.next().toLowerCase();
            pop = s.nextLine();
            places.add(pl);
            populations.add(pop);
        }
//        while (s.hasNextLine()){
//            places.add(s.nextLine());
//        }
        s.close();

        randomGenerator = new Random();
        int index = randomGenerator.nextInt(places.size());
        String rndPlace = places.get(index);
        return rndPlace;

    }


}