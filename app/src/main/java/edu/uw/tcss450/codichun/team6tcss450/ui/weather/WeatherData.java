package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

public class WeatherData {
    private final String mDay;
    private final String mTemp;
    private final String mWind;
    private final String mCity;
    public static class Builder {
        private final String mDay;
        private final String mTemp;
        private final String mWind;
        private final String mCity;


        public Builder(String day, String temp,String wind,String city) {
            this.mDay = day;
            this.mTemp = temp;
            this.mWind = wind;
            this.mCity = city;
        }

        public WeatherData build() {
            return new WeatherData(this);
        }

    }
    private WeatherData(final Builder builder) {
        this.mDay = builder.mDay;
        this.mTemp = builder.mTemp;
        this.mWind = builder.mWind;
        this.mCity = builder.mCity;
    }

    public String getmDay() {
        return mDay;
    }

    public String getmTemp() {
        return mTemp;
    }

    public String getmWind() {
        return mWind;
    }

    public String getmCity() {
        return mCity;
    }
}
