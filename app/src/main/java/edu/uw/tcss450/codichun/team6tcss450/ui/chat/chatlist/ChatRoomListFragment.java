package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.util.ArrayList;
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
    int HARD_CODE_CHAT_ROOM_ID = 1;
    private ChatRowAdapter myAdapter;
    private ChatListViewModel myViewModel;
    private View myView;

    private NavController myNavController;
    //private UserInfoViewModel mUserModel;


    public ChatRoomListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room_list, container, false);
//        myNavController = Navigation.findNavController(myView);


        myViewModel = new ViewModelProvider(requireActivity()).get(ChatListViewModel.class);

        //tring to load chat lists
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        //myViewModel.loadChats(model.getUserId(), model.getmJwt());
        RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        myAdapter = new ChatRowAdapter(getActivity(), new ArrayList<>());
//        recyclerView.setAdapter(myAdapter);
        //myViewModel = new ViewModelProvider(this).get(ChatListViewModel.class);

        // check if the adapter is null before creating a new one
        if(myAdapter == null) {
            myAdapter = new ChatRowAdapter(getActivity(), new ArrayList<>());
            recyclerView.setAdapter(myAdapter);

            myViewModel.getChatRows().observe(getViewLifecycleOwner(), new Observer<List<ChatRow>>() {
                @Override
                public void onChanged(List<ChatRow> chatRows) {
                    myAdapter.updateData(chatRows);  // Update data in your adapter
                    myAdapter.notifyDataSetChanged();  // Notify adapter of data change

                    myAdapter.setOnItemClickListener(new ChatRowAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //Navigation.findNavController(view).navigate(R.id.action_navigation_chatlist_to_chatRoomFragment);
                            // Get the data at position
                            ChatRow data = myAdapter.getDataAtPosition(position);

                            // Get the ID of the chat room

                            //TODO: UNCOMMENT IT WHEN IT'S NOT HARD CODE
                            int chatRoomId = data.getmChatRoomID();
                            System.out.println("on chat room list fragment, data.getChatRoomID: " + chatRoomId);
                            //TODO: UNCOMMENT IT WHEN IT'S HARD CODE
                            //int chatRoomId = HARD_CODE_CHAT_ROOM_ID;

                            String chatRoomName = data.getmRoomName();

                            // Create a Bundle to hold the chat room ID
                            Bundle bundle = new Bundle();
                            bundle.putInt("chatRoomID", chatRoomId);
                            bundle.putString("chatRoomName", chatRoomName);
                            System.out.println("on chat room list fragment, bundle: " + bundle.getInt("chatRoomID"));

//                        //create a new chat room
//                        ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
//                        chatRoomFragment.setArguments(bundle);

                            // Use Navigation Component to navigate to the new Fragment
                            Navigation.findNavController(view).navigate(R.id.action_navigation_chatlist_to_chatRoomFragment, bundle);
                        }
                    });
                }
            });

            // Load the chat lists
            myViewModel.loadChats(model.getUserId(), model.getmJwt());
        } else {
            recyclerView.setAdapter(myAdapter);
        }

//
//        myViewModel.getChatRows().observe(getViewLifecycleOwner(), new Observer<List<ChatRow>>() {
//            @Override
//            public void onChanged(List<ChatRow> chatRows) {
//
//                myAdapter.updateData(chatRows);  // Update data in your adapter
//                myAdapter.notifyDataSetChanged();  // Notify adapter of data change
//
//                myAdapter.setOnItemClickListener(new ChatRowAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        //Navigation.findNavController(view).navigate(R.id.action_navigation_chatlist_to_chatRoomFragment);
//                        // Get the data at position
//                        ChatRow data = myAdapter.getDataAtPosition(position);
//
//                        // Get the ID of the chat room
//
//                        //TODO: UNCOMMENT IT WHEN IT'S NOT HARD CODE
//                        int chatRoomId = data.getmChatRoomID();
//                        System.out.println("on chat room list fragment, data.getChatRoomID: " + chatRoomId);
//                        //TODO: UNCOMMENT IT WHEN IT'S HARD CODE
//                        //int chatRoomId = HARD_CODE_CHAT_ROOM_ID;
//
//                        String chatRoomName = data.getmRoomName();
//
//                        // Create a Bundle to hold the chat room ID
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("chatRoomID", chatRoomId);
//                        bundle.putString("chatRoomName", chatRoomName);
//                        System.out.println("on chat room list fragment, bundle: " + bundle.getInt("chatRoomID"));
//
////                        //create a new chat room
////                        ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
////                        chatRoomFragment.setArguments(bundle);
//
//                        // Use Navigation Component to navigate to the new Fragment
//                        Navigation.findNavController(view).navigate(R.id.action_navigation_chatlist_to_chatRoomFragment, bundle);
//                    }
//                });
//
//
//
//            }
//        });
//
//        // Load the chat lists
//        myViewModel.loadChats(model.getUserId(), model.getmJwt());
//        System.out.println("user id is: " + model.getUserId());

//        // Observe the chat rows LiveData
//        myViewModel.getChatRows().observe(getViewLifecycleOwner(), chatRows -> {
//            myAdapter.updateData(chatRows);  // Update data in your adapter
//            myAdapter.notifyDataSetChanged();  // Notify adapter of data change
//        });


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

        //tool bar
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
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