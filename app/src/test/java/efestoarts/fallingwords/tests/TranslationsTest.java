package efestoarts.fallingwords.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import efestoarts.fallingwords.BuildConfig;
import efestoarts.fallingwords.Translation;
import efestoarts.fallingwords.Translations;

import static junit.framework.Assert.*;


@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TranslationsTest {

    @Test
    public void getTranslationTest() throws JSONException {

        JSONObject firstTranslation = new JSONObject();
        firstTranslation.put("text_eng", "pupil");
        firstTranslation.put("text_spa", "alumno / alumna");

//        JSONObject secondTranslation = new JSONObject();
//        firstTranslation.put("text_eng", "pupil");
//        firstTranslation.put("text_spa", "alumno / alumna");

        JSONArray testDictionary = new JSONArray();
        testDictionary.put(firstTranslation);

        Translations translations = new Translations(testDictionary);
        Translation translation = translations.getTranslation();

        assertEquals("pupil", translation.translatedWord);
        assertEquals("alumno / alumna", translation.challengeWord);
        assertEquals(true, translation.isCorrect);
    }
}
