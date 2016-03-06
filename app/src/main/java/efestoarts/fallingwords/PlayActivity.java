package efestoarts.fallingwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String challengeWord = null;
        String translationWord = null;

        setContentView(R.layout.activity_play);
        try {
            JSONArray dictionary = getWordsDictionary();
            JSONObject firstPair = (JSONObject)dictionary.get(0);
            challengeWord = firstPair.getString("text_spa");
            translationWord = firstPair.getString("text_eng");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.challenge_text)).setText(challengeWord);
        ((TextView) findViewById(R.id.translation_text)).setText(translationWord);
    }

    private JSONArray getWordsDictionary() throws JSONException {
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

        return new JSONArray(words);
    }
}
