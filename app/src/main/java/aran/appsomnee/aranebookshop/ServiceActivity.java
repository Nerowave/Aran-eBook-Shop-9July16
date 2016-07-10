package aran.appsomnee.aranebookshop;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
        }// onPost gen onPost


    } //SynProduct Calss


} // Main Class
