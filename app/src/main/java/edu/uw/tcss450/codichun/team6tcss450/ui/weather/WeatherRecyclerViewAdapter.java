package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherCardBinding;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {
    //Store all of the blogs to present
    private final List<WeatherData> mBlogs;
    public WeatherRecyclerViewAdapter(List<WeatherData> items) {
        this.mBlogs = items;
    }
    @Override
    public int getItemCount() {
        return mBlogs.size();
    }
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new WeatherViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_card, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.setBlog(mBlogs.get(position));
    }
    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherCardBinding binding;
        private WeatherData mBlog;
        public WeatherViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherCardBinding.bind(view);
            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            displayPreview();
        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (binding.textCity.getVisibility() == View.GONE) {
                binding.textCity.setVisibility(View.VISIBLE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.textCity.setVisibility(View.GONE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setBlog(final WeatherData data) {
            Context c = mView.getContext();
            String drawablename = data.getmIcon();
            int drawableId = c.getResources().getIdentifier(drawablename, "drawable", c.getPackageName());
            mBlog = data;
            binding.textDayofweek.setText(data.getmDay());
            binding.textWeatherinfo.setText(data.getmTemp() + "°F \n" + data.getmDescription());
            binding.textCity.setText(data.getmCity());
            binding.imageWeather.setImageResource(drawableId);
            displayPreview();
        }
    }
}
