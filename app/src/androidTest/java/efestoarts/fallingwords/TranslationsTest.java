package efestoarts.fallingwords;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class TranslationsTest {

    @Test
    public void nextTranslation() throws JSONException {
        JSONObject firstTranslation = new JSONObject();
        firstTranslation.put("text_eng", "pupil");
        firstTranslation.put("text_spa", "alumno / alumna");

        JSONObject secondTranslation = new JSONObject();
        secondTranslation.put("text_eng", "holidays");
        secondTranslation.put("text_spa", "vacantiones");

        JSONArray testDictionary = new JSONArray();
        testDictionary.put(firstTranslation);
        testDictionary.put(secondTranslation);

        Translations translations = Mockito.spy(new Translations(testDictionary));
        Mockito.when(translations.getNextTranslationIndexes()).thenReturn(
                new int[]{0, 1},
                new int[]{1, 0},
                new int[]{0, 0});

        Translation translation = translations.nextTranslation();

        assertEquals("pupil", translation.translatedWord);
        assertEquals("vacantiones", translation.challengeWord);
        assertEquals(false, translation.isCorrect);

        translation = translations.nextTranslation();

        assertEquals("holidays", translation.translatedWord);
        assertEquals("alumno / alumna", translation.challengeWord);
        assertEquals(false, translation.isCorrect);

        translation = translations.nextTranslation();

        assertEquals("pupil", translation.translatedWord);
        assertEquals("alumno / alumna", translation.challengeWord);
        assertEquals(true, translation.isCorrect);
    }

    @Test
    public void getNextTranslationIndexes_sometimesIsEqual() throws JSONException {
        JSONObject aTranslation = new JSONObject();
        aTranslation.put("text_eng", "holidays");
        aTranslation.put("text_spa", "vacantiones");

        JSONArray dictionary = Mockito.mock(JSONArray.class);
        Mockito.when(dictionary.length()).thenReturn(100);
        Mockito.when(dictionary.get(Matchers.anyInt())).thenReturn(aTranslation);

        Translations translations = new Translations(dictionary);

        int correctTranslationsCounter = 0;

        for (int i = 0; i < 300; i++) {
            Translation translation = translations.nextTranslation();

            if (translation.isCorrect) {
                correctTranslationsCounter++;
            }
        }

        assertEquals(100, correctTranslationsCounter, 30);
    }
}
