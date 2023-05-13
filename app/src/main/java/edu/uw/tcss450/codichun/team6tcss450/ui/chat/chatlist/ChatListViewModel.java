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

/**
 * @author codichun
 * @version 1.0
 */
public class ChatListViewModel extends ViewModel {

    private MutableLiveData<List<ChatRow>> rows;

   // private MutableLiveData<List<ChatRow>> chatRowsLiveData;

    public ChatListViewModel() {
        rows = new MutableLiveData<>(new ArrayList<>());
        addChatRow("Global Chat", "see ya", R.drawable.image_chatlist_profile_32dp, 1);
        addChatRow("testroom", "...", R.drawable.image_chatlist_profile_32dp, 2);
    }

    public LiveData<List<ChatRow>> getChatRows() {
        return rows;
    }

    public void addChatRow(String roomName, String message, int profile, int roomID){
        List<ChatRow> currentList = rows.getValue();
        if(currentList != null){
            currentList.add(0, new ChatRow(roomName,message,profile, roomID));
            rows.setValue(currentList);
        }
    }

    /**
     * Local data
     */
    private void loadChatRows() {
        //Mock data for testing, can be changed to database later
        //List<ChatRow> rows = new ArrayList<ChatRow>();

//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Codi", "see ya", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Julia", "ok", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("JD", "NP", R.drawable.image_chatlist_profile_32dp));
//        rows.add(new ChatRow("Majed", "It works", R.drawable.image_chatlist_profile_32dp));
//
//        chatRowsLiveData.setValue(rows);
    }
}
