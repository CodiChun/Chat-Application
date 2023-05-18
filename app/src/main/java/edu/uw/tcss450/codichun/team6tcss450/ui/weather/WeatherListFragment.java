package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherListBinding;


public class WeatherListFragment extends Fragment {
    private WeatherListViewModel mModel;
    private View myView;

    public WeatherListFragment(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_weather_list, container, false);
        mModel.connectGetDaily();
        mModel.connectGetHourly();
//        TextView view = recyclerView.findViewById(R.id.label_city);
//        String s = currentData.getValue().getmCity() + "\n" + currentData.getValue().getmTemp();
//        view.setText(s);
        return myView;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());
        binding.layoutWait.setVisibility(View.VISIBLE);
        mModel.connectGetDaily();
        mModel.connectGetHourly();
        mModel.connectGetCurrent();
        mModel.addWeatherListObserver(getViewLifecycleOwner(), weatherList -> {
            if (!weatherList.isEmpty()) {
                binding.listRoot.setAdapter(new WeatherRecyclerViewAdapter(weatherList));
            }
        });
        mModel.addWeatherListObserverHourly(getViewLifecycleOwner(),weatherList ->{
            if (!weatherList.isEmpty()) {
                binding.listHourly.setAdapter(new WeatherRecyclerViewAdapterHourly(weatherList));
            }
        });
        mModel.addWeatherListObserverCurrent(getViewLifecycleOwner(),curr ->{
            Context c = myView.getContext();
            String drawablename = curr.getmIcon();
            int drawableId = c.getResources().getIdentifier(drawablename, "drawable", c.getPackageName());
            binding.labelCity.setText(curr.getmCity() + "\n" + curr.getmTemp() + "\n" + curr.getmDescription());
            binding.imageCurrentWeather.setImageResource(drawableId);
        });
        binding.layoutWait.setVisibility(View.INVISIBLE);

    }
}