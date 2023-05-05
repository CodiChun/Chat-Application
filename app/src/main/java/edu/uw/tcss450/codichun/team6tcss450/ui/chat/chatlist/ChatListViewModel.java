package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class ChatListViewModel extends ViewModel {

    private MutableLiveData<List<ChatRow>> chatRowsLiveData;

//    public ChatListViewModel(@NonNull Application application) {
//        super(application);
//    }

    public LiveData<List<ChatRow>> getChatRows() {
        if (chatRowsLiveData == null) {
            chatRowsLiveData = new MutableLiveData<List<ChatRow>>();
            loadChatRows();
        }
        return chatRowsLiveData;
    }

    private void loadChatRows() {
        //Mock data for testing, can be changed to database later
        List<ChatRow> rows = new ArrayList<ChatRow>();
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));

        chatRowsLiveData.setValue(rows);
    }
}
