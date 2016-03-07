package efestoarts.fallingwords;

public class Translation {
    public final String challengeWord;
    public final String translatedWord;
    public final boolean isCorrect;

    public Translation(String challengeWord, String translatedWord, boolean isCorrect) {

        this.challengeWord = challengeWord;
        this.translatedWord = translatedWord;
        this.isCorrect = isCorrect;
    }


}
