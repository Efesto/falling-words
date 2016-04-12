package efestoarts.fallingwords;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PlayActivityTest {

    PlayActivity activity;
    private Translation correctTranslation;
    private Translation wrongTranslation;

    @Rule
    public ActivityTestRule<PlayActivity> activityTestRule = new ActivityTestRule<>(PlayActivity.class, true, false);

    @Mock
    Translations mockedTranslations;

    @Mock
    Presenter mockedPresenter;

    @Before
    public void before() throws JSONException {

        MockitoAnnotations.initMocks(this);

        ((FallingWordsApp) InstrumentationRegistry.getTargetContext().getApplicationContext()).setPresenter(mockedPresenter);

        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();

        correctTranslation = new Translation("Challenge word", "Translated word", true);
        wrongTranslation = new Translation("Challenge word", "Translated word", false);
    }

    @Test
    public void on_next_round() throws JSONException {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.nextRound(correctTranslation);
            }
        });

        assertRightWordsCounterIs("0");
        assertWrongWordsCounterIs("0");
        assertRoundsCounterIs("1");
        assertViewHasText(R.id.translation_text, "Translated word");
        assertViewHasText(R.id.challenge_text, "Challenge word");
    }

    @Test
    public void press_is_right_button_with_correct_translation() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.nextRound(correctTranslation);
                        isCorrectButton().performClick();
                    }
                });

        assertRightWordsCounterIs("1");
        assertWrongWordsCounterIs("0");
        assertRoundsCounterIs("1");
    }

    @Test
    public void press_is_right_button_with_wrong_translation() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.nextRound(wrongTranslation);
                        isCorrectButton().performClick();
                    }
                }
        );

        assertRightWordsCounterIs("0");
        assertWrongWordsCounterIs("1");
        assertRoundsCounterIs("1");
    }

    @Test
    @Ignore("This little cocky test requires some RXJava")
    public void timeDelays() throws JSONException, InterruptedException {
        when(mockedTranslations.nextTranslation()).thenReturn(
                correctTranslation,
                correctTranslation
        );

        activity.nextRound(correctTranslation);

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

    private void assertViewHasText(int viewId, String expectedText) {
        onView(withId(viewId)).check(matches(withText(expectedText)));
    }


}