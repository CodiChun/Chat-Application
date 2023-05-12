package edu.uw.tcss450.codichun.team6tcss450.ui.chat.chatlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.ActivityChatListBinding;

public class ChatListActivity extends AppCompatActivity {
    private ActivityChatListBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); //online, need check

    }

    private void getUsers(){
        //TODO
    }

    private void showErrorMessage(){
        //TODO
    }




}