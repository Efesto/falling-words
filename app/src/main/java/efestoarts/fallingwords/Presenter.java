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
        activity.nextRound(translations.nextTranslation());
    }
}
