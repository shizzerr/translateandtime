package com.example.translateandtime;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;




public class MainActivity extends AppCompatActivity {

    String from,to,url,transURL,text,result;
    TextInputEditText userText;
    TextView resultText;
    //FROM
    Button buttonFromEN;
    Button buttonFromFR;
    Button buttonFromPL;
    //TO
    Button buttonToEN;
    Button buttonToFR;
    Button buttonToPL;
    //TRANSLATE
    Button translate;


    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);

        translateClick();
        switchButton();
        Translate();

        url ="https://api.mymemory.translated.net/get?";
    }

    private void translateClick() {
        buttonFromEN = findViewById(R.id.buttonEnglishFROM);
        buttonFromPL = findViewById(R.id.buttonPolishFROM);
        buttonFromFR = findViewById(R.id.buttonFranceFROM);
        buttonToEN = findViewById(R.id.buttonEnglishTO);
        buttonToPL = findViewById(R.id.buttonPolishTO);
        buttonToFR = findViewById(R.id.buttonFranceTO);
        buttonFromEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = "en";
            }
        });
        buttonFromPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = "pl";
            }
        });
        buttonFromFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from ="fr";
            }
        });
        buttonToEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to = "en";
            }
        });
        buttonToPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to = "pl";
            }
        });
        buttonToFR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to = "fr";
            }
        });
    }



    private void Translate(){
        translate =(Button) findViewById(R.id.buttonTranslate);
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userText = findViewById(R.id.textInputEditText);
                text = userText.getText().toString();
                resultText =  findViewById(R.id.textViewResult2);
                transURL = url+"q="+text+"&langpair="+from+"|"+to;
                Toast.makeText(MainActivity.this,transURL,Toast.LENGTH_LONG).show();

                JsonObjectRequest jsObjectRequest = new JsonObjectRequest(Request.Method.GET, transURL,
                        null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("matches");
                            JSONObject match = jsonArray.getJSONObject(0);
                            result = match.getString("translation");
                            resultText.setText(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Problem",error.getMessage());
                        error.printStackTrace();
                    }
                });
                Volley.newRequestQueue(getApplicationContext()).add(jsObjectRequest);

            }
        });
    }
    //switcher
    private void switchButton() {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }
}
