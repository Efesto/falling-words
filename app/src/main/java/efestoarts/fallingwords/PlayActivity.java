package efestoarts.fallingwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class PlayActivity extends AppCompatActivity {

    private Translation currentTranslation;
    private TextView roundsCounter;
    private TextView wrongsCounter;
    private TextView rightsCounter;

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

    public void nextRound() {
        currentTranslation = null;

        try {
            currentTranslation = getTranslations().getTranslation();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentTranslation.isCorrect) {
                    increaseCounter(wrongsCounter);
                } else {
                    increaseCounter(rightsCounter);
                }

                increaseCounter(roundsCounter);
            }
        });

        findViewById(R.id.wrong_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTranslation.isCorrect) {
                    increaseCounter(wrongsCounter);
                } else {
                    increaseCounter(rightsCounter);
                }

                increaseCounter(roundsCounter);
            }
        });


        ((TextView) findViewById(R.id.challenge_text)).setText(currentTranslation.challengeWord);
        ((TextView) findViewById(R.id.translation_text)).setText(currentTranslation.translatedWord);
    }

    private void increaseCounter(TextView counterView)
    {
        counterView.setText(Integer.parseInt((String) counterView.getText())  + 1 + "");
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
}
