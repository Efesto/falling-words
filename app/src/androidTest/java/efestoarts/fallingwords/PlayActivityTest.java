package efestoarts.fallingwords;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PlayActivityTest {

    PlayActivity activity;
    private Translations translations;
    private Translation correctTranslation;
    private Translation wrongTranslation;

    @Rule
    public ActivityTestRule<PlayActivity> activityTestRule = new ActivityTestRule<>(PlayActivity.class);

    private void initActivity() throws JSONException {
        activity = spy(activityTestRule.getActivity());
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.nextRound();

                assertRightWordsCounterIs("0");
                assertWrongWordsCounterIs("0");
                assertRoundsCounterIs("2");
                assertViewHasText(R.id.translation_text, "Translated word");
                assertViewHasText(R.id.challenge_text, "Challenge word");
            }
        });
    }

    @Test
    public void rightButton() throws JSONException {
        when(translations.nextTranslation()).thenReturn(
                correctTranslation,
                wrongTranslation
        );

        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
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
                }
        );
    }

    @Test
    @Ignore("This little cocky test requires some RXJava")
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
        assertViewHasText(R.id.right_words_counter, expected);
    }

    private void assertWrongWordsCounterIs(String expected) {
        assertViewHasText(R.id.wrong_words_counter, expected);
    }

    private void assertRoundsCounterIs(String expected) {
        assertViewHasText(R.id.rounds_counter, expected);
    }

    private void assertViewHasText(int viewId, String expectedText)
    {
        onView(withId(viewId)).check(matches(withText(expectedText)));
    }


}