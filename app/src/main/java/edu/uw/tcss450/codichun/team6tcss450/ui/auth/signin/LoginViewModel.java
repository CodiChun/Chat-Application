package edu.uw.tcss450.codichun.team6tcss450.ui.auth.signin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<JSONObject> mResponse;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }
}
