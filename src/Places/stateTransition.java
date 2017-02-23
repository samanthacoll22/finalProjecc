package Places;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class stateTransition<StateNode, nodes> implements Simulate{

	static String place;
    static String place_sub_str = "";
    static String nextState;
    static String currentState;    
    static int totalCost, typeCost, lookCost, pickCost, searchCost = 0;  

    static Scanner input = new Scanner(System.in);

    static ArrayList<Double> pType = new ArrayList<Double>
    	(Arrays.asList(1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.8, 0.85, 0.9, 1.0));
    static ArrayList<Double> pPick = new ArrayList<Double>
		(Arrays.asList(0.9, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.2, 0.1, 0.3, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.9));
    static ArrayList<Integer> cPick = new ArrayList<Integer>
		(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));


	public static void main(String args[]) throws FileNotFoundException {

		Scanner s = new Scanner(new File("cities.txt"));
		ArrayList<String> places = new ArrayList<String>();
		while (s.hasNextLine()){
		    places.add(s.nextLine());
		}
		s.close();
		System.out.println(places);
		

        while(!places.contains(place_sub_str) ) {
            nextState = getNextState();
          System.out.println(place_sub_str);
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

        System.out.println("You have typed in the full destination." );
        totalCost = typeCost + lookCost + pickCost + searchCost;
        System.out.println("The cost of this query is " + totalCost);
    }

    private static String getNextState() {
        int i = place_sub_str.length();
        if(i<=2){
            nextState = "type";
        } else {
            if(currentState == "type"){
                System.out.println("If you would like to type another letter, press 'T' " );
                System.out.println("If you would like to look at the list of suggestions, press 'L' " );
                if (input.nextLine().equalsIgnoreCase("T")) {
                    nextState = "type";
                } else if (input.nextLine().equalsIgnoreCase("L")) {
                    nextState = "look";
                } else {
                    System.out.println("Please enter a valid option, either 'T' or 'L'." );
                }

            } else if (currentState == "look") {
                System.out.println("You are looking at the  list of suggestions" );
                System.out.println("If you would like to type another letter, press 'T' or if you would like "
                        + "to pick from the list of suggestions press 'P'." );
                if(input.nextLine().equalsIgnoreCase("T")){
                    nextState = "type";
                } else if (input.nextLine().equalsIgnoreCase("P")) {
                    nextState = "pick";
                } else {
                    System.out.println("Please enter a valid option, either 'T' or 'P'." );
                }

            } else {
                nextState = "end";
            }

        }
        return nextState;
    }


    private static String typeState() {
        currentState = "type";
        System.out.println("Please type a letter." );
        place_sub_str = place_sub_str + input.nextLine();

        typeCost = typeCost + 5;
        return nextState;
    }

    private static String lookState() {
        currentState = "look";

        lookCost = lookCost + 10;
        return nextState;
    }

    private static String pickState() {
        currentState = "pick";
        System.out.println("You are picking an option from the list of suggestions.");

        pickCost = pickCost + 20;
        return currentState;
    }

    private static void searchState() {
        System.out.println("You picked your selection from the list." );

        searchCost = searchCost + 50;
        place_sub_str = place;
    }

}