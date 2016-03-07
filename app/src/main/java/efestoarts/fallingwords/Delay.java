package efestoarts.fallingwords;

import android.os.AsyncTask;

import java.util.Calendar;

public class Delay extends AsyncTask<Void, Void, Void> {

    private int milliseconds;

    public Delay(int milliseconds)
    {
        this.milliseconds = milliseconds;
    }

    private Calendar endTime;

    @Override
    protected void onPreExecute() {
        endTime = Calendar.getInstance();
        endTime.add(Calendar.MILLISECOND, milliseconds);

    }

    @Override
    protected Void doInBackground(Void... params) {
        while(Calendar.getInstance().compareTo(endTime) <= 0)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        return null;
    }
}
