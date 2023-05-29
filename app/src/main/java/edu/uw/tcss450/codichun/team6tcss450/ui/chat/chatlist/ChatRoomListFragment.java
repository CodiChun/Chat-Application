package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import edu.uw.tcss450.codichun.team6tcss450.services.PushReceiver;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom.ChatMessage;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom.ChatRoomFragment;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomListFragment extends Fragment {

    private ChatRowAdapter myAdapter;
    private ChatListViewModel myViewModel;
    private View myView;

    private NavController myNavController;
    //private UserInfoViewModel mUserModel;
    private BroadcastReceiver mChatRoomUpdateReceiver;
    private UserInfoViewModel model;

    private BroadcastReceiver mChatUpdateReceiver;


    /**
     * Constructor
     */
    public ChatRoomListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the BroadcastReceiver
        mChatUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get the new message and the chat room ID
                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");
                int chatId = intent.getIntExtra("chatid", -1);

                // Update the ViewModel
                myViewModel.updateChatRowWithNewMessage(chatId, cm);
            }
        };

        // Register the BroadcastReceiver to receive a broadcast whenever there is a new chat message
        IntentFilter filter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        getActivity().registerReceiver(mChatUpdateReceiver, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver when the fragment is destroyed
        getActivity().unregisterReceiver(mChatUpdateReceiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room_list, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(ChatListViewModel.class);

        //tring to load chat lists
       model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_chatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //add the swiper
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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
                            // Get the data at position
                            ChatRow data = myAdapter.getDataAtPosition(position);

                            //set the notification image go
                            ChatRow clickedRow = chatRows.get(position);
                            clickedRow.setHasNewMessage(false);

                            // Get the ID of the chat room
                            int chatRoomId = data.getmChatRoomID();
                            //System.out.println("on chat room list fragment, data.getChatRoomID: " + chatRoomId);

                            String chatRoomName = data.getmRoomName();

                            // Create a Bundle to hold the chat room ID
                            Bundle bundle = new Bundle();
                            bundle.putInt("chatRoomID", chatRoomId);
                            bundle.putString("chatRoomName", chatRoomName);
                            //System.out.println("on chat room list fragment, bundle: " + bundle.getInt("chatRoomID"));

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
        return myView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);
        myNavController = Navigation.findNavController(view);
        addButtonNewChat(view);

        //tool bar for log out
        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * Button lisener for create a new chat room
     * @param view
     */
    private void addButtonNewChat(View view){
        Button buttonNewChat = (Button) view.findViewById(R.id.button_newchatroom_chatlist);
        buttonNewChat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myNavController.navigate(R.id.action_navigation_chatlist_to_newChatFragment);
            }
        });
    }


    //for remove room
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
            builder.setTitle("Remove chat room");
            builder.setMessage("Do you want to hide this chat room or leave it?");
            builder.setPositiveButton("Hide", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Remove the item from the list but not from the database
                    int position = viewHolder.getAdapterPosition();
                    myAdapter.getRows().remove(position);
                    myAdapter.notifyItemRemoved(position);
                }
            });
            builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Remove the item from the list and also from the database
                    int chatRoomId = myAdapter.getRows().get(position).getmChatRoomID();
                    UserInfoViewModel userInfoModel = new ViewModelProvider(getActivity())
                            .get(UserInfoViewModel.class);
                    int position = viewHolder.getAdapterPosition();
                    myAdapter.getRows().remove(position);
                    myAdapter.notifyItemRemoved(position);

                    // Remove the user from the chat room in the database
                    myViewModel.removeMemberFromChat(chatRoomId, userInfoModel.getEmail(), userInfoModel.getmJwt());
                }
            });
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User cancelled, just refresh the item to prevent it from disappearing
                    myAdapter.notifyItemChanged(position);
                }
            });
            builder.show();
        }
    };


    /**
     * For updating new created chat room to all members
     */
    @Override
    public void onStart() {
        super.onStart();
        if (mChatRoomUpdateReceiver == null) {
            mChatRoomUpdateReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int newChatRoomId = intent.getIntExtra("chatRoomId", -1);
                    if (newChatRoomId != -1) {
                        // This involve calling a method on ViewModel to fetch the chat room data from the server and add it to the list.
                        //myViewModel.updateList(model.getUserId() ,model.getmJwt());
                        //System.out.println("***** new chat room broadcast received ******");
                        myViewModel.loadChats(model.getUserId() ,model.getmJwt());
                    }
                }
            };
        }
        IntentFilter filter = new IntentFilter(PushReceiver.RECEIVED_NEW_CHATROOM);
        getActivity().registerReceiver(mChatRoomUpdateReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mChatRoomUpdateReceiver != null) {
            getActivity().unregisterReceiver(mChatRoomUpdateReceiver);
        }
    }

    /**
     * Getter for chat row adapter
     * @return
     */
    public ChatRowAdapter getChatRowAdapter() {
        return myAdapter;
    }

    /**
     * Setter for chat row adapter
     * @param myAdapter
     */
    public void setMyAdapter(ChatRowAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }
}