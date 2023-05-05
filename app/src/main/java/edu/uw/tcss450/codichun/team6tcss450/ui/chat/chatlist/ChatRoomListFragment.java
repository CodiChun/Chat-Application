package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatRowAdapter adapter;
    private ChatListViewModel viewModel;

    View view;

    public ChatRoomListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat_room_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        viewModel.getChatRows().observe(getViewLifecycleOwner(), new Observer<List<ChatRow>>() {
            @Override
            public void onChanged(List<ChatRow> chatRows) {
                adapter = new ChatRowAdapter(getActivity(), chatRows);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;

//        RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_chatList);
//
//         //Mock data for testing, can be changed to database later
//        List<ChatRow> rows = new ArrayList<ChatRow>();
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(new ChatRowAdapter(getActivity(),rows));
//
//
//        myNavController = Navigation.findNavController(myView);

    }

    private void addButtonNewChat(){
        ImageButton buttonNewChat = (ImageButton) view.findViewById(R.id.button_newchatroom_chatlist);
        buttonNewChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: wait till the navigator done to uncomment this one
//                myNavController.navigate(R.id.action_fragment_chat_room_list_to_fragment_new_chat);
            }
        });
    }
}