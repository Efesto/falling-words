package efestoarts.fallingwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private Translation currentTranslation;
    private TextView roundsCounter;
    private TextView wrongsCounter;
    private TextView rightsCounter;
    private Animation fallingWordAnimation;
    private TextView challengeWord;
    private boolean roundIsPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        challengeWord = (TextView) findViewById(R.id.challenge_text);

        roundsCounter = (TextView) findViewById(R.id.rounds_counter);
        wrongsCounter = (TextView) findViewById(R.id.wrong_words_counter);
        rightsCounter = (TextView) findViewById(R.id.right_words_counter);

        roundsCounter.setText("0");
        wrongsCounter.setText("0");
        rightsCounter.setText("0");

        fallingWordAnimation = AnimationUtils.loadAnimation(this, R.anim.falling_animation);
        fallingWordAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (roundIsPlaying) {
                    if (currentTranslation.isCorrect) {
                        increaseTextViewCounter(wrongsCounter);
                    } else {
                        increaseTextViewCounter(rightsCounter);
                    }

                    endRound();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        nextRound();
    }

    private void endRound() {
        if (roundIsPlaying) {
            roundIsPlaying = false;

            challengeWord.clearAnimation();
            challengeWord.setVisibility(View.INVISIBLE);

            findViewById(R.id.right_button).setEnabled(false);

            betweenRoundsTimer().execute();
        }
    }

    public void nextRound() {
        roundIsPlaying = true;
        findViewById(R.id.right_button).setEnabled(true);

        increaseTextViewCounter(roundsCounter);

        currentTranslation = null;
        try {
            currentTranslation = getTranslations().getTranslation();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.right_button).setOnClickListener(this);

        challengeWord.setVisibility(View.VISIBLE);
        challengeWord.setText(currentTranslation.challengeWord);
        challengeWord.startAnimation(fallingWordAnimation);

        ((TextView) findViewById(R.id.translation_text)).setText(currentTranslation.translatedWord);
    }

    public Translations getTranslations() throws JSONException {
        String words = null;
        InputStream inputStream = null;
        try {
            inputStream = getResources().openRawResource(R.raw.words);

            words = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return new Translations(new JSONArray(words));
    }

    @Override
    public void onClick(View v) {
        if (currentTranslation.isCorrect) {
            increaseTextViewCounter(rightsCounter);
        } else {
            increaseTextViewCounter(wrongsCounter);
        }

        endRound();
    }

    //AsyncTask can be executed only once so we need to build a new one every time we need one
    public Delay betweenRoundsTimer() {
        return new Delay(1000) {
            @Override
            protected void onPostExecute(Void aVoid) {
                nextRound();
            }
        };
    }

    private void increaseTextViewCounter(TextView counterView) {
        counterView.setText(String.format("%d", Integer.parseInt((String) counterView.getText()) + 1));
    }
}
