package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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

    private int mChatRoomID;

    public View myView;

    private NavController myNavController;

    private ChatViewModel mChatModel;
    private UserInfoViewModel mUserModel;

    private ChatSendViewModel mSendModel;

    String mChatRoomName;

    public ChatRoomFragment() {

    }

//    public ChatRoomFragment(int theRoomID) {
//        this.HARD_CODED_CHAT_ID = theRoomID;
//    }

    //*************
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mChatRoomID = getArguments().getInt("chatRoomID");
            mChatRoomName = getArguments().getString("chatRoomName");
        }

        System.out.println("chatRoomFragment onCreate: " + mChatRoomID);

        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mChatModel.getFirstMessages(mChatRoomID, mUserModel.getmJwt());
        mSendModel = provider.get(ChatSendViewModel.class);
    }
    //*************

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room, container, false);
            TextView textView = myView.findViewById(R.id.textView_chatroom_name);
            textView.setText(mChatRoomName);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get new chat room name and id
        if(mChatRoomName == null && getArguments()!=null){
            mChatRoomID = getArguments().getInt("newChatRoomId");
            mChatRoomName = getArguments().getString("newChatRoomName");
            // Set the text of the TextView to the chat room name
            TextView textView = myView.findViewById(R.id.textView_chatroom_name);
            textView.setText(mChatRoomName);
        }
        System.out.println("chatRoomFragment onViewCreated: " + mChatRoomID);


        myNavController = Navigation.findNavController(view);
        // Button listeners
        addNavigationBack(view);

        //*************
        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerviewChatroom;

        /**
         * Global Layout Listener to detect when the keyboard is shown
         * Scroll to the end of the list when the keyboard is shown
         */
        rv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (keyboardShown(rv.getRootView())) {
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                }
            }
        });


        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new ChatRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(mChatRoomID),
                mUserModel.getEmail()));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(mChatRoomID, mUserModel.getmJwt());
        });

        mChatModel.addMessageObserver(mChatRoomID, getViewLifecycleOwner(),
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

        //Send button was clicked. Send the message via the SendViewModel
        binding.buttonChatroomSend.setOnClickListener(button -> {
            mSendModel.sendMessage(mChatRoomID,
                    mUserModel.getmJwt(),
                    binding.edittextChatroomMessage.getText().toString());
        });
//when we get the response back from the server, clear the edittext
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.edittextChatroomMessage.setText(""));


        //*************

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

    /**
     * Help method of to check if the keyboard is shown
     * @param rootView
     * @return
     */
    public boolean keyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}