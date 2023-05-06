package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherListBinding;


public class WeatherListFragment extends Fragment {
    private WeatherListViewModel mModel;
    private View myView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
        //mModel.connectGet();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_weather_list, container, false);
        RecyclerView recyclerView = myView.findViewById(R.id.list_root);
        RecyclerView recyclerView2 = myView.findViewById(R.id.list_hourly);


        if (recyclerView instanceof RecyclerView) {
            //Try out a grid layout to achieve rows AND columns. Adjust the widths of the
            //cards on display
            // ((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 2));
            //Try out horizontal scrolling. Adjust the widths of the card so that it is
            //obvious that there are more cards in either direction. i.e. don't have the cards
            //span the entire witch of the screen. Also, when considering horizontal scroll
            //on recycler view, ensure that thre is other content to fill the screen.
//            ((LinearLayoutManager)((RecyclerView) view).getLayoutManager())
//            .setOrientation(LinearLayoutManager.HORIZONTAL);
            (recyclerView).setAdapter(
                    new WeatherRecyclerViewAdapter(getWeatherList()));
            (recyclerView2).setAdapter(
                    new WeatherRecyclerViewAdapterHourly(getWeatherList()));
        }
        return myView;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());
//        mModel.addBlogListObserver(getViewLifecycleOwner(), blogList -> {
//            if (!blogList.isEmpty()) {
//                binding.listRoot.setAdapter(
//                        new WeatherRecyclerViewAdapter(blogList)
//                );
//                binding.layoutWait.setVisibility(View.GONE);
//            }
//        });
    }
    public static List<WeatherData> getWeatherList() {
        WeatherData[] data = new WeatherData[20];
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[] times = {"8:00AM","9:00AM","10:00AM","11:00AM","12:00PM","1:00PM"};
        for (int i = 0; i < data.length; i++) {
            data[i] = new WeatherData.Builder(days[(int) (Math.random() * days.length)]
                    ,"76","windy","Tacoma",times[(int) (Math.random() * times.length)]).build();
        }
        return Arrays.asList(data);
    }
}