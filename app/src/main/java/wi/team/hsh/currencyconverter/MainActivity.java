package wi.team.hsh.currencyconverter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import wi.team.hsh.currencyconverter.fragments.CurrencyConverterFragment;
import wi.team.hsh.currencyconverter.fragments.CurrencyListFragment;
import wi.team.hsh.currencyconverter.fragments.HomeFragment;

//HAUPTKLASSE DER ANWENDUNG
public class MainActivity extends AppCompatActivity {

    ConnectionAsyncTask asyncTask = null; //
    JSONArray currencyData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Konfiguration der BOTTOM NAVIGATION
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // überprüfe ob der fragment_container FrameLayout bereits verwendet wird
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // Neues Fragment erstellen und in ActivityLayout übergeben
            HomeFragment homeFragment = new HomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            homeFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment).commit();
        }

        //Wenn App mit dem Internet verbunden ist, Daten laden
        if(checkNetworkInfo()){
            executeAsyncTask();
        }else {
            Toast.makeText(this, "Bitte Internetverbindung überprüfen", Toast.LENGTH_SHORT).show();
        }

    }

    /** Verbindung prüfen
     uses permission in androidManifest hinzufügen */
    private boolean checkNetworkInfo() {

        ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        boolean isConnected = false;

        // Hinweis ob Verbindung vorhanden ist oder nicht
        //falls vorhanden verbinden
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            //TODO toast oder Snackbar?
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Not Connected, check your connection", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        changeFagment("Home");
                        return true;
                    case R.id.navigation_currency_list:
                        changeFagment("Currencies");
                        return true;
                    case R.id.navigation_converter:
                        changeFagment("Converter");
                        return true;
                }
                return false;
        }
    };

    private void changeFagment(String fragmentName) {
        Bundle args = new Bundle();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Create fragment and give it an argument specifying the article it should show
        switch (fragmentName){
            case "Home":
                HomeFragment homeFragment = new HomeFragment();
                //args.putInt(CurrencyConverterFragment.ARG_POSITION, position);
                homeFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, homeFragment);
                break ;
            case "Currencies":
                CurrencyListFragment listFragment = new CurrencyListFragment();

                //args.putInt(CurrencyConverterFragment.ARG_POSITION, position);
                listFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, listFragment);
                break ;
            case "Converter":
                CurrencyConverterFragment converterFragment = new CurrencyConverterFragment();
                //args.putInt(CurrencyConverterFragment.ARG_POSITION, position);
                converterFragment.setArguments(args);
                transaction.replace(R.id.fragment_container, converterFragment);
                break;
        }

        //back stack so the user can navigate back
        transaction.addToBackStack(null);
        transaction.commit();

    }

/** AsyncTask erlaubt es Anfragen im Hintergrund auszuführen*/
    private void executeAsyncTask() {

        // AsyncTask Objekt erzeugen
        // Ergebnis wird über Methode onPostExecute unter Variable asyncTask gespeichert
        asyncTask = new ConnectionAsyncTask();
        try {
            currencyData = asyncTask.execute("").get(); //AsyncTask ausführen
            if (currencyData!=null) { //speicherung der geladenen Daten für offline ausführen
                //TODO Progressbar
                saveDataToSharedPrefe(currencyData);
            }else{
            //ELSE vielleicht Daten irgendwo local speichern und immer abgreifen
            };

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    //Speichert daten für spätere oder offline Zugriff
    private void saveDataToSharedPrefe(JSONArray currencyData) {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString("theJson",currencyData.toString()).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO nicht zwingend notwendig
        //PreferenceManager.getDefaultSharedPreferences(this).edit().clear();
    }
}
