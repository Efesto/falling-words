package efestoarts.fallingwords.tests;

import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import efestoarts.fallingwords.BuildConfig;
import efestoarts.fallingwords.Delay;
import efestoarts.fallingwords.PlayActivity;
import efestoarts.fallingwords.R;
import efestoarts.fallingwords.Translation;
import efestoarts.fallingwords.Translations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Ignore("These requires some redesign of the activity because the delays screws the execution")
@Config(sdk = 21, constants = BuildConfig.class)
public class PlayActivityTest {

    PlayActivity activity;
    private Translations translations;
    private Translation correctTranslation;
    private Translation wrongTranslation;

    private void initActivity() throws JSONException {
        activity = spy(Robolectric.setupActivity(PlayActivity.class));
        translations = mock(Translations.class);
        doReturn(translations).when(activity).getTranslations();
    }

    @Before
    public void before() throws JSONException {
        initActivity();

        correctTranslation = new Translation("Challenge word", "Translated word", true);
        wrongTranslation = new Translation("Challenge word", "Translated word", false);
        when(activity.betweenRoundsTimer()).thenReturn(new Delay(10));
    }

    @Test
    public void appearance() throws JSONException {

        when(translations.nextTranslation()).thenReturn(
                correctTranslation
        );

        activity.nextRound();

        assertRightWordsCounterIs("0");
        assertWrongWordsCounterIs("0");
        assertRoundsCounterIs("2");
        assertEquals("Translated word", ((TextView) activity.findViewById(R.id.translation_text)).getText());
        assertEquals("Challenge word", ((TextView) activity.findViewById(R.id.challenge_text)).getText());
    }

    @Test
    public void rightButton() throws JSONException {
        when(translations.nextTranslation()).thenReturn(
                correctTranslation,
                wrongTranslation
        );

        activity.nextRound();

        isCorrectButton().performClick();

        assertRightWordsCounterIs("1");
        assertWrongWordsCounterIs("0");
        assertRoundsCounterIs("2");
        activity.nextRound();

        isCorrectButton().performClick();

        assertRightWordsCounterIs("1");
        assertWrongWordsCounterIs("1");
        assertRoundsCounterIs("3");
    }

    @Test
    public void timeDelays() throws JSONException, InterruptedException {

        initActivity();

        when(translations.nextTranslation()).thenReturn(
                correctTranslation,
                correctTranslation
        );

        activity.nextRound();

        isCorrectButton().performClick();
        assertFalse(isCorrectButton().isEnabled());
        Thread.sleep(1100);

        assertRoundsCounterIs("3");
        assertTrue(isCorrectButton().isEnabled());

        Thread.sleep(3000);
        assertFalse(isCorrectButton().isEnabled());

        assertRoundsCounterIs("4");
        assertWrongWordsCounterIs("3");
        assertRightWordsCounterIs("1");
    }

    private View isCorrectButton() {
        return activity.findViewById(R.id.correct_translation_button);
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


}