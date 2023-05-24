package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class WeatherListViewModel extends AndroidViewModel {
    private MutableLiveData<List<WeatherData>> mWeatherList;
    private MutableLiveData<List<WeatherData>> mWeatherListHourly;
    private MutableLiveData<WeatherData> mCurrentData;


    public WeatherListViewModel(@NonNull Application application) {
        super(application);
        mWeatherList = new MutableLiveData<>();
        mWeatherList.setValue(new ArrayList<>());
        mWeatherListHourly = new MutableLiveData<>();
        mWeatherListHourly.setValue(new ArrayList<>());
        mCurrentData = new MutableLiveData<>();
        mCurrentData.setValue(new WeatherData());
    }
    public void addWeatherListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<WeatherData>> observer) {
        mWeatherList.observe(owner, observer);
    }
    public void addWeatherListObserverHourly(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super List<WeatherData>> observer) {
        mWeatherListHourly.observe(owner, observer);
    }
    public void addWeatherListObserverCurrent(@NonNull LifecycleOwner owner,
                                             @NonNull Observer<? super WeatherData> observer) {
        mCurrentData.observe(owner, observer);
    }
    public void connectGetCurrent(final String jwt,final String query){
        String url =
                getApplication().getResources().getString(R.string.base_url) + "weather";
        JSONObject body = new JSONObject();
        try {
            if (Character.isDigit(query.charAt(0))){
                body.put("zipcode",query);
                body.put("city","NA");
                body.put("latlong","NA");
            }else {
                body.put("city", query);
                body.put("zipcode","NA");
                body.put("latlong","NA");
            }
            body.put("forecast", "current");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            JSONObject response = result.getJSONArray("data").getJSONObject(0);
                            JSONObject weather = response.getJSONObject("weather");
                            String city = response.getString("city_name");
                            String wind = response.getString("wind_spd");
                            String temp = response.getString("temp");
                            String description = weather.getString("description");
                            String time = response.getString("ob_time");
                            String icon = weather.getString("icon");
                            WeatherData temporary = new WeatherData.Builder("none",temp,wind,city,time,description,icon).build();
                            if (!mCurrentData.getValue().equals(temporary)) {
                                mCurrentData.setValue(temporary);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("ERROR!", e.getMessage());
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        }){
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
    public void connectGetDaily(String jwt,String query) {
        mWeatherList.getValue().clear();
        String url =
                getApplication().getResources().getString(R.string.base_url) + "weather";
        JSONObject body = new JSONObject();
        try {
            if (Character.isDigit(query.charAt(0))){
                body.put("zipcode",query);
                body.put("city","NA");
                body.put("latlong","NA");
            }else {
                body.put("city", query);
                body.put("zipcode","NA");
                body.put("latlong","NA");
            }
            body.put("forecast", "daily");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        JSONObject response = result;
                        try {
                            JSONArray days = response.getJSONArray("data");
                            String city = response.getString("city_name");
                            for(int i = 0; i < days.length() ; i++){
                                JSONObject temp = days.getJSONObject(i);
                                JSONObject weather = temp.getJSONObject("weather");
                                String day = temp.getString("valid_date");
                                String description = weather.getString("description");
                                String icon = weather.getString("icon");
                                String wind = temp.getString("wind_spd");
                                String tempData = temp.getString("temp");
                                WeatherData OneDay = new WeatherData.Builder(day,tempData,wind,city,"NA",description,icon).build();
                                if (!mWeatherList.getValue().contains(OneDay)) {
                                    mWeatherList.getValue().add(OneDay);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("ERROR Daily!", e.getMessage());
                        }
                        mWeatherList.setValue(mWeatherList.getValue());
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        }){
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
    public void connectGetHourly(String jwt,String query) {
        mWeatherListHourly.getValue().clear();
        String url =
                getApplication().getResources().getString(R.string.base_url) + "weather";
        JSONObject body = new JSONObject();
        try {
            if (Character.isDigit(query.charAt(0))){
                body.put("zipcode",query);
                body.put("city","NA");
                body.put("latlong","NA");
            }else {
                body.put("city", query);
                body.put("zipcode","NA");
                body.put("latlong","NA");
            }
            body.put("forecast", "hourly");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        JSONObject response = result;
                        try {
                            JSONArray days = response.getJSONArray("data");
                            String city = response.getString("city_name");
                            for(int i = 0; i < days.length() ; i++){
                                JSONObject temp = days.getJSONObject(i);
                                JSONObject weather = temp.getJSONObject("weather");
                                String description = weather.getString("description");
                                String icon = weather.getString("icon");
                                String wind = temp.getString("wind_spd");
                                String tempData = temp.getString("temp");
                                String time = temp.getString("timestamp_local");
                                WeatherData OneDay = new WeatherData.Builder("NA",tempData,wind,city,time,description,icon).build();
                                if (!mWeatherListHourly.getValue().contains(OneDay)) {
                                    mWeatherListHourly.getValue().add(OneDay);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("ERROR Hourly!", e.getMessage());
                        }
                        mWeatherListHourly.setValue(mWeatherListHourly.getValue());
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
            }
        }){
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
}
