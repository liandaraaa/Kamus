package com.made.lianda.kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.made.lianda.kamus.db.KamusHelper;
import com.made.lianda.kamus.model.English;
import com.made.lianda.kamus.model.Indonesia;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreloadDataActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload_data);

        progressBar = findViewById(R.id.preload_rogressbar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreferences preferences;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(PreloadDataActivity.this);
            preferences = new AppPreferences(PreloadDataActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = preferences.getFirstRun();

            if (firstRun) {
                ArrayList<Indonesia> indonesias = preLoadRaw();
                ArrayList<English> englishes = preLoadEnglishRaw();

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);

                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / englishes.size();

                kamusHelper.beginTransaction();

                try {
                    for (Indonesia kamus : indonesias) {
                        kamusHelper.insertTransactionIndonesia(kamus);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {

                }

                try {
                    for (English english : englishes){
                        kamusHelper.insertTransactionEnglish(english);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e){

                }

                kamusHelper.endTransaction();
                kamusHelper.close();

                preferences.setFirstRun(false);
                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(PreloadDataActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<Indonesia> preLoadRaw() {
        ArrayList<Indonesia> kamusIndonesias = new ArrayList<>();

        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream data = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(data));
            int count = 0;

            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                Indonesia kamusIndonesia;

                kamusIndonesia = new Indonesia(splitstr[0], splitstr[1]);
                kamusIndonesias.add(kamusIndonesia);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kamusIndonesias;
    }

    public ArrayList<English> preLoadEnglishRaw() {
        ArrayList<English> englishes = new ArrayList<>();

        String line = null;
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream data = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(data));
            int count = 0;

            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                English english;

                english = new English(splitstr[0], splitstr[1]);
                englishes.add(english);
                count++;

            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return englishes;
    }
}
