import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Samantha on 19/03/2017.
 */
public class RandomPlace {

    private static Random randomGenerator;

    public static String getRandomPlace() throws FileNotFoundException {
        Scanner s = new Scanner(new File("simpleCities.txt"));  // A simpler texfile, with more popular cities.
        String pl;

        ArrayList<String> RndPlaces = new ArrayList<>();

        while(s.hasNext()){
            pl = s.nextLine().toLowerCase();
            RndPlaces.add(pl);
        }
        s.close();

        randomGenerator = new Random();
        int index = randomGenerator.nextInt(RndPlaces.size());
        String rndPlace = RndPlaces.get(index);
        return rndPlace;

    }
}
