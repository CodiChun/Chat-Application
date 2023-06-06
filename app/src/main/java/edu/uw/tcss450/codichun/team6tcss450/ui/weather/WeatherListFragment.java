package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherListBinding;
import edu.uw.tcss450.codichun.team6tcss450.model.LocationViewModel;
import edu.uw.tcss450.codichun.team6tcss450.model.UserInfoViewModel;


public class WeatherListFragment extends Fragment{
    private WeatherListViewModel mModel;
    private View myView;
    UserInfoViewModel mUserModel;
    LocationViewModel mLocationModel;
    MutableLiveData<List<String>> uLocations;
    public WeatherListFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
        mUserModel = new ViewModelProvider(requireActivity()).get(UserInfoViewModel.class);
        uLocations = new MutableLiveData<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_weather_list, container, false);
//        mModel.connectGetDaily();
//        mModel.connectGetHourly();
//        TextView view = recyclerView.findViewById(R.id.label_city);
//        String s = currentData.getValue().getmCity() + "\n" + currentData.getValue().getmTemp();
//        view.setText(s);
        return myView;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLocationModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());
        ImageButton location = binding.imageButton;
        ImageButton save = binding.saveButton;
        SearchView search = binding.searchLocation;
        Location currentLoc = mLocationModel.getCurrentLocation();
        String latlng = currentLoc.getLatitude() + "," + currentLoc.getLongitude();


        binding.layoutWait.setVisibility(View.VISIBLE);
        System.out.println(latlng);
        String jwt = mUserModel.getmJwt();
        System.out.println(mUserModel.getUserId());
        mModel.getLocations(jwt,mUserModel.getEmail(), mUserModel.getUserId());
        mModel.connectGetCurrent(jwt, latlng);
        mModel.connectGetDaily(jwt, latlng);
        mModel.connectGetHourly(jwt, latlng);
        mModel.addWeatherListObserver(getViewLifecycleOwner(), weatherList -> {
            if (!weatherList.isEmpty()) {
                binding.listRoot.setAdapter(new WeatherRecyclerViewAdapter(weatherList));
            }
        });
        mModel.addWeatherListObserverHourly(getViewLifecycleOwner(), weatherList -> {
            if (!weatherList.isEmpty()) {
                binding.listHourly.setAdapter(new WeatherRecyclerViewAdapterHourly(weatherList));
            }
        });
        mModel.addWeatherListObserverCurrent(getViewLifecycleOwner(), curr -> {
            Context c = myView.getContext();
            String drawablename = curr.getmIcon();
            int drawableId = c.getResources().getIdentifier(drawablename, "drawable", c.getPackageName());
            binding.labelCity.setText(curr.getmCity() + "\n" + curr.getmTemp() + "°F \n" + curr.getmDescription());
            binding.imageCurrentWeather.setImageResource(drawableId);
        });
        mModel.addLocationListObserver(getViewLifecycleOwner(), list ->{
            uLocations.setValue(list);
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mModel.connectGetCurrent(jwt, s);
                mModel.connectGetDaily(jwt, s);
                binding.listRoot.getAdapter().notifyDataSetChanged();
                mModel.connectGetHourly(jwt, s);
                binding.listHourly.getAdapter().notifyDataSetChanged();
                binding.searchLocation.setQuery("", false);
                binding.searchLocation.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String[] tempLocations = uLocations.getValue().toArray(new String[0]);
                Log.i("TEST TEMPLOCATIONs", Arrays.toString(tempLocations));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Location:")
                        .setItems(tempLocations, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = tempLocations[which];
                                if (!input.equals("Current Location")) {
                                    mModel.connectGetCurrent(jwt, input);
                                    mModel.connectGetDaily(jwt, input);
                                    binding.listRoot.getAdapter().notifyDataSetChanged();
                                    mModel.connectGetHourly(jwt, input);
                                    binding.listHourly.getAdapter().notifyDataSetChanged();
                                }else{
                                    Location currentLoc = mLocationModel.getCurrentLocation();
                                    String latlng = currentLoc.getLatitude() + "," + currentLoc.getLongitude();
                                    mModel.connectGetCurrent(jwt, latlng);
                                    mModel.connectGetDaily(jwt, latlng);
                                    binding.listRoot.getAdapter().notifyDataSetChanged();
                                    mModel.connectGetHourly(jwt, latlng);
                                    binding.listHourly.getAdapter().notifyDataSetChanged();
                                }
                            }
                        });
                builder.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = binding.labelCity.getText().toString().split("\n")[0];
                Log.i("TEST CITY SPLIT", city);
                mModel.addLocations(jwt,mUserModel.getEmail(),city,mUserModel.getUserId());
            }
        });
        binding.layoutWait.setVisibility(View.INVISIBLE);

    }
}