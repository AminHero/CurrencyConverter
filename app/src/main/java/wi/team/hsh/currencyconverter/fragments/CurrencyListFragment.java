package wi.team.hsh.currencyconverter.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wi.team.hsh.currencyconverter.DataManagement;
import wi.team.hsh.currencyconverter.R;
import wi.team.hsh.currencyconverter.recycleView.RecycleViewAdapter;

/**
 * Created by amin on 31.10.17.
 */

public class CurrencyListFragment extends Fragment {

    private RecyclerView recyclerView; //Dynamisches Listen-Layout für Darstellung der Währung
    private RecycleViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_currencylist, container, false);

        //Recycleview für die effiziente Darstellung der Liste
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        //LayoutManager kümmert sich um die Darstellung in Recyclerview
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        //gespeicherte Daten von SharedPreferences laden - könnte auch Daten wieder aus Internet laden falls notwendig
        //geht so schneller, effizienter
        getDataFromSharedPref();

        //Adapter um einzelne Elemente in die Liste hinzuzufügen
        adapter = new RecycleViewAdapter(getContext(), getDataFromSharedPref());
        recyclerView.setAdapter(adapter);

        return rootView;

    }



    public List<DataManagement> getDataFromSharedPref() {

        //daten von SharedPref laden und in JSON konvertieren für schnell Verarbeitung.
        JSONArray currencyData = null;
        String preferences = PreferenceManager.
                getDefaultSharedPreferences(getActivity()).getString("theJson", "");
        List<DataManagement> data = new ArrayList<>();


        try {
            currencyData = new JSONArray(preferences);
            String dataToString = currencyData.toString();
            JSONArray coinMarketcapDataArray = new JSONArray(dataToString);

            //iteriere json array und speicher ausgewählte Elemente in bestimmte Variablen. zB. Name, Rank oder Symbol
            for (int i = 0; i < coinMarketcapDataArray.length(); i++) {
                DataManagement current = new DataManagement();

                //get single row as json object
                JSONObject jsonRow = coinMarketcapDataArray.getJSONObject(i);
                String name = jsonRow.getString("name");
                current.title = name;

                //Description Text in RecycleView
                String symbol = jsonRow.getString("symbol");
                String rank = jsonRow.getString("rank");
                String total_supply = jsonRow.getString("total_supply");
                String price_usd = jsonRow.getString("price_usd");

                String descriptionText = name + "s symbol is " + symbol + " and ranks at nr " + rank + "." +
                        " It has a total supply of " + total_supply + ". The  price for in USD is currently " + price_usd + ".";
                current.tvDescription = descriptionText;
                //transfer name to resource file
                name = name.trim().replaceAll(" +", "_");
                name = "logo_" + name;
                current.iconId = getResources().getIdentifier(name.toLowerCase(), "drawable", getContext().getPackageName());
                data.add(current);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }


}
