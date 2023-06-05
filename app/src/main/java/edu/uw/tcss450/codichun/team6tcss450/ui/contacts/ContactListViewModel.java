package edu.uw.tcss450.codichun.team6tcss450.ui.contacts;

import android.app.Application;
import android.util.Log;

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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.io.RequestQueueSingleton;

public class ContactListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Contact>> mContacts;
    private final MutableLiveData<List<Contact>> mPendingList;
    private final MutableLiveData<List<Contact>> mChosenList;


    public ContactListViewModel(@NonNull Application application) {
        super(application);
        mContacts = new MutableLiveData<>();
        mContacts.setValue(new ArrayList<>());
        mPendingList = new MutableLiveData<>();
        mPendingList.setValue(new ArrayList<>());
        mChosenList = new MutableLiveData<>();
        mChosenList.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Contact>> getContactList() {
        return this.mContacts;
    }

    public MutableLiveData<List<Contact>> getChosenList() {
        return this.mChosenList;
    }

    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                       @Nullable Observer<?super List<Contact>> observer){
        mContacts.observe(owner,observer);
    }

    public void addChosenListObeserver(@NonNull LifecycleOwner owner,
                                       @Nullable Observer<?super List<Contact>> observer) {
        mChosenList.observe(owner, observer);
    }

    public void addPendingListObserver(@NonNull LifecycleOwner owner,
                                       @Nullable Observer<?super List<Contact>> observer){
        mPendingList.observe(owner,observer);
    }

    /**
     * Add to pending requests list
     * @param contact contact to be added
     */
    public void addToPendingList(Contact contact) {
        mPendingList.getValue().add(contact);
        mPendingList.setValue(mPendingList.getValue());
    }

    /**
     * reset contacts list
     */
    public void resetContacts(){
        mContacts.setValue(new ArrayList<>());
    }

    public void resetChosens() { mChosenList.setValue(new ArrayList<>());}

    /**
     * reset requests list
     */
    public void resetRequests() {
        mPendingList.setValue(new ArrayList<>());
    }




    public void connectDeleteForSelectMember(String nickname) {
        String url = getApplication().getResources().getString(R.string.chosen_delete_url) + nickname;
        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                this::handleResult,
                this::handleErrorDeleteSelect
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleErrorDeleteSelect(VolleyError volleyError) {
        Log.e("DELETEMEMBER", volleyError.getLocalizedMessage());
    }

    private void handleResult(JSONObject jsonObject) {
        JSONObject response = jsonObject;
        if (response.has("success")) {
            Log.d("DELETEMEMBER", "Delete member success");
        }
    }
    public void connectPostRequestForSelectMember(int memberid, String firstname) {
        String url = getApplication().getResources().getString(R.string.chosen_get_url) + memberid + "/";
        JSONObject body = new JSONObject();
        try {
            body.put("firstname", firstname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                this::handleResultSelect,
                this::handleErrorSelect
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleErrorSelect(VolleyError volleyError) {
        Log.e("SELECTMEMBER", volleyError.getLocalizedMessage());
    }

    private void handleResultSelect(JSONObject result) {
        JSONObject response = result;
        if (response.has("success")) {
            Log.d("SELECTMEMBER", "Select member success");
        }
    }

    public void connectGetRequest(int random) {
        String url = getApplication().getResources().getString(R.string.chosen_get_url) + random;

        Request<JSONObject> request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleResultChosen,
                this::handleErrorChosen);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    private void handleErrorChosen(VolleyError volleyError) {
        Log.e("Chosen List", volleyError.getMessage());
    }

    private void handleResultChosen(JSONObject result) {
        Log.d("GetChosenList", "Success Get Request");
        MutableLiveData<List<Contact>> list = mChosenList;
        try {
            JSONObject response = result;
            if (response.has("rows")) {
                JSONArray rows = response.getJSONArray("rows");
                List<Integer> helper = new ArrayList<>();
                helper.add(0);
                for (int mem = 0; mem < rows.length(); mem++) {
                    JSONObject chosenJson = rows.getJSONObject(mem);
                    Contact chosen = new Contact(
                            chosenJson.getString("memberid"),
                            chosenJson.getString("firstname"),
                            null,
                            null,
                            null,
                            null);

                    if(!helper.contains(Integer.parseInt(chosen.getUserID()))) {
                        list.getValue().add(chosen);
                        helper.add(Integer.parseInt(chosen.getUserID()));
                    }
                }
            } else {
                Log.e("ERROR", "No Chosen Member Exist In Server");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Chosen-HandleResult", e.getMessage());
        }
        list.setValue(list.getValue());
    }



    public void connectContacts(int memberId, String jwt, String type) {
        String url = "https://team-6-tcss-450-backend.herokuapp.com/contacts/" + memberId + "/";

        if (type.equals("requests")) {
            url += 0;
        } else {
            url += 1;
        }

        Request<JSONObject> request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                result -> handleResult(result, type),
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

    private void handleResult(final JSONObject result, String type) {
        MutableLiveData<List<Contact>> list = mContacts;
        try {
            JSONObject response = result;
            Status status = Status.FRIENDS;

            if (type.equals("requests")) {
                status = Status.RECEIVED_REQUEST;
                list = mPendingList;
            }

            if (response.has("rows")) {
                JSONArray rows = response.getJSONArray("rows");
                for (int i = 0; i< rows.length(); i++){
                    JSONObject jsonContact = rows.getJSONObject(i);
                    Contact contact = new Contact(
                            jsonContact.getString("FirstName"),
                            jsonContact.getString("LastName"),
                            jsonContact.getString("NickName"),
                            jsonContact.getString("Email"),
                            jsonContact.getString("Userid"),
                            status
                    );

                    if(!list.getValue().contains(contact))
                        list.getValue().add(contact);
                }
            } else {
                Log.e("ERROR", "No Friends Provided");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR",e.getMessage());
        }
        list.setValue(list.getValue());
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.wtf("NETWORK ERROR", error.getMessage());
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.wtf("CLIENT ERROR",
                    error.networkResponse.statusCode + " " + data);
        }
    }

    /**
     * handle error from server
     * @param error error

    private void handleError(final VolleyError error) {
        throw new IllegalStateException(error.getMessage());
    }
    */
}
