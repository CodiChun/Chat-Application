package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom.ChatRoomFragment;

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
                myAdapter.setOnItemClickListener(new ChatRowAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Navigation.findNavController(view).navigate(R.id.action_navigation_chatlist_to_chatRoomFragment);
                        // Get the data at position
                        ChatRow data = myAdapter.getDataAtPosition(position);

                        // Get the ID of the chat room
                        int chatRoomId = data.getChatRoomId();
                        String chatRoomName = data.getName();

                        // Create a Bundle to hold the chat room ID
                        Bundle bundle = new Bundle();
                        bundle.putInt("chatRoomId", chatRoomId);
                        bundle.putString("chatRoomName", chatRoomName);

                        // Use Navigation Component to navigate to the new Fragment
                        Navigation.findNavController(view).navigate(R.id.action_navigation_chatlist_to_chatRoomFragment, bundle);
//
//                        // Create new Fragment
//                        ChatRoomFragment fragment = new ChatRoomFragment();
//
//                        // Replace the current Fragment with the new one
//                        //FragmentTransaction transaction = getActivity()
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container_chatlist_fragmentcontainer, fragment)
//                                .addToBackStack(null)
//                                .commit();
                    }
                });

                recyclerView.setAdapter(myAdapter);
            }
        });


        return myView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //******************
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        //FragmentChatRoomListBinding.bind(getView()).textHello.setText("Hello " + model.getEmail());
        //******************
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