package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class LocationDialogFragment extends DialogFragment {
    private NavController myNavController;

    private String[] tempLocations = {"Tacoma","Puyallup","Tempe","Bellevue"};
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        myNavController = Navigation.findNavController(this.getView());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Location:")
                .setItems(tempLocations, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = tempLocations[which];
                        //set new data to bundle
                        Bundle bundleNewRoom = new Bundle();
                        bundleNewRoom.putString("input", input);

                        myNavController.navigate(R.id.action_newChatFragment_to_chatRoomFragment, bundleNewRoom);

                    }
                });
        return builder.create();
    }
}
