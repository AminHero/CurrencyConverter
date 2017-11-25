package wi.team.hsh.currencyconverter;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by amin on 22.10.17.
 */

public class ConnectionAsyncTask extends AsyncTask<String, Void, JSONArray> {

    //Methode kümmert sich um Verbindungsaufbau und laden der Daten in JSON Format
    @Override
    protected JSONArray doInBackground(String... params) {

        URL url = null;
        JSONArray jsonArray = null;
        try {
            String string = "https://api.coinmarketcap.com/v1/ticker/?convert=EUR&limit=10";
            url = new URL(string);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            //Ergebnisse von der Schnittstelle lesen und "speichern"
            if (responseCode == 200){ //code 200 = Verbidnung in Ordnung!
                InputStream inputStream = new BufferedInputStream(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputString);
                }

                jsonArray = new JSONArray(stringBuilder.toString()); // Ergebnis als JSON objekt speichern
                con.disconnect(); //Verbindung zu der Seite trennen/schließen um Speicher zu sparen
            }else {
                //TODO check why responseCode != 200;
            }

            //Exceptions falls etwas schief geht
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    //übergibt das ERgebnis an MainActivity
    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
    }
}
