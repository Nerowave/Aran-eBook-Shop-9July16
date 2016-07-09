package aran.appsomnee.aranebookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    //Explicit นี่คือการประกาศตัวแปร
    private EditText nameEditText, surnameEditText, userEditText,
            passwordEditText;
    private String nameString, surnameString,
            userString, passwordString;
    private static final String urlPHP = "http://swiftcodingthai.com/9july/add_user_aran.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //Bind of Initial Widget คือการผูกตัวแปป กับ Widget บน Activity
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);

    } // Main Method

    public void clickSignUpSign(View view) {


        //Get Value From EditText
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (checkSpace()) {
            //True Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกทุกช่องด้วย คะ");

        } else {
            //False No Space

            updateNewUserToServer();

        } // if


    } //clickSign

    private void updateNewUserToServer() {
        //Control space command shift enter
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("User", userString)
                .add("Password", passwordString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });
    } // update

    private boolean checkSpace() {

        boolean status = false;

        status = nameString.equals("") || surnameString.equals("") ||
                userString.equals("") || passwordString.equals("");

        return status;
    }


} // Main Class
