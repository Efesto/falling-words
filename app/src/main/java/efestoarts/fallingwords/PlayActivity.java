package efestoarts.fallingwords;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private boolean roundIsGoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        roundsCounter = (TextView) findViewById(R.id.rounds_counter);
        wrongsCounter = (TextView) findViewById(R.id.wrong_words_counter);
        rightsCounter = (TextView) findViewById(R.id.right_words_counter);

        roundsCounter.setText("0");
        wrongsCounter.setText("0");
        rightsCounter.setText("0");

        nextRound();
    }

    private void endRound() {
        roundIsGoing = false;
        roundTimer().cancel(false);

        findViewById(R.id.right_button).setEnabled(false);
        findViewById(R.id.wrong_button).setEnabled(false);

        betweenRoundsTimer().execute();
    }

    public void nextRound() {
        roundIsGoing = true;
        findViewById(R.id.right_button).setEnabled(true);
        findViewById(R.id.wrong_button).setEnabled(true);

        increaseCounter(roundsCounter);
        roundTimer().execute();

        currentTranslation = null;
        try {
            currentTranslation = getTranslations().getTranslation();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.right_button).setOnClickListener(this);
        findViewById(R.id.wrong_button).setOnClickListener(this);

        ((TextView) findViewById(R.id.challenge_text)).setText(currentTranslation.challengeWord);
        ((TextView) findViewById(R.id.translation_text)).setText(currentTranslation.translatedWord);
    }

    private void increaseCounter(TextView counterView) {
        counterView.setText(String.format("%d", Integer.parseInt((String) counterView.getText()) + 1));
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
        if ((currentTranslation.isCorrect && v.getId() == R.id.wrong_button) || (!currentTranslation.isCorrect && v.getId() == R.id.right_button)) {
            increaseCounter(wrongsCounter);
        } else {
            increaseCounter(rightsCounter);
        }

        endRound();
    }

    //AsyncTask can be executed only once
    public Delay roundTimer() {
        return new Delay(3000) {
            @Override
            protected void onPostExecute(Void aVoid) {
                if (roundIsGoing) {
                    increaseCounter(wrongsCounter);
                    endRound();
                }
            }
        };
    }

    public Delay betweenRoundsTimer() {
        return new Delay(1000) {
            @Override
            protected void onPostExecute(Void aVoid) {
                nextRound();
            }
        };
    }
}
