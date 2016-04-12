package efestoarts.fallingwords;

public class Presenter {

    private PlayActivity activity;
    private Translations translations;

    public Presenter(Translations translations) {
        this.translations = translations;
    }

    public void resume(PlayActivity activity) {
        this.activity = activity;
        activity.nextRound(translations.nextTranslation());
    }

    public void roundEnded() {
        new Delay(1000) {
            @Override
            protected void onPostExecute(Void aVoid) {
                activity.nextRound(translations.nextTranslation());
            }
        }.execute();
    }
}
