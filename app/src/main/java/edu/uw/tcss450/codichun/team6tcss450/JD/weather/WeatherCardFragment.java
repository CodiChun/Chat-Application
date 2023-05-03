package edu.uw.tcss450.codichun.team6tcss450.JD.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.tcss450.codichun.team6tcss450.R;

public class WeatherCardFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        BlogPostFragmentArgs args = BlogPostFragmentArgs.fromBundle(getArguments());
//
//        FragmentWeatherCardBinding binding = FragmentWeatherCardBinding.bind(getView());
//
//        binding.textPubdate.setText(args.getBlog().getPubDate());
//        binding.textTitle.setText(args.getBlog().getTitle());
//
//        final String preview =  Html.fromHtml(
//                        args.getBlog().getTeaser(),
//                        Html.FROM_HTML_MODE_COMPACT)
//                .toString();
//        binding.textPreview.setText(preview);
//
//        //Note we are using an Intent here to start the default system web browser
//        binding.buttonUrl.setOnClickListener(button ->
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(args.getBlog().getUrl()))));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_card, container, false);
    }
}
