package edu.uw.tcss450.codichun.team6tcss450.ui.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class HomeFragment extends Fragment {

    //private HomeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Log.d("Bottom nav", "HOME");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
