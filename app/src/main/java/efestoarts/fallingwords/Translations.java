package efestoarts.fallingwords;

import org.json.JSONArray;
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

    public Translation nextTranslation() {

        try {
            int[] translationIndexes = getNextTranslationIndexes();

            JSONObject firstPair = (JSONObject) dictionary.get(translationIndexes[0]);

            String translationWord = firstPair.getString("text_eng");

            JSONObject secondPair = (JSONObject) dictionary.get(translationIndexes[1]);
            String challengeWord = secondPair.getString("text_spa");

            boolean isCorrect = translationIndexes[0] == translationIndexes[1];
            return new Translation(challengeWord, translationWord, isCorrect);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public int[] getNextTranslationIndexes() {
        int upperIndex = dictionary.length();
        int randomWordIndex1 = random.nextInt(upperIndex);
        int randomWordIndex2 = random.nextInt(upperIndex);

        //About 1/randomFactor of the translations are corrects
        int randomFactor = 3;
        if (random.nextInt(randomFactor) == 0)
        {
            randomWordIndex1 = randomWordIndex2;
        }

        return new int[]{randomWordIndex1, randomWordIndex2};
    }
}
