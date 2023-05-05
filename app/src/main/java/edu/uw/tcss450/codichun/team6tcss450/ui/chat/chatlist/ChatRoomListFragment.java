package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;

import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomListFragment extends Fragment {

//    private RecyclerView myRecyclerView;
    private ChatRowAdapter myAdapter;
    private ChatListViewModel myViewModel;
    private View myView;

    private NavController myNavController;


    public ChatRoomListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room_list, container, false);
//        myNavController = Navigation.findNavController(myView);
        RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        myViewModel.getChatRows().observe(getViewLifecycleOwner(), new Observer<List<ChatRow>>() {
            @Override
            public void onChanged(List<ChatRow> chatRows) {
                myAdapter = new ChatRowAdapter(getActivity(), chatRows);
                recyclerView.setAdapter(myAdapter);
            }
        });


        return myView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myNavController = Navigation.findNavController(view);
        addButtonNewChat(view);
    }

    private void addButtonNewChat(View view){
        Button buttonNewChat = (Button) view.findViewById(R.id.button_newchatroom_chatlist);
        buttonNewChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myNavController.navigate(R.id.action_navigation_chatlist_to_newChatFragment);
            }
        });
    }
}