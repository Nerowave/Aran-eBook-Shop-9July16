package aran.appsomnee.aranebookshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends AppCompatActivity {

    //Eplicit
    private TextView textView;
    private ListView listView;
    private  String nameString, surnameString, urlJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Setup Constant
        MyConstant myConstant = new MyConstant();
        urlJSON = myConstant.getUrlJSONproduct();

        //Initial Widget
        textView = (TextView) findViewById(R.id.textView11);
        listView = (ListView) findViewById(R.id.listView); // alt + enter gen

        //Show View
        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");
        textView.setText("Welcome " + nameString + " " + surnameString);

        //Syn And Create Listview
        SynProduct synProduct = new SynProduct(this, listView, urlJSON);
        synProduct.execute();

    } // Main Method

    private class SynProduct extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private String myURL;
        private ListView myListView;
        private String[] bookStrings, priceStrings, iconStrings;

        public SynProduct(Context context,
                          ListView myListView,
                          String myURL) { //Comand + N gen con
            this.context = context;
            this.myListView = myListView;
            this.myURL = myURL;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("ShopV2","e doInBack ==>");
                return null;
            }

        } //doInback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ShopV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                bookStrings = new String[jsonArray.length()];
                priceStrings = new String[jsonArray.length()];
                iconStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i +=1) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    bookStrings[i] = jsonObject.getString("Name");
                    priceStrings[i] = jsonObject.getString("Price");
                    iconStrings[i] = jsonObject.getString("Cover");

                } //for

                MyAdapter myAdapter = new MyAdapter(bookStrings, context, iconStrings, priceStrings);
                myListView.setAdapter(myAdapter);

                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(ServiceActivity.this, DetailActivity.class);
                        intent.putExtra("NameLogin", nameString);
                        intent.putExtra("SurnameLogin", surnameString);
                        intent.putExtra("Book", bookStrings[i]);
                        intent.putExtra("Price", priceStrings[i]);
                        intent.putExtra("Icon", iconStrings[i]);
                        startActivity(intent);


                    } // onItemClick
                });

            } catch (Exception e) {
                Log.d("ShopV2", "e onPost ==> " + e.toString());
            }

        }// onPost gen onPost


    } //SynProduct Calss


} // Main Class
