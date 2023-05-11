package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.widget.Toolbar;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomFragment extends Fragment {

    //The chat ID for "global" chat
    private static final int HARD_CODED_CHAT_ID = 1;

    public View myView;

    private NavController myNavController;

    private ChatViewModel mChatModel;
    private UserInfoViewModel mUserModel;

    public ChatRoomFragment() {

    }

    //*************
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mChatModel.getFirstMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
    }
    //*************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room, container, false);
//        myToolBar = myView.findViewById(R.id.topbar_chatroom);
        int chatRoomId = getArguments().getInt("chatRoomId");
        // Get the name of the chat room
        String chatRoomName = getArguments().getString("chatRoomName");

        // Set the text of the TextView to the chat room name
        TextView textView = myView.findViewById(R.id.textView_chatroom_name);
        textView.setText(chatRoomName);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myNavController = Navigation.findNavController(view);
        // Button listeners
        addButtonSend(view);
        addNavigationBack(view);

        //*************
        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerviewChatroom;
        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new ChatRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(HARD_CODED_CHAT_ID),
                mUserModel.getEmail()));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
        });

        mChatModel.addMessageObserver(HARD_CODED_CHAT_ID, getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });

        //*************

    }

    private void addButtonSend(View view){
        Button buttonSend = (Button) view.findViewById(R.id.button_chatroom_send);
        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: add action
            }
        });
    }

    private void addNavigationBack(View view) {
        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.imageButton_chatroom_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myNavController.navigate(R.id.action_chatRoomFragment_to_navigation_chatlist);
            }
        });
    }
}