package nl.code7.prog4tent_android.presentation.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.code7.prog4tent_android.R;
import nl.code7.prog4tent_android.adapter.RentalAdapter;
import nl.code7.prog4tent_android.domain.Customer;
import nl.code7.prog4tent_android.domain.Inventory;
import nl.code7.prog4tent_android.domain.Rental;


public class RentalFragment extends Fragment {
    private static final String TAG = RentalFragment.class.getName();

    private ListView rentalListView;
    private ArrayList<Rental> rentalList;
    private RentalAdapter rentalAdapter;

    private Customer customer;
    private String token;


    public RentalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customer = (Customer) getActivity().getIntent().getSerializableExtra("CUSTOMER");
        token = getActivity().getIntent().getExtras().getString("TOKEN");
        View rootView = inflater.inflate(R.layout.fragment_rental, container, false);

        rentalList = new ArrayList<>();
        rentalListView = (ListView) rootView.findViewById(R.id.rental_ListView);
        rentalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Do you want to return the film?");
                alert.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                volleyReturn(rentalList.get(position));
                            }
                        });
                alert.show();
            }
        });
        rentalAdapter = new RentalAdapter(getContext(), rentalList);
        rentalListView.setAdapter(rentalAdapter);
        volleyRentals();
        return rootView;
    }
    public void volleyRentals() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        //USE 10.0.2.2 INSTEAD OF localhost IF USING AN EMULATOR
        String url = "https://tentprog4.herokuapp.com/api/v1/rentals/" + customer.getCustomer_id() + "/" + "?current_rentals=true";

        Log.d(TAG, customer.getCustomer_id() + "");

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        rentalList.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Rental rental = new Rental();
                                rental.setRental_id(obj.getString("rental_id"));
                                rental.setTitle(obj.getString("title"));
                                rental.setRental_date(obj.getString("rental_date"));
                                rental.setInventory_id(obj.getInt("inventory_id"));
                                rental.setCustomer_id(obj.getInt("customer_id"));
                                rental.setReturn_date(obj.getString("return_date"));
                                rental.setStaff_id(obj.getInt("staff_id"));
                                rental.setLast_update(obj.getString("last_update"));
                                rental.setRental_duration(obj.getString("rental_duration"));
                                rentalList.add(rental);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rentalAdapter.notifyDataSetChanged();
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Something went wrong.");
                    }
                }) {

            //Set headers and provide X-Access-Token generated during login.
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                String token = getActivity().getIntent().getExtras().getString("TOKEN");
                headers.put("X-Access-Token", token);
                return headers;
            }

        };

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

    }

    public void volleyReturn(Rental rental) {

        int inventory_id = rental.getInventory_id();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //USE 10.0.2.2 INSTEAD OF localhost IF USING AN EMULATOR
        String url = "http://tentprog4.herokuapp.com/api/v1/rentals/" + customer.getCustomer_id() + "/" + inventory_id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        volleyRentals();
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Something went wrong.");
                    }
                }) {

            //Set headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("X-Access-Token", token);
                return headers;
            }

        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
