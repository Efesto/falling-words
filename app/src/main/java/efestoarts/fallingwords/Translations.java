package efestoarts.fallingwords;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Translations {
    private JSONArray dictionary;

    public Translations(JSONArray dictionary) {
        this.dictionary = dictionary;
    }

    public Translation getTranslation() throws JSONException {
        JSONObject firstPair = (JSONObject)dictionary.get(0);
        String challengeWord = firstPair.getString("text_spa");
        String translationWord = firstPair.getString("text_eng");

        return new Translation(challengeWord, translationWord, true);
    }
}
