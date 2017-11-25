package wi.team.hsh.currencyconverter.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import wi.team.hsh.currencyconverter.R;

/**
 * Created by amin on 31.10.17.
 */

public class CurrencyConverterFragment extends Fragment  {

    public static final String ARG_POSITION = "";
    private Button btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    private Button btnCancel, btnConvert;
    private TextView currencyOneTextView, currencyTwoTextView;
    private String selectedInputCrpto = "";
    private String selectedOutput = "";
    private HashMap<String, Double> pricesHashMap;
    private double current_eur_price = 0.85730;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_currency_converter, container, false);
        initViewItems(rootView);
        return rootView;
    }

    private void initViewItems(View rootView) {
        //Spinner hinzufügen
        setupSpinner(rootView);

        //BUTTONS hinzufügen
        btnConvert = (Button) rootView.findViewById(R.id.btn_convert);
        btnConvert.setOnClickListener(btnOnclickLister);
        btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(btnOnclickLister);
        btnZero = (Button) rootView.findViewById(R.id.btn_zero);
        btnZero.setOnClickListener(btnOnclickLister);
        btnOne = (Button) rootView.findViewById(R.id.btnOne);
        btnOne.setOnClickListener(btnOnclickLister);
        btnTwo = (Button) rootView.findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(btnOnclickLister);
        btnThree = (Button) rootView.findViewById(R.id.btnThree);
        btnThree.setOnClickListener(btnOnclickLister);
        btnFour = (Button) rootView.findViewById(R.id.btnFour);
        btnFour.setOnClickListener(btnOnclickLister);
        btnFive = (Button) rootView.findViewById(R.id.btnFive);
        btnFive.setOnClickListener(btnOnclickLister);
        btnSix = (Button) rootView.findViewById(R.id.btnSix);
        btnSix.setOnClickListener(btnOnclickLister);
        btnSeven = (Button) rootView.findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(btnOnclickLister);
        btnEight = (Button) rootView.findViewById(R.id.btnEight);
        btnEight.setOnClickListener(btnOnclickLister);
        btnNine = (Button) rootView.findViewById(R.id.btnNine);
        btnNine.setOnClickListener(btnOnclickLister);

        //TEXTVIEWS hinzufügen
        currencyOneTextView = (TextView) rootView.findViewById(R.id.tvOne);
        currencyTwoTextView = (TextView) rootView.findViewById(R.id.tvTwo);
        currencyOneTextView.setText("");
        currencyTwoTextView.setText("");

        //Preise von SharedPref holen und abspeichern damit umgerechnet werden kann
        setPriceDataFromSharedPreferences();

    }

    private void setupSpinner(View rootView) {

        //Spinner
        Spinner real_currency_spinner = (Spinner) rootView.findViewById(R.id.real_currency_spinner);
        Spinner crypto_currency_spinner = (Spinner) rootView.findViewById(R.id.crypto_currency_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> real_currency_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.real_currency_spinner_items, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> crypto_currency_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.crypto_currency_spinner_items, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        real_currency_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crypto_currency_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        real_currency_spinner.setAdapter(real_currency_adapter);
        crypto_currency_spinner.setAdapter(crypto_currency_adapter);

        //onItemSelectedListener
        real_currency_spinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);
        crypto_currency_spinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);

    }


    AdapterView.OnItemSelectedListener spinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            //parent.getItemAtPosition(position);

            //zwei Spinner, eins für Krypto und eins für normale Währung
            Spinner spinner = (Spinner) parent;
            if(spinner.getId() == R.id.crypto_currency_spinner)
            {
                selectedInputCrpto = parent.getItemAtPosition(position).toString();
            }
            else if(spinner.getId() == R.id.real_currency_spinner){
                selectedOutput = parent.getItemAtPosition(position).toString();
            }else {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener btnOnclickLister = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //Ereignisse, wenn auf bestimmte Buttongs geklickt wird
            switch (v.getId()) {
                case R.id.btn_zero:
                    currencyOneTextView.append("0");
                    break;
                case R.id.btnOne:
                    currencyOneTextView.append("1");
                    break;
                case R.id.btnTwo:
                    currencyOneTextView.append("2");
                    break;
                case R.id.btnThree:
                    currencyOneTextView.append("3");
                    break;
                case R.id.btnFour:
                    currencyOneTextView.append("4");
                    break;
                case R.id.btnFive:
                    currencyOneTextView.append("5");
                    break;
                case R.id.btnSix:
                    currencyOneTextView.append("6");
                    break;
                case R.id.btnSeven:
                    currencyOneTextView.append("7");
                    break;
                case R.id.btnEight:
                    currencyOneTextView.append("8");
                    break;
                case R.id.btnNine:
                    currencyOneTextView.append("9");
                    break;
                case R.id.btn_cancel:
                    currencyOneTextView.setText("");
                    break;
                //Konvertiert die Währungen
                case R.id.btn_convert:
                    String inputValue = currencyOneTextView.getText().toString(); // eingegebenen Text abgreifen und als Variable speichern
                    convert(inputValue,selectedInputCrpto, selectedOutput); //konvertieren
                    break;
            }
        }
    };

    private void convert(String inputValue, String selectedCrpto, String selectedOutput) {


        //prüfe ob Preiswerte vorhanden sind um Fehler zu vermeiden
            if(pricesHashMap.get(selectedCrpto)!= null){
                double  price = pricesHashMap.get(selectedCrpto);
                double price_usd = price * Double.parseDouble(inputValue);
                if(selectedOutput.equals("EUR")){
                    double price_eur = price_usd/ current_eur_price;
                    currencyTwoTextView.setText(""+price_eur);
                }else {
                    currencyTwoTextView.setText(""+price_usd);
                }
            }else {
                Toast.makeText(getActivity(), "NULL PRICE", Toast.LENGTH_SHORT).show();
            }

    }

    //
    private void setPriceDataFromSharedPreferences(){
        JSONArray currencyData = null;
        String preferences = PreferenceManager.
                getDefaultSharedPreferences(getActivity()).getString("theJson", "");
        pricesHashMap= new HashMap<>();


        try {
            currencyData = new JSONArray(preferences);
            String dataToString = currencyData.toString();
            JSONArray coinMarketcapDataArray = new JSONArray(dataToString);

            //iterate over josn array
            for (int i = 0; i < coinMarketcapDataArray.length(); i++) {
                //get single row as json object
                JSONObject jsonRow = coinMarketcapDataArray.getJSONObject(i);

                //Symbol and Price
                String symbol = jsonRow.getString("symbol");
                Double price_usd = Double.parseDouble(jsonRow.getString("price_usd"));
                pricesHashMap.put(symbol,price_usd);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
