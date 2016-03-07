package efestoarts.fallingwords;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

public class Translations {
    private JSONArray dictionary;
    private Random random;

    public Translations(JSONArray dictionary) {
        random = new Random(Calendar.getInstance().get(Calendar.MILLISECOND));
        this.dictionary = dictionary;
    }

    public Translation getTranslation() throws JSONException {
        int[] translationIndexes = getNextWordIndexes();

        JSONObject firstPair = (JSONObject)dictionary.get(translationIndexes[0]);
        String translationWord = firstPair.getString("text_eng");

        JSONObject secondPair = (JSONObject)dictionary.get(translationIndexes[1]);
        String challengeWord = secondPair.getString("text_spa");

        boolean isCorrect = translationIndexes[0] == translationIndexes[1];
        return new Translation(challengeWord, translationWord, isCorrect);
    }

    public int[] getNextWordIndexes() {
        int randomWordIndex1 = random.nextInt(dictionary.length() - 1);
        int randomWordIndex2 = random.nextInt(dictionary.length() - 1);

        int tweakFactor = 3;
        if (random.nextInt(tweakFactor) == random.nextInt(tweakFactor))
        {
            randomWordIndex1 = randomWordIndex2;
        }

        return new int[]{randomWordIndex1, randomWordIndex2};
    }
}
