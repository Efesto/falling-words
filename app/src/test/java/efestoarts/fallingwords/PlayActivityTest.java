package efestoarts.fallingwords.tests;

import junit.framework.Assert;

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

        assertNotNull(activity);

    }

}