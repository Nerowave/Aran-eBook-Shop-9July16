package aran.appsomnee.aranebookshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText,passwordEditText;
    private String userString,passwordString;
    private String urlJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Wdget
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

        MyConstant myConstant = new MyConstant();
        urlJSON = myConstant.getUrlJSONuser();

    } // Main Method

    private class SynUserTABLE extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private String myURL, myUserString, myPasswordString,
                truePasswordString, loginNameString, loginSurnameString;
        private boolean statusABoolean = true;

        public SynUserTABLE(Context context,
                            String myPasswordString,
                            String myURL,
                            String myUserString) {
            this.context = context;
            this.myPasswordString = myPasswordString;
            this.myURL = myURL;
            this.myUserString = myUserString;
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
                Log.d("ShopV1", "e doInBack ==> " + e.toString());//ดู log
                return null;
            }
        } // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ShopV1", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i +=1) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (myUserString.equals(jsonObject.getString("User"))) {

                        statusABoolean = false;
                        truePasswordString = jsonObject.getString("Password");
                        loginNameString = jsonObject.getString("Name");
                        loginSurnameString = jsonObject.getString("Surname");

                    }
                }  //for

                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "ไม่มี User นี้",
                            "ไม่มี" + myUserString + "ในฐานข้อมูลเรา");
                } else if (myPasswordString.equals(truePasswordString)) {

                    Toast.makeText(context, "Welcome " + loginNameString + " " + loginSurnameString, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                    intent.putExtra("Name", loginNameString);
                    intent.putExtra("Surname", loginSurnameString);
                    startActivity(intent);
                    finish();

                } else {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "Password False",
                            "Please Try Again Password False");
                }


            } catch (Exception e) {
                Log.d("ShopV1","e OnPost ==>" + e.toString());
            }

        } //onPost


    } // SybUser Class


    public void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "Have Space", "Please Fill All Every Blank");

        } else {
            //No Space
            SynUserTABLE synUserTABLE = new SynUserTABLE(this,
                    passwordString, urlJSON, userString);
            synUserTABLE.execute();

        }

    } //clickSignIn เลือกที่ xml ตรงที่ต้องการแล้วใช้ onclick

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));

    }

} // Main Class
