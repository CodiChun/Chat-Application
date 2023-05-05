package edu.uw.tcss450.codichun.team6tcss450.ui.chat.newchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class NewChatFragment extends Fragment {

    public View myView;
    private NavController myNavController;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_new_chat, container, false);

//        myNavController = Navigation.findNavController(myView);
//
//        // Button listeners
//        addCancelButtonListener();
//        addAddPeopleButtonListener();

        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myNavController = Navigation.findNavController(view);
        // Button listeners
        addCancelButtonListener(view);
        addAddPeopleButtonListener(view);
        addSendButtonListener(view);
    }

    private void addCancelButtonListener(View view){
        ImageButton buttonCancel = (ImageButton)view.findViewById(R.id.button_chatroom_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myNavController.navigate(R.id.action_newChatFragment_to_navigation_chatlist);
            }
        });
    }

    private void addAddPeopleButtonListener(View view){
        ImageButton buttonAddPeople = (ImageButton)view.findViewById(R.id.button_chatroom_addpeople);
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

    private void addSendButtonListener(View view){
        Button buttonSend = (Button)view.findViewById(R.id.button_chatroom_send);
        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: need to make sure it's creating a specific new chat room
                //TODO: add correct new data to the chatting history
                myNavController.navigate(R.id.action_newChatFragment_to_chatRoomFragment);
            }
        });
    }
}