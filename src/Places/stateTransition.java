package Places;

import java.util.Scanner;


public class stateTransition<StateNode, nodes>{
	
		   static String place;
		   static String place_sub_str = "";
		   static String nextState;		   
		   static String type;
		   static String look;
		   static String pick;
		   
		   static int totalCost, typeCost, lookCost, pickCost = 0; 
		   
		   static Scanner input = new Scanner(System.in);
		   
	   public static void main(String args[]) {
		   System.out.println( "Please enter a destination: " );  
		   place = input.nextLine();
		   
		   while(!place.equals(place_sub_str) ) {
			   	nextState = getNextState();			   	
				if(nextState == type){
			   		typeState();
			   	} else if (nextState == look) { 
			   		lookState();
//			   		System.out.println("look" );
			   	} else {
			   		pickState();
//			   		System.out.println("pick" );
			   	}	
		   	}
		   System.out.println("You have typed in the full destination." );
		   
		   totalCost = typeCost + lookCost + pickCost;
		   System.out.println("The cost of this query is " + totalCost);
	   }
	   
	private static String getNextState() {
		int i = place_sub_str.length();
	   	if(i<=2){
	   		nextState = type;
//			System.out.println(i);
	   	} else {
	   			System.out.println("If you would like to type another letter, press 'T' " );
	   			System.out.println("If you would like to look at the list of suggestions, press 'L' " );
	   			if (input.nextLine().equalsIgnoreCase("T")) {
	   				nextState = type;
	   			} else if (input.nextLine().equalsIgnoreCase("L")) { 
	   				nextState = look;
	   				System.out.println(nextState);
	   			} else {
	   				System.out.println("Please enter a valid option, either 'T' or 'L'." );
	   			}   			
	   	}
		return nextState;
	} 
	

	private static void typeState() {
		System.out.println("Please type a letter." );
		place_sub_str = place_sub_str + input.nextLine();
		
		typeCost = typeCost + 5;
	}      
		
	private static void lookState() {
		System.out.println("You are looking at the  list of suggestions" );
		System.out.println("If you would like to type another letter, press 'T' or if you would like"
					+ "to pick from the list of suggestions press 'P'." );
			if(input.nextLine().equalsIgnoreCase("T")){
				nextState=type;
			} else if (input.nextLine().equalsIgnoreCase("P")) { 
	   			nextState = pick;
	   		} else {
	   			System.out.println("Please enter a valid option, either 'T' or 'L'." );
	   		}	
		lookCost = lookCost + 10;
	} 
		
	private static void pickState() {
		System.out.println("You are picking an option from the list of suggestions.");
		pickCost = pickCost + 20;
	} 
	
}