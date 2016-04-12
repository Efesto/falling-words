package efestoarts.fallingwords;

import android.app.Application;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import java.io.InputStream;

public class FallingWordsApp extends Application {

    private Translations translations;
    private Presenter presenter;

    public Presenter getPresenter()
    {
        if (presenter == null) {
            presenter = new Presenter(getTranslations());
        }
        return presenter;
    }

    private Translations getTranslations() {
        if(translations == null) {
            InputStream inputStream = null;
            try {
                inputStream = getResources().openRawResource(R.raw.words);

                String words = IOUtils.toString(inputStream);
                translations = new Translations(new JSONArray(words));
            } catch (Exception e) {
                e.printStackTrace();

                //We don't like code that doesn't says what's wrong
                throw new RuntimeException(e);

            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return translations;
    }

    public void setTranslations(Translations translations)
    {
        this.translations = translations;
    }
}
