package efestoarts.fallingwords.tests;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import efestoarts.fallingwords.*;
import static junit.framework.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=21, constants = BuildConfig.class)
public class PlayActivityTest {

    @Test
    public void appearance()
    {
        PlayActivity activity = Robolectric.setupActivity(PlayActivity.class);

        assertEquals("primary school", ((TextView)activity.findViewById(R.id.translation_text)).getText());
        assertEquals("escuela primaria", ((TextView)activity.findViewById(R.id.challenge_text)).getText());
    }

}