package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.io.RequestQueueSingleton;


/**
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

    public LiveData<List<ChatRow>> getChatRows() {
        return rows;
    }

    public void addChatRow(ChatRow theChatRow) {
        List<ChatRow> currentList = rows.getValue();
        if (currentList != null) {
            currentList.add(0, theChatRow);
            rows.setValue(currentList);
        }
    }

    public void updateChatRow(int theMemberId, ChatRow theChatRow){
        List<ChatRow> list = getChatRoomListByMemberId(theMemberId);
        list.add(0, theChatRow);
        getOrCreateMapEntry(theMemberId).setValue(list);
    }

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




    private void handleCreateNewChatRoomSuccess(final JSONObject response, ChatRoomCreationCallback callback,  final List<Integer> memberId, final String jwt) {
        try {
            if (response.has("chatID")) {
                int chatId = response.getInt("chatID");
                callback.onChatRoomCreated(chatId);
                addMemberToChat(chatId, memberId, jwt);
                // Here you can create a new ChatRow with the obtained chatId and update your list
                // For instance:
                //addChatRow("New Room", new ArrayList<>(), chatId, R.drawable.image_chatlist_profile_32dp);
            } else {
                Log.e("JSON PARSE ERROR", "No chatId found in response");
            }
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handleCreateNewChatRoomSuccess");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

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
                        // Do something with response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        error.printStackTrace();
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

//    public void loadChats(int memberId, final String jwt) {
//        //String url = getApplication().getResources().getString(R.string.base_url) + END_POINT + "members/" + memberId;
//        String url = getApplication().getResources().getString(R.string.base_url) + "member/" + memberId;
//        System.out.println("End point for chat list: " + url);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        List<ChatRow> updatedChatRooms = new ArrayList<>();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject chatObject = response.getJSONObject(i);
//                                int chatId = chatObject.getInt("chatid");
//                                String name = chatObject.getString("name");
//
//                                // Assuming you have a Chat class with a constructor like Chat(int chatId, String name)
//                                ChatRow chatRow = new ChatRow(name, chatId, HARD_CODED_PROFILE);
//                                //chatList.add(chat);
//                                System.out.println("new chat room: " + chatId + ", name");
//                                addChatRow(chatRow);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        // Update RecyclerView adapter here with the new chat list
//                        // chatAdapter.updateData(chatList);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error
//                        System.out.println("Error! Could not load chats: " + error.getMessage());
//                    }
//                }
//        ){
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer " + jwt);
//                return headers;
//            }
//        };
//
//        // Add JsonArrayRequest to the RequestQueue
//        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
//                .addToRequestQueue(jsonArrayRequest);
//    }

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

                                // Assuming you have a ChatRow class with a constructor like ChatRow(String name, int chatId, int profileImage)
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
                        // chatAdapter.updateData(chatList);
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



    private void handelSuccess(final JSONObject response) {
        List<ChatRow> list;
        if (!response.has("memberId")) {
            throw new IllegalStateException("Unexpected response in ChatListViewModel: " + response);
        }
        try {
            list = getChatRoomListByMemberId(response.getInt("memberId"));
            JSONArray chatRoomsJSA = response.getJSONArray("rows"); //TODO: rows??
            for(int i = 0; i < chatRoomsJSA.length(); i++) {
                JSONObject chatRoomJSO = chatRoomsJSA.getJSONObject(i);

                final JSONArray membersArray = chatRoomJSO.getJSONArray("memberId");
                List<Integer> membersList = new ArrayList<>();
                for (int j = 0; j < membersArray.length(); j++) {
                    membersList.add(membersArray.getInt(j));
                }

                ChatRow chatRow = new ChatRow(
                        chatRoomJSO.getString("chatRoomName"),
                        (ArrayList<Integer>) membersList,
                        chatRoomJSO.getInt("chatId"),
                        chatRoomJSO.getInt("profile")
                );
                if (!list.contains(chatRow)) {
                    // don't add a duplicate
                    list.add(0, chatRow);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Chat chatRoomJSO already received",
                            "Or duplicate id:" + chatRow.getmChatRoomID());
                }

            }
            //inform observers of the change (setValue)
            getOrCreateMapEntry(response.getInt("chatId")).setValue(list);
        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

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


    //************************
    public interface ChatRoomCreationCallback {
        void onChatRoomCreated(int chatId);
    }

}
