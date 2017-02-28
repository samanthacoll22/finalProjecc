package Places;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;



public class stateTransition<StateNode, nodes> implements Simulate{

	private static String place;
    private static String place_sub_str = "";
    private static String nextState;
    private static String currentState;
    private static String a_letter;
    private static int position;
    private static long totalTime;
   	private static long startTime;
   	private static Random random;
	
    private static int totalCost, typeCost, lookCostWithPicking, lookCostWithoutPicking, pickCost, searchCost = 0;      
    private static Scanner input = new Scanner(System.in);

    private static ArrayList<Double> pType = new ArrayList<Double>
    	(Arrays.asList(1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.8, 0.85, 0.9, 1.0));
    private static ArrayList<Double> pPick = new ArrayList<Double>
		(Arrays.asList(0.9, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.2, 0.1, 0.3, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.9));
    private static ArrayList<Integer> cPick = new ArrayList<Integer>
		(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    


	public static void main(String args[]) throws FileNotFoundException {

		System.out.println("Please enter a destination:");
		place = input.nextLine();		
		
//		Scanner s = new Scanner(new File("cities.txt"));
//		ArrayList<String> places = new ArrayList<String>();
//		while (s.hasNextLine()){
//		    places.add(s.nextLine());
//		}
//		s.close();
//		System.out.println(places);
		
	    startTime = (System.currentTimeMillis()/1000);

        System.out.println("Time			Action");
        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			Starting...");
        
		
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
        System.out.println(" ");
        System.out.println("The cost of this query is " + totalCost);        

        totalTime = ((System.currentTimeMillis()/1000) - startTime);
        
    }

    private static String getNextState() {
        int i = place_sub_str.length();
        Random random = new Random();        
        if(i<=2){        	
            nextState = "type";            
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } else {
        	
            if(currentState == "type"){            	
            	Double randomType = pType.get(random.nextInt(pType.size()));
//            	System.out.println(randomType);
                if (randomType > 0.5) {
                    nextState = "type";
                    
                } else {
                	nextState = "look";
                }
                
	                try {
	    				Thread.sleep(1500);
	    			} catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}

            } else if (currentState == "look") {
            	Double randomPick = pPick.get(random.nextInt(pType.size()));
//            	System.out.println(randomPick);
            	if(randomPick > 0.5){
                    nextState = "pick";
                    lookCostWithPicking = lookCostWithPicking + 5;
//                    System.out.println(lookCostWithPicking);
                } else {
                    nextState = "type";
                    lookCostWithoutPicking = (lookCostWithoutPicking + cPick.get(position));
                    System.out.println(lookCostWithoutPicking);
                    }
                
	                try {
	    				Thread.sleep(2000);
	    			} catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}

            } else {
                nextState = "end";
            }
        }
        return nextState;
    }


    private static String typeState() {
        currentState = "type";
        position = (place_sub_str.length());
        a_letter = Character.toString(place.charAt(position));
        
        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			User has typed " + "'"+a_letter.toUpperCase()+"'");
        place_sub_str = place_sub_str + a_letter;

        typeCost = typeCost + 2;
        return nextState;
    }

    private static String lookState() {
        currentState = "look";        
        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			User is looking at the list of suggestions.");

        return nextState;
    }

    private static String pickState() {
        currentState = "pick";
        place_sub_str = place;
        System.out.println(((System.currentTimeMillis()/1000) - startTime) + "			User picks '" + place.toUpperCase() + "' from the list of suggestions.");
        
        pickCost = pickCost + 10;
        return currentState;
    }

    private static void searchState() {
//        System.out.println("You picked your selection from the list." );

//       searchCost = searchCost + 50;
//        place_sub_str = place;
    }
    


}