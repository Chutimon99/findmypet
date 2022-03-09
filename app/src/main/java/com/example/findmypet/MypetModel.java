package com.example.findmypet;

public class MypetModel {
    double latitude,longtitude;
    String timestamp;


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp (String timestamp) {
        this.timestamp = timestamp;
    }

    public MypetModel(double latitude, double longtitude , String timestamp ) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.timestamp = timestamp;

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
