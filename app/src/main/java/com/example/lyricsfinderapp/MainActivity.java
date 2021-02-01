package com.example.lyricsfinderapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    EditText edtArtistName,edtSongName;
    Button btnGetLyrics;
    TextView txtLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtArtistName=findViewById(R.id.edtArtistName);
        edtSongName=findViewById(R.id.edtSongName);
        btnGetLyrics=findViewById(R.id.btnGetLyrics);
        txtLyrics=findViewById(R.id.tvLyrics);

        btnGetLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtArtistName.getText().toString().equals("") && !edtSongName.getText().toString().equals("") ) {
                    String url = "https://api.lyrics.ovh/v1/" + edtArtistName.getText().toString() + "/" + edtSongName.getText().toString();
                    url.replace(" ", "20%");
                    final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                                    String key = it.next();
                                    if (key.equals("lyrics")){
                                        txtLyrics.setText(response.getString("lyrics"));
                                    }else if (key.equals("error")){
                                        txtLyrics.setText("Lyrics not found!");
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }else{
                    Toast.makeText(MainActivity.this,"Enter required details!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
