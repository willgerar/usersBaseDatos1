package com.example.peticiones.usersbasedatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button mButton;
    private ListView mListView;
    private List<String> mLista = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mButton = findViewById(R.id.btnAgregar);
        mButton.setOnClickListener(this);
        mListView = findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
    }

    //Métodod
    @Override
    public void onClick(View v) {

        ClassConnection connection = new ClassConnection();

        switch(v.getId()){
            case R.id.btnAgregar:
                try {
                    String response = connection.execute("https://django-parking-server.herokuapp.com/endpoints/api/parking/").get();

                    //leer en formato Json
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name").trim();
                        mLista.add(name);
                        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
                        mListView.setAdapter(mAdapter);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }


    //Método
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /*Intent intent = new Intent(MainActivity1.this, ParkingActivity.class);
        intent.putExtra("pos", position);
        startActivity(intent);*/

        ClassConnection connection = new ClassConnection();

        try {
            String response = connection.execute("https://django-parking-server.herokuapp.com/endpoints/api/location/").get();

            JSONArray jsonArray1 = new JSONArray(response);

            JSONObject jsonObject = jsonArray1.getJSONObject(position);
            String streetName = jsonObject.getString("street_address").trim();
            int postalCode = jsonObject.getInt("postal_code");
            String cityName = jsonObject.getString("city").trim();
            String stateProvince = jsonObject.getString("state_province");
            double latitude = jsonObject.getDouble("latitude");
            double longitude = jsonObject.getDouble("longitude");

            mLista.add(streetName+postalCode+cityName+stateProvince+latitude+longitude);
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
            mListView.setAdapter(mAdapter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(MainActivity1.this, "item Clicked: "+ position, Toast.LENGTH_SHORT).show();
    }

}
