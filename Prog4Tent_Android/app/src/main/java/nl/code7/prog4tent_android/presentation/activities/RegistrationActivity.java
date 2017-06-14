package nl.code7.prog4tent_android.presentation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import nl.code7.prog4tent_android.R;
import nl.code7.prog4tent_android.presentation.fragments.FilmFragment;

public class RegistrationActivity extends AppCompatActivity {

    // UI references.
    private EditText emailView, passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Login form
        emailView = (EditText) findViewById(R.id.new_email_EditText);
        passwordView = (EditText) findViewById(R.id.new_password_EditText);

        Button make_account_Btn = (Button) findViewById(R.id.complete_registration_Button);
        make_account_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();

                volleyRegistration(email, password);
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);


            }
        });


    }

    public void volleyRegistration(String u, String p) {

        final String email = u;
        final String password = p;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //USE 10.0.2.2 INSTEAD OF localhost IF USING AN EMULATOR
        String url = "http://10.0.2.2:8080/api/v1/register";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //TODO: Rework if statement
                        if (!response.contains("error") && !response.isEmpty()){
                            //Log.i(TAG, "Response: " + response);

                            //Start activity and put response/token in extras
                            Intent i = new Intent(getApplicationContext(), FilmFragment.class);
                            i.putExtra("EMAIL", emailView.getEditableText().toString());
                            String token = response.replaceAll("^\"|\"$", "");
                            i.putExtra("TOKEN", token);
                            startActivity(i);
                        } else {
                            //Log.e(TAG, "Response: " + response);
                        }

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG, "Something went wrong.");
                    }
                }) {

            //Set headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            //Set content-type of the body
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            //Set the body of the request
            @Override
            public byte[] getBody() throws AuthFailureError {

                String mContent = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
                byte[] body = new byte[0];
                try {
                    body = mContent.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    //Log.e(TAG, "Unable to gets bytes from JSON", e.fillInStackTrace());
                }
                return body;
            }

        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
