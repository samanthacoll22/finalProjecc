/**
 * Created by Samantha on 29/02/2017.
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Simulator {

    public static String descriptions = "off";
    public static String ordering = "alphabetical";

    public static void main(String args[]) throws IOException {

        StateTransition.main(args);

    }
}
