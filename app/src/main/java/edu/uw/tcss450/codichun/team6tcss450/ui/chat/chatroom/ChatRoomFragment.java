package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
//import android.widget.Toolbar;

import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomFragment extends Fragment {

    public View myView;



    private NavController myNavController;
//    private Toolbar myToolBar;

    public ChatRoomFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room, container, false);
//        myToolBar = myView.findViewById(R.id.topbar_chatroom);
        int chatRoomId = getArguments().getInt("chatRoomId");
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myNavController = Navigation.findNavController(view);
        // Button listeners
        addButtonSend(view);
        addNavigationBack(view);

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