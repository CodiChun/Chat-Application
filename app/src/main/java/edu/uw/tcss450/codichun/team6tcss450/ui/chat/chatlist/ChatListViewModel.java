package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

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
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom.ChatMessage;


/**
 * The view model of chat room list
 * @author codichun
 * @version 1.0
 */
public class ChatListViewModel extends AndroidViewModel {

    private MutableLiveData<List<ChatRow>> rows;
    private Map<Integer, MutableLiveData<List<ChatRow>>> mRows;
    List<ChatRow> updatedChatRooms = new ArrayList<>();

    final String END_POINT = "chatroom";
    int HARD_CODED_PROFILE = R.drawable.image_chatlist_profile_32dp;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        rows = new MutableLiveData<>(new ArrayList<>());
        mRows = new HashMap<>();
        //addChatRow(new ChatRow("Global Chat", new ArrayList<>(Arrays.asList(14, 15, 16)),1, R.drawable.image_chatlist_profile_32dp));
        //addChatRow(new ChatRow("testroom", new ArrayList<>(Arrays.asList(14, 15)),2, R.drawable.image_chatlist_profile_32dp));
    }

    /**
     * Get the current chat items on the recycler view
     * @return the current chat items on the recycler view
     */
    public LiveData<List<ChatRow>> getChatRows() {
        return rows;
    }

    /**
     * Add a new chat row
     * @param theChatRow
     */
    public void addChatRow(ChatRow theChatRow) {
        List<ChatRow> currentList = rows.getValue();
        if (currentList != null) {
            currentList.add(0, theChatRow);
            rows.setValue(currentList);
        }
    }

    /**
     * Create a new chat room on the list
     * @param roomName
     * @param memberId
     * @param jwt
     * @param callback
     */
    public void createNewChatRoom(final String roomName, final List<Integer> memberId, final String jwt, ChatRoomCreationCallback callback) {
        String url = getApplication().getResources().getString(R.string.base_url) + END_POINT;

        JSONObject body = new JSONObject();
        try {
            body.put("name", roomName);
            body.put("memberId", memberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                response -> handleCreateNewChatRoomSuccess(response, callback, memberId, jwt),
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

        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }


    /**
     * Handle success when create a new chat room
     * @param response
     * @param callback
     * @param memberId
     * @param jwt
     */

    private void handleCreateNewChatRoomSuccess(final JSONObject response, ChatRoomCreationCallback callback,  final List<Integer> memberId, final String jwt) {
        try {
            if (response.has("chatID")) {
                int chatId = response.getInt("chatID");
                callback.onChatRoomCreated(chatId);
                addMemberToChat(chatId, memberId, jwt);
                //addChatRow("New Room", new ArrayList<>(), chatId, R.drawable.image_chatlist_profile_32dp);
            } else {
                Log.e("JSON PARSE ERROR", "No chatId found in response");
            }
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handleCreateNewChatRoomSuccess");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Add a member to a chat room
     * @param chatId
     * @param memberIds
     * @param jwt
     */
    private void addMemberToChat(int chatId, List<Integer> memberIds, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + END_POINT + "/" + chatId;

        JSONArray jsonMembersArray = new JSONArray(memberIds);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("memberIds", jsonMembersArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(memberIds.toString() + "is(are) added to " + chatId);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        error.printStackTrace();
                        System.out.println(error.getMessage());
                    }
                })
                         {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);

    }

    /**
     * Remove a member from a chat room
     * @param chatId
     * @param email
     * @param jwt
     */
    public void removeMemberFromChat(int chatId, String email, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + END_POINT + "/" + chatId + "/" + email;

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(email + " has left the group " + chatId);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + jwt);
                return headers;
            }
        };
        // Add JsonObjectRequest to the RequestQueue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(stringRequest);
    }

    /**
     * Load all the chat rooms for the member
     * @param memberId
     * @param jwt
     */
    public void loadChats(int memberId, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "member/" + memberId;
        System.out.println("End point for chat list: " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<ChatRow> updatedChatRooms = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject chatObject = response.getJSONObject(i);
                                int chatId = chatObject.getInt("chatid");
                                String name = chatObject.getString("name");
                                ChatRow chatRow = new ChatRow(name, chatId, HARD_CODED_PROFILE);

                                // Check if this ChatRow is already present in the list
                                if (!rows.getValue().contains(chatRow)) {
                                    System.out.println("new chat room: " + chatId + ", name");
                                    addChatRow(chatRow);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // Update RecyclerView adapter here with the new chat list
                        rows.postValue(rows.getValue());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Error! Could not load chats: " + error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + jwt);
                return headers;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(jsonArrayRequest);
    }


    /**
     * Return a reference to the List<> associated with the chat room. If the View Model does
     * not have a mapping for this chatID, it will be created.
     *
     * WARNING: While this method returns a reference to a mutable list, it should not be
     * mutated externally in client code. Use public methods available in this class as
     * needed.
     *
     * @param theMemberId the id of the chat room List to retrieve
     * @return a reference to the list of messages
     */
    public List<ChatRow> getChatRoomListByMemberId(final int theMemberId) {
        return getOrCreateMapEntry(theMemberId).getValue();
    }
    private MutableLiveData<List<ChatRow>> getOrCreateMapEntry(final int theMemberId) {
        if(!mRows.containsKey(theMemberId)) {
            mRows.put(theMemberId, new MutableLiveData<>(new ArrayList<>()));
        }
        return mRows.get(theMemberId);
    }


    /**
     * Handle error
     * @param error
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }


    /**
     * A call back when a new chat room created
     */
    public interface ChatRoomCreationCallback {
        void onChatRoomCreated(int chatId);
    }

    /**
     * Update a new chat room to chat list when a new room is added
     * All members who is invited to the new chat room will have updated list
     * @param chatId
     * @param message
     */
    public void updateChatRowWithNewMessage(int chatId, ChatMessage message) {
        List<ChatRow> updatedRows = new ArrayList<>(rows.getValue());
        for (ChatRow row : updatedRows) {
            if (row.getmChatRoomID() == chatId) {
                row.setHasNewMessage(true);
                row.setmLastMessage(message.getMessage());
                break;
            }
        }
        rows.postValue(updatedRows);

    }

    /**
     * get chat room list
     * @return rows
     */
    public MutableLiveData<List<ChatRow>> getRows() {
        return rows;
    }

    /**
     * Set chat room list
     * @param rows
     */
    public void setRows(MutableLiveData<List<ChatRow>> rows) {
        this.rows = rows;
    }
}
