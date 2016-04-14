package efestoarts.fallingwords;

import rx.functions.Action1;

public class Presenter {

    private Translations translations;
    private RxBus bus;

    public Presenter(Translations translations, RxBus bus) {
        this.translations = translations;
        this.bus = bus;

        bus.toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o.equals("ActivityReady"))
                {
                    Presenter.this.bus.send(Presenter.this.translations.nextTranslation());
                }
                else if(o.equals("RoundEnded"))
                {
                    new Delay(1000) {
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            Presenter.this.bus.send(Presenter.this.translations.nextTranslation());
                        }
                    }.execute();
                }
            }
        });
    }
}
