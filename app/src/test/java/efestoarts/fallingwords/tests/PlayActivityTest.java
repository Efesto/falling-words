package efestoarts.fallingwords.tests;

import android.widget.TextView;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import efestoarts.fallingwords.*;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=21, constants = BuildConfig.class)
public class PlayActivityTest {

    PlayActivity activity;

    @Before
    public void before() {
        activity = spy(Robolectric.setupActivity(PlayActivity.class));
    }

    @Test
    public void appearance() throws JSONException {

        Translation translation = new Translation("Challenge word", "Translated word", true);
        setNextTranslation(translation);

        activity.nextRound();

        assertRightWordsCounterIs("0");
        assertWrongWordsCounterIs("0");
        assertRoundsCounterIs("0");
        assertEquals("Translated word", ((TextView) activity.findViewById(R.id.translation_text)).getText());
        assertEquals("Challenge word", ((TextView) activity.findViewById(R.id.challenge_text)).getText());
    }

    @Test
    public void rightButton() throws JSONException {
        Translation translation = new Translation("Challenge word", "Translated word", true);
        setNextTranslation(translation);
        activity.nextRound();

        activity.findViewById(R.id.right_button).performClick();

        assertRightWordsCounterIs("1");
        assertWrongWordsCounterIs("0");
        assertRoundsCounterIs("1");

        translation = new Translation("Challenge word", "Translated word", false);
        setNextTranslation(translation);
        activity.nextRound();

        activity.findViewById(R.id.right_button).performClick();

        assertRightWordsCounterIs("1");
        assertWrongWordsCounterIs("1");
        assertRoundsCounterIs("2");
    }


    @Test
    public void wrongButton() throws JSONException {
        Translation translation = new Translation("Challenge word", "Translated word", true);
        setNextTranslation(translation);
        activity.nextRound();

        activity.findViewById(R.id.wrong_button).performClick();

        assertRightWordsCounterIs("0");
        assertWrongWordsCounterIs("1");
        assertRoundsCounterIs("1");

        translation = new Translation("Challenge word", "Translated word", false);
        setNextTranslation(translation);
        activity.nextRound();

        activity.findViewById(R.id.wrong_button).performClick();

        assertRightWordsCounterIs("1");
        assertWrongWordsCounterIs("1");
        assertRoundsCounterIs("2");
    }

    private void assertRightWordsCounterIs(String expected) {
        assertEquals(expected, ((TextView) activity.findViewById(R.id.right_words_counter)).getText());
    }

    private void assertWrongWordsCounterIs(String expected) {
        assertEquals(expected, ((TextView) activity.findViewById(R.id.wrong_words_counter)).getText());
    }

    private void assertRoundsCounterIs(String expected) {
        assertEquals(expected, ((TextView) activity.findViewById(R.id.rounds_counter)).getText());
    }

    private void setNextTranslation(Translation translation) throws JSONException {
        Translations translations = mock(Translations.class);
        doReturn(translation).when(translations).getTranslation();
        doReturn(translations).when(activity).getTranslations();
    }


}