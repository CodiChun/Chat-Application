package edu.uw.tcss450.codichun.team6tcss450.ui.chat.newchat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        myNavController = Navigation.findNavController(myView);

        // Button listeners
        addCancelButtonListener();
        addAddPeopleButtonListener();

        return myView;
    }

    private void addCancelButtonListener(){
        ImageButton buttonCancel = (ImageButton)myView.findViewById(R.id.button_chatroom_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: wait till the navigator done to uncomment this one
//                myNavController.navigate(R.id.action_fragment_new_chat_to_fragment_chat_room_list);
            }
        });
    }

    private void addAddPeopleButtonListener(){
        ImageButton buttonAddPeople = (ImageButton)myView.findViewById(R.id.button_chatroom_addpeople);
        buttonAddPeople.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: wait till the navigator done to uncomment this one
//                myNavController.navigate(R.id.action_fragment_new_chat_to_fragment_contacts_list);
            }
        });
    }
}