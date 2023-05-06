package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.codichun.team6tcss450.R;
//import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherListBinding;


public class WeatherListFragment extends Fragment {
    private WeatherListViewModel mModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
        mModel.connectGet();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_list, container, false);

    }
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());
//        mModel.addBlogListObserver(getViewLifecycleOwner(), blogList -> {
//            if (!blogList.isEmpty()) {
//                binding.listRoot.setAdapter(
//                        new WeatherRecyclerViewAdapter(blogList)
//                );
//                binding.layoutWait.setVisibility(View.GONE);
//            }
//        });
//    }
}