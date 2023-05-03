package edu.uw.tcss450.codichun.team6tcss450.JD.weather;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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
    private MutableLiveData<List<WeatherData>> mBlogList;
    public WeatherListViewModel(@NonNull Application application) {
        super(application);
        mBlogList = new MutableLiveData<>();
        mBlogList.setValue(new ArrayList<>());
    }
    public void addBlogListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<WeatherData>> observer) {
        mBlogList.observe(owner, observer);
    }
    private void handleError(final VolleyError error) {
//you should add much better error handling in a production release.
//i.e. YOUR PROJECT
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            if (root.has(getString.apply(R.string.keys_json_weather_temp))) {
                JSONObject temp =
                        root.getJSONObject(getString.apply(
                                R.string.keys_json_weather_temp));
                if (temp.has(getString.apply(R.string.keys_json_weather_day))) {
                    JSONArray data = temp.getJSONArray(
                            getString.apply(R.string.keys_json_weather_day));
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonBlog = data.getJSONObject(i);
                        WeatherData post = new WeatherData.Builder(
                                jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_weather_day)),
                                jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_weather_temp)),
                                jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_weather_day)),
                                jsonBlog.getString(
                                        getString.apply(
                                                R.string.keys_json_weather_city)))
                                .build();
                        if (!mBlogList.getValue().contains(post)) {
                            mBlogList.getValue().add(post);
                        }
                    }
                } else {
                    Log.e("ERROR!", "No data array");
                }
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mBlogList.setValue(mBlogList.getValue());
    }
    public void connectGet() {
        String url =
                "https://cfb3-tcss450-labs-2021sp.herokuapp.com/phish/blog/get";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
// add headers <key,value>
                headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFp" +
                        "bCI6ImNiQHV3LmVkdSIsIm1lbWJlcmlkIjoxLCJpYXQiOjE2ODExNTc1NzUsImV4c" +
                        "CI6MTY4OTc5NzU3NX0.ntXQOZZ0n1RhUHWwEnXD73JccDwtHZVrWnGwa5seDEA");
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
