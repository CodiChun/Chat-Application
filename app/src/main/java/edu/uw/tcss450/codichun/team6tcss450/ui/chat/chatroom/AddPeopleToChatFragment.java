package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.io.RequestQueueSingleton;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist.ChatListViewModel;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist.ChatRow;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.newchat.NewRoomAdapter;

/**
 * create an instance of this fragment.
 */
public class AddPeopleToChatFragment extends Fragment {

    public View view;
    private NavController myNavController;
    int HARD_CODE_PROFILE = R.drawable.image_chatlist_profile_32dp;
    List<Integer> HARD_CODED_MEMBERS = new ArrayList<>(Arrays.asList(26, 27, 28));
    List<Integer> mSelectedMembers;

    UserInfoViewModel mUserModel;
    private NewRoomAdapter adapter;
    private RecyclerView recyclerView;
    private List<RoomInfoMember> mContactList;
    private int mChatId;
    private String mJwt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_people_to_chat, container, false);
        mSelectedMembers = new ArrayList<>();

        //get contact list data
        mContactList =  new ArrayList<>();
        mUserModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        fetchMembers(mUserModel.getmJwt());

        //mock data
//        mContactList.add(new RoomInfoMember("test1@test.com"));
//        mContactList.add(new RoomInfoMember("test2@test.com"));
//        mContactList.add(new RoomInfoMember("test3@test.com"));

        adapter = new NewRoomAdapter(mContactList, getContext(), mUserModel.getUserId(), false);
        recyclerView = view.findViewById(R.id.recyclerview_addtochat);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //get the chatId
        mChatId = getArguments().getInt("chatId");

        //add buttons
        addConfirmButtonListener();
        addCancelButtonListener();

        return view;
    }

    /**
     * Get the contact list for adding to chat room
     * @param jwt
     */
    private void fetchMembers(String jwt) {
        int memberId = mUserModel.getUserId();

        String url = getActivity().getApplication().getResources().getString(R.string.base_url) + "contacts2/list/" + memberId + "/1";
        System.out.println("end point for chat list on new chat fragment: " + url);

        // Create a new JSON request to fetch the contacts
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response and retrieve the contacts array
                            JSONArray contactsArray = response.getJSONArray("rows");

                            // Process the contacts array
                            for (int i = 0; i < contactsArray.length(); i++) {
                                JSONObject contactObject = contactsArray.getJSONObject(i);

                                // Extract contact information
                                int contactId = contactObject.getInt("id");
                                String username = contactObject.getString("nickname");
                                String email = contactObject.getString("email"); //
                                String firstname = contactObject.getString("firstname");
                                String lastname = contactObject.getString("lastname");

                                // Process the contact data
                                mContactList.add(new RoomInfoMember(username, contactId, email));
                                //System.out.println("new chat fragment got data: " + username);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //System.out.println("New Chat Fragment" + e.getMessage());
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //System.out.println(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("authorization", "Bearer " + jwt);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        RequestQueueSingleton.getInstance(getActivity().getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    /**
     * add the button for confirm
     */
    private void addConfirmButtonListener(){
        Button buttonCreate = (Button)view.findViewById(R.id.button_addtochat_confirm);
        //EditText editTextRoomName = view.findViewById(R.id.edittext_newchat_roomname);
        buttonCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //String chatRoomName = editTextRoomName.getText().toString();


                ChatListViewModel viewModel = new ViewModelProvider(requireActivity()).get(ChatListViewModel.class);

                //update selected member list
                mSelectedMembers = adapter.getSelectedMembers();

                //add member to chat
                addMemberToChat(mChatId, mSelectedMembers, mUserModel.getmJwt());
                System.out.println("jwt: " + mUserModel.getmJwt() +  ", chatId: " + mChatId + ", member list" + mSelectedMembers);

                //go back to chat room
                Navigation.findNavController(requireView()).popBackStack();
                System.out.println("create click");

            }
        });
    }

    /**
     * Add members to a chat room
     * @param chatId
     * @param memberIds
     * @param jwt
     */
    private void addMemberToChat(int chatId, List<Integer> memberIds, final String jwt) {
        String url = requireActivity().getApplication().getResources().getString(R.string.base_url) + "chatroom" + "/" + chatId;
        System.out.println("addMemberToChat called: chatId: " + chatId + ", membersIds: " + memberIds.toString());

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
                        error.printStackTrace();
                        System.out.println("AddPeopleToChatFragment: " + error.getMessage());
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
        RequestQueueSingleton.getInstance(requireActivity().getApplication().getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
    }


    /**
     * add the button fo cancel
     * go back to last page
     */
    private void addCancelButtonListener(){
        ImageButton buttonCancel = (ImageButton)view.findViewById(R.id.button_addtochat_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).popBackStack();
            }
        });
    }



}