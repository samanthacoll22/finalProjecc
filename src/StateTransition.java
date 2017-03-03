/**
 * Created by Samantha on 25/02/2017.
 */

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StateTransition implements Simulator{

    public static String place;
    public static String place_sub_str = "";
    private static String nextState;
    private static String currentState;
    private static String a_letter;

    private static double average;

    private static long timeTaken;
    private static long startTime;

    private static Random random;

    private static int position;
    public static int attempt = 1;

    private static int totalCost, typeCost, lookCostWithPicking, lookCostWithoutPicking, pickCost, searchCost = 0;

    private static ArrayList<Double> pType = new ArrayList<Double>
            (Arrays.asList(1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.8, 0.85, 0.9, 1.0));
    private static ArrayList<Double> pPick = new ArrayList<Double>
            (Arrays.asList(0.1, 0.1, 0.2, 0.5, 0.6, 0.6, 0.7, 0.5, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.7, 0.8, 0.9, 0.9));
    private static ArrayList<Integer> cPick = new ArrayList<Integer>
            (Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    private static ArrayList<Integer> attempts = new ArrayList<Integer>();
    private static ArrayList<Long> times = new ArrayList<Long>();

    private static Scanner input = new Scanner(System.in);



    public static void main(String args[]) throws FileNotFoundException {

        Scanner s = new Scanner(new File("cities.txt"));
        ArrayList<String> places = new ArrayList<String>();
        while (s.hasNextLine()){
            places.add(s.nextLine());
        }
        s.close();
        System.out.println(places);

        System.out.println("Please enter a destination from the list above:");
        place = input.nextLine();

        System.out.println("Attempt			Place			Total Cost 		Time Taken");

        for(int i =0 ; i <5 ; i++){
            run();
        }
        average = calculateAverage();
        System.out.println("The average time taken out per search is: " + average);

//        Histogram graph = new Histogram();
//        graph.histogramGraph();

        System.out.println(getHistogram());
    }

    public static void run() throws FileNotFoundException{

        startTime = (System.currentTimeMillis()/1000);

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

        timeTaken = ((System.currentTimeMillis()/1000) - startTime);
        System.out.println(attempt +"	    		" + place.toUpperCase() + "			" + totalCost + "		    	" + timeTaken);
        times.add(timeTaken);

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
                Double randomType = pType.get(random.nextInt(pType.size()));
                if (randomType > 0.5) {
                    nextState = "type";
                } else {
                    nextState = "look";
                }
            } else if (currentState == "look") {
                Double randomPick = pPick.get(random.nextInt(pType.size()));
                if(randomPick > 0.5){
                    nextState = "pick";
                    lookCostWithPicking = lookCostWithPicking + 5;
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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        position = (place_sub_str.length());
        a_letter = Character.toString(place.charAt(position));

//        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			User has typed " + "'"+a_letter.toUpperCase()+"'");
        place_sub_str = place_sub_str + a_letter;
        typeCost = typeCost + 2;

        if (place.equals(place_sub_str)) {
            searchState();
        }

        return nextState;

    }

    private static String lookState() {
        currentState = "look";
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			User is looking at the list of suggestions.");
        return nextState;
    }

    private static String pickState() {
        currentState = "pick";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			User picks '" + place.toUpperCase() + "' from the list of suggestions.");

        pickCost = pickCost + 10;
        return currentState;
    }

    private static void searchState() {
        searchCost = searchCost + 20;
        place_sub_str = place;
    }

    private static double calculateAverage() {
        if (times == null || times.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (Long t : times) {
            sum += t;
        }
        return sum / times.size();
    }

    public ArrayList<Integer> getAttempts() {
        return attempts;
    }

    public static ArrayList<Long> getTimes() {
        return times;
    }

    private static String makeStarRow(Long time) {
        while (time > 0) {
            System.out.print("*");
            time--;
        }
        if (time < 1) {
            System.out.println();
        }
        return null;
    }

    public static String getHistogram() {
        ArrayList<Long> histogram = StateTransition.getTimes();
        for (int i = 0; i <times.size(); i++) {
            Long time = histogram.get(i);
            StateTransition.makeStarRow(time);
        }
        return "";
    }

}