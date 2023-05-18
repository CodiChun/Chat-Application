package edu.uw.tcss450.codichun.team6tcss450.ui.weather;

public class WeatherData {
    private final String mDay;
    private final String mTemp;
    private final String mWind;
    private final String mCity;
    private final String mTime;
    private final String mDescription;
    private final String mIcon;
    public static class Builder {
        private final String mDay;
        private final String mTemp;
        private final String mWind;
        private final String mCity;
        private final String mTime;

        private final String mDescription;
        private final String mIcon;
        public Builder(String day, String temp,String wind,String city,String time,String description,String icon) {
            this.mDay = day;
            this.mTemp = temp;
            this.mWind = wind;
            this.mCity = city;
            this.mTime = time;
            this.mDescription = description;
            this.mIcon = icon;
        }

        public WeatherData build() {
            return new WeatherData(this);
        }

    }
    //TO BE USED IN ORDER TO INITIALIZE CURRENT DATA SO IT CAN BE OBSERVED PROPERLY
    WeatherData(){
        this.mDay = "null";
        this.mTemp = "null";
        this.mWind = "null";
        this.mCity = "null";
        this.mTime = "null";
        this.mDescription = "null";
        this.mIcon = "null";
    }
    WeatherData(final Builder builder) {
        this.mDay = builder.mDay;
        this.mTemp = builder.mTemp;
        this.mWind = builder.mWind;
        this.mCity = builder.mCity;
        this.mTime = builder.mTime;
        this.mDescription = builder.mDescription;
        this.mIcon = builder.mIcon;
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

    public String getmTime() {
        return mTime;
    }
    public String getmDescription(){return mDescription;}
    public String getmIcon(){return mIcon;}
    public boolean equals(WeatherData other){
        return this.mDay.equals(other.getmDay()) &&
                this.mTime.equals(other.getmTime()) &&
                this.mIcon.equals(other.getmIcon()) &&
                this.mCity.equals(other.getmCity()) &&
                this.mTemp.equals(other.getmTemp()) &&
                this.mWind.equals(other.getmWind());
    }

}
