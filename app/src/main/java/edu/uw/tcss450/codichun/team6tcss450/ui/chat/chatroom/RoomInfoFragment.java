package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.io.RequestQueueSingleton;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;

/**
 * The fragment for chat room information
 * @author codichun
 * @version 1.0
 */
public class RoomInfoFragment extends Fragment implements RoomInfoMemberAdapter.OnRemoveMemberListener{

    private RoomInfoMemberAdapter adapter;
    private RecyclerView recyclerView;
    private Button addMemberButton, backToChatRoomButton;
    private TextView roomNameTextView;

    private int mChatId;
    private String mChatName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_info, container, false);
        mChatId = getArguments().getInt("chatId");
        mChatName = getArguments().getString("name");
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roomNameTextView = view.findViewById(R.id.TextView_roominfo_roomname);
        recyclerView = view.findViewById(R.id.recyclerView_roominfo_memberlist);
        addMemberButton = view.findViewById(R.id.button_roominfo_addmember);
        backToChatRoomButton = view.findViewById(R.id.backToChatRoomButton);

        adapter = new RoomInfoMemberAdapter(this);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        backToChatRoomButton.setOnClickListener(v -> {
            // Implement the navigation back to chat room, using Android Navigation Component
            Navigation.findNavController(v).navigateUp();
        });

        addMemberButton.setOnClickListener(v -> {
            // TODO: Implement the functionality to add a new member, could be a new fragment to add member
            // Navigation.findNavController(v).navigate(R.id.action_to_addMemberFragment);
        });

        // Fetch room name and set it to roomNameTextView
        roomNameTextView.setText(mChatName);

        //Fetch members list and set it to the adapter
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        UserInfoViewModel userInfoViewModel = provider.get(UserInfoViewModel.class);
        fetchMembers(mChatId, userInfoViewModel.getmJwt());
    }


    /**
     * Get the member list of the current chat room
     * @param chatId
     * @param jwt
     */
    private void fetchMembers(int chatId, String jwt) {

        String url = getActivity().getApplication().getResources().getString(R.string.base_url) + "chats" + "/" + chatId;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject resp = new JSONObject(response);
                        JSONArray membersArray = resp.getJSONArray("rows");

                        List<RoomInfoMember> members = new ArrayList<>();
                        for (int i = 0; i < membersArray.length(); i++) {
                            JSONObject memberObject = membersArray.getJSONObject(i);
                            String email = memberObject.getString("email");
                            String username = memberObject.getString("username");
                            int memberid = Integer.parseInt(memberObject.getString("memberid"));
                            members.add(new RoomInfoMember(username, memberid, email));
                        }

                        // Set the members to the adapter
                        adapter.setMembers(members);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("RoomInfoFragment", "That didn't work!", error);
                    System.out.println("RoomInfoFragment" + error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("authorization", "Bearer " + jwt); // replace with your JWT Token
                return params;
            }
        };

        // Add the request to the RequestQueue.
        RequestQueueSingleton.getInstance(getActivity().getApplication().getApplicationContext())
                .addToRequestQueue(stringRequest);
    }


    /**
     * Remove a member from the chat room
     * @param chatId
     * @param email
     * @param jwt
     */
    public void removeMemberFromChat(int chatId, String email, final String jwt) {
        String url = getActivity().getApplication().getResources().getString(R.string.base_url) + "chats" + "/" + chatId + "/" + email;

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
        RequestQueueSingleton.getInstance(getActivity().getApplication().getApplicationContext())
                .addToRequestQueue(stringRequest);
    }

    /**
     * update recycler view when remove a member
     * @param email
     */
    @Override
    public void onRemoveMember(String email) {
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        UserInfoViewModel userInfoViewModel = provider.get(UserInfoViewModel.class);
        removeMemberFromChat(mChatId, email, userInfoViewModel.getmJwt());
    }
}
