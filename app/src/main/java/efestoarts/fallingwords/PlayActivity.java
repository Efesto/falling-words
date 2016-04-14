package efestoarts.fallingwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class PlayActivity extends AppCompatActivity {

    @Bind(R.id.rounds_counter) TextView roundsCounter;
    @Bind(R.id.wrong_words_counter) TextView wrongsCounter;
    @Bind(R.id.right_words_counter) TextView rightsCounter;
    @Bind(R.id.challenge_text) TextView challengeWord;
    @Bind(R.id.translation_text) TextView translationWord;
    @Bind(R.id.correct_translation_button) Button translationIsCorrectButton;
    private Subscription subscription;
    private RxBus bus;

    @OnClick(R.id.correct_translation_button) void clickOnCorrectButton() {
        if (currentTranslation.isCorrect) {
            increaseTextViewCounter(rightsCounter);
        } else {
            increaseTextViewCounter(wrongsCounter);
        }

        endRound();
    }

    private boolean roundIsPlaying;
    private Translation currentTranslation;
    private Animation fallingWordAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

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

        bus = ((FallingWordsApp) getApplication()).getBus();

        subscription = bus.toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if(o.getClass() == Translation.class)
                {
                    nextRound((Translation) o);
                }
            }
        });

        bus.send("ActivityReady");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    public void nextRound(Translation nextTranslation) {
        currentTranslation = nextTranslation;
        roundIsPlaying = true;
        translationIsCorrectButton.setEnabled(true);

        increaseTextViewCounter(roundsCounter);

        challengeWord.setText(currentTranslation.challengeWord);
        challengeWord.setVisibility(View.VISIBLE);
        challengeWord.startAnimation(fallingWordAnimation);

        translationWord.setText(currentTranslation.translatedWord);
    }

    private void endRound() {
        if (roundIsPlaying) {
            roundIsPlaying = false;

            challengeWord.clearAnimation();
            challengeWord.setVisibility(View.INVISIBLE);

            translationIsCorrectButton.setEnabled(false);
            bus.send("RoundEnded");
        }
    }

    private void increaseTextViewCounter(TextView counterView) {
        counterView.setText(String.format("%d", Integer.parseInt((String) counterView.getText()) + 1));
    }
}
