package com.example.peticiones.usersbasedatos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class ParkingActivity extends AppCompatActivity{

    private TextView mTxtView1;
    //private ListView mListViewP;



    //private List<String> mListaP = new ArrayList<>();
    //private ArrayAdapter<String> mAdapterP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_activity);

        mTxtView1 = findViewById(R.id.mTxtParking);
        //mTxtView1 = findViewById(R.id.mTxtView);
        //mListViewP = findViewById(R.id.mListView);
        Bundle params = getIntent().getExtras();//retorna los parametros de la actividad anterior
        int vPos = params.getInt("pos");


        ClassConnection connectionL = new ClassConnection();

        try {
            String response = connectionL.execute("https://django-parking-server.herokuapp.com/endpoints/api/location/").get();

            JSONArray jsonArrayP = new JSONArray(response);

            JSONObject jsonObjectP = jsonArrayP.getJSONObject(vPos);

            String streetName = jsonObjectP.getString("street_address").trim();
            int postalCode = jsonObjectP.getInt("postal_code");
            String cityName = jsonObjectP.getString("city").trim();
            String stateProvince = jsonObjectP.getString("state_province").trim();
            double latitude = jsonObjectP.getDouble("latitude");
            double longitude = jsonObjectP.getDouble("longitude");


            mTxtView1.setText(streetName + postalCode + cityName + stateProvince + latitude + longitude);
            /*mListaP <String> = new ArrayList<>();
            mListaP.add(streetName + postalCode + cityName + stateProvince + latitude + longitude);
            ArrayAdapter mAdapterP = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mListaP);
            mListViewP.setAdapter(mAdapterP);*/


            //Toast.makeText(ParkingActivity.this, "item Clicked: "+ vPos, Toast.LENGTH_SHORT).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
