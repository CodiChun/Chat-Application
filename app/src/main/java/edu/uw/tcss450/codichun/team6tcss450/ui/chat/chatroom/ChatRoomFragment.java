package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatroom;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uw.tcss450.codichun.team6tcss450.R;

/**
 * create an instance of this fragment.
 * @author codichun
 * @version 1.0
 */
public class ChatRoomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }
}