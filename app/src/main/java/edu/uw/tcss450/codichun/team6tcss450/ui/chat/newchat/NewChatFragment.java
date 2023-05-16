package edu.uw.tcss450.codichun.team6tcss450.ui.chat.newchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist.ChatListViewModel;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist.ChatRoomListFragment;
import edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist.ChatRow;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class NewChatFragment extends Fragment {

    public View view;
    private NavController myNavController;
    int HARD_CODE_PROFILE = R.drawable.image_chatlist_profile_32dp;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_chat, container, false);

//        myNavController = Navigation.findNavController(myView);
//
//        // Button listeners
//        addCancelButtonListener();
//        addAddPeopleButtonListener();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myNavController = Navigation.findNavController(view);
        // Button listeners
        addCancelButtonListener(view);
        addAddPeopleButtonListener(view);
        addSendButtonListener();
    }

    private void addCancelButtonListener(View view){
        ImageButton buttonCancel = (ImageButton)view.findViewById(R.id.button_newchat_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myNavController.navigate(R.id.action_newChatFragment_to_navigation_chatlist);
            }
        });
    }

    private void addAddPeopleButtonListener(View view){
        ImageButton buttonAddPeople = (ImageButton)view.findViewById(R.id.button_newchat_addpeople);
        buttonAddPeople.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: NOT WORKING
                //myNavController.navigate(R.id.action_newChatFragment_to_navigation_chatlist);
                //myNavController.navigate(R.id.navigation_chatlist);
//                NavController myNavController2 = Navigation.findNavController(requireActivity(), R.id.navigation_chatlist);
//                myNavController2.navigate(R.id.navigation_chatlist);

            }
        });
    }

    private void addSendButtonListener(){
        Button buttonSend = (Button)view.findViewById(R.id.button_newchat_send);
        EditText editTextRoomName = view.findViewById(R.id.edittext_newchat_roomname);
        EditText editTextMessage = view.findViewById(R.id.edittext_newchat_typing);
        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: add correct new data to the chatting history

                // Assign a unique chat room number
                int newChatRoomId = hashCode();

                //get the room name from user input
                //TODO: HANDLE IF ROOM NAME IS NULL

                String chatRoomName = editTextRoomName.getText().toString();
                String message = editTextMessage.getText().toString();

                //update the chat list
                ChatListViewModel viewModel = new ViewModelProvider(requireActivity()).get(ChatListViewModel.class);
                viewModel.addChatRow(chatRoomName, message, HARD_CODE_PROFILE, newChatRoomId);



                //TODO: get the message from user input

                //set new data to bundle
                Bundle bundleNewRoom = new Bundle();
                bundleNewRoom.putInt("newChatRoomId", newChatRoomId);
                bundleNewRoom.putString("newChatRoomName", chatRoomName);


                //pass the bundle to the new chat room
                myNavController.navigate(R.id.action_newChatFragment_to_chatRoomFragment, bundleNewRoom);
            }
        });
    }
}