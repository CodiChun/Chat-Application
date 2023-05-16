package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.codichun.team6tcss450.R;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherCardBinding;
import edu.uw.tcss450.codichun.team6tcss450.databinding.FragmentWeatherHourlyBinding;

public class WeatherRecyclerViewAdapterHourly extends RecyclerView.Adapter<WeatherRecyclerViewAdapterHourly.WeatherViewHolder> {
    //Store all of the blogs to present
    private final List<WeatherData> mBlogs;
    public WeatherRecyclerViewAdapterHourly(List<WeatherData> items) {
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
                .inflate(R.layout.fragment_weather_hourly, parent, false));
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
        public FragmentWeatherHourlyBinding binding;
        private WeatherData mBlog;
        public WeatherViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherHourlyBinding.bind(view);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        void setBlog(final WeatherData blog) {
            mBlog = blog;
            binding.textHour.setText(blog.getmTime());
            binding.textWeatherinfo.setText(blog.getmTemp());
        }
    }
}
