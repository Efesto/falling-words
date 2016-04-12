package efestoarts.fallingwords;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class PresenterTest {

    @Mock
    Translations mockedTranslations;

    @Mock
    PlayActivity mockedActivity;

    @Test
    public void resume_set_next_round() throws Exception {
        MockitoAnnotations.initMocks(this);
        new Presenter(mockedTranslations).resume(mockedActivity);
        verify(mockedActivity).nextRound(null);
    }
}