package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_chat_room, container, false);
        myNavController = Navigation.findNavController(myView);
//        myToolBar = myView.findViewById(R.id.topbar_chatroom);

        // add button listener
        addButtonSend();

        return myView;
    }

    private void addButtonSend(){
        Button buttonSend = (Button) myView.findViewById(R.id.button_chatroom_send);
        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: add action
            }
        });
    }

//    private void addNavigationBack(){
//        myToolBar.setNavigationOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                //TODO: wait till the navigator done to edit and uncomment this one
////                NavHostFragment.findNavController(ChatRoomFragment.this).navigate(R.id.TODO);
//            }
//        });
//    }
}