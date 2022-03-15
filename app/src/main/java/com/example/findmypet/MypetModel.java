package com.example.findmypet;

public class MypetModel {
    double latitude,longtitude;
    String timestamp,battery ;


    public String getTimestamp() {
        return timestamp;
    }
    public String getBattery(){ return battery;}

    public void setTimestamp (String timestamp) {
        this.timestamp = timestamp;
    }
    public void setBattery (String battery){this.battery = battery;}
    public MypetModel(double latitude, double longtitude , String timestamp , String battery ) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.timestamp = timestamp;
        this.battery = battery;

    }

    public  double getLatitude() {
        return latitude;
    }

    public void setLatitude( double latitude) {
        this.latitude = latitude;
    }

    public  double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude( double longtitude) {
        this.longtitude = longtitude;
    }

}
