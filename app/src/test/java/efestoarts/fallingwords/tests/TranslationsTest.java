package efestoarts.fallingwords.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import efestoarts.fallingwords.BuildConfig;
import efestoarts.fallingwords.Translation;
import efestoarts.fallingwords.Translations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TranslationsTest {


    @Test
    public void getRandomTranslation() throws JSONException {
        JSONObject firstTranslation = new JSONObject();
        firstTranslation.put("text_eng", "pupil");
        firstTranslation.put("text_spa", "alumno / alumna");

        JSONObject secondTranslation = new JSONObject();
        secondTranslation.put("text_eng", "holidays");
        secondTranslation.put("text_spa", "vacantiones");

        JSONArray testDictionary = new JSONArray();
        testDictionary.put(firstTranslation);
        testDictionary.put(secondTranslation);

        Translations translations = spy(new Translations(testDictionary));
        when(translations.getNextWordIndexes()).thenReturn(
                new int[]{0,1},
                new int[]{1,0},
                new int[]{0,0});

        Translation translation = translations.getTranslation();

        assertEquals("pupil", translation.translatedWord);
        assertEquals("vacantiones", translation.challengeWord);
        assertEquals(false, translation.isCorrect);

        translation = translations.getTranslation();

        assertEquals("holidays", translation.translatedWord);
        assertEquals("alumno / alumna", translation.challengeWord);
        assertEquals(false, translation.isCorrect);

        translation = translations.getTranslation();

        assertEquals("pupil", translation.translatedWord);
        assertEquals("alumno / alumna", translation.challengeWord);
        assertEquals(true, translation.isCorrect);
    }

    @Test
    public void getNextWordIndex_sometimesIsEqual() throws JSONException {
        JSONObject aTranslation = new JSONObject();
        aTranslation.put("text_eng", "holidays");
        aTranslation.put("text_spa", "vacantiones");

        JSONArray dictionary = mock(JSONArray.class);
        when(dictionary.length()).thenReturn(100);
        when(dictionary.get(anyInt())).thenReturn(aTranslation);

        Translations translations = new Translations(dictionary);

        int sameIndexCounter = 0;

        for (int i = 0; i < 300; i++)
        {
            Translation translation = translations.getTranslation();

            if (translation.isCorrect)
            {
                sameIndexCounter++;
            }
        }

        assertEquals(100, sameIndexCounter, 30);
    }
}
