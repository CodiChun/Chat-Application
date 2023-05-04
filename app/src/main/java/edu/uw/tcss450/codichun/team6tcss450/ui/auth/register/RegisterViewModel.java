package edu.uw.tcss450.codichun.team6tcss450.ui.auth.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

public class RegisterViewModel extends AndroidViewModel {

    private MutableLiveData<JSONObject> mResponse;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }
}
