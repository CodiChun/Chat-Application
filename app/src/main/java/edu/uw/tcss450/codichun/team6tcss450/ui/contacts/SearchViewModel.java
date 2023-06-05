package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import edu.uw.tcss450.codichun.team6tcss450.MainActivity;

public class SearchViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Contact>> mSearchList;

    /**
     * Constructor
     * @param application current application
     */
    public SearchViewModel(@NonNull Application application) {
        super(application);
        mSearchList = new MutableLiveData<>();
        mSearchList.setValue(new ArrayList<>());
    }

    /**
     * Add observer for the list of results after Search People
     * @param owner owner
     * @param observer observer
     */
    public void addSearchListObserver(@NonNull LifecycleOwner owner,
                                      @Nullable Observer<?super List<Contact>> observer){
        mSearchList.observe(owner,observer);
    }

    /**
     * Reset the searched list
     */
    public void resetSearchResults() {
        mSearchList.setValue(new ArrayList<>());
    }

    /**
     * connect server to search people
     * @param jwt jwt of the user
     * @param searched nickname/firstname/lastname/email
     */
    public void connectSearch(String jwt, String searched) {
        resetSearchResults();
        String url = "https://team-6-tcss-450-backend.herokuapp.com/" + "search/" + searched;
        Request<JSONObject> request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleResult,
                this::handleError) {
            @Override
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

    /**
     * Handle error from the server
     * @param error error from the server
     */
    private void handleError(final VolleyError error) {
        Log.e("ERROR", error.getLocalizedMessage());
    }

    /**
     * Handle result from the server
     * @param result result from the server
     */
    private void handleResult(final JSONObject result) {
        try {
            JSONObject response = result;
            Status status = Status.NOT_FRIENDS;

            if (response.has("rows")) {
                JSONArray rows = response.getJSONArray("rows");
                for (int i = 0; i< rows.length(); i++){
                    JSONObject jsonContact = rows.getJSONObject(i);
                    Contact contact = new Contact(
                            jsonContact.getString("id"),
                            jsonContact.getString("nickname"),
                            jsonContact.getString("firstname"),
                            jsonContact.getString("lastname"),
                            jsonContact.getString("email"),
                            status
                    );

                    if(!mSearchList.getValue().contains(contact))
                        mSearchList.getValue().add(contact);
                }
            } else {
                Log.e("ERROR", "No Friends Provided");
                Toast.makeText(MainActivity.getActivity(),
                        "No users Provided!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR",e.getMessage());
        }
        mSearchList.setValue(mSearchList.getValue());
    }
}
