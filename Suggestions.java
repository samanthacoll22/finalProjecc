import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Samantha on 19/03/2017.
 */
public class Suggestions {

    static StateTransition s = new StateTransition();

    public static String getSuggestions() {
        ArrayList<String> suggestions = new ArrayList<String>();
        for (String word : s.places) {
            if (word.startsWith(s.place_sub_str)){
                suggestions.add(word);
            }
        }

        if(Simulator.sort.equals("A")) {
            Collections.sort(suggestions, String.CASE_INSENSITIVE_ORDER);
        }

        for (int i = 0; i < s.noOfSuggestions; i++) {
            s.shownSuggestions.add(suggestions.get(i));
            if (suggestions.size() < s.noOfSuggestions) {
                suggestions.add("");
            }
            if (Simulator.descriptions.equals("on")){
                System.out.println("\033[33m                                    " + s.shownSuggestions.get(i) + "\033[0m");
            }

        }

        return s.place_sub_str;
    }
}
