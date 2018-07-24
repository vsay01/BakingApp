package com.baking.bakingapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StepWS implements Parcelable {
    public Integer id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public StepWS() {
    }

    protected StepWS(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<StepWS> CREATOR = new Parcelable.Creator<StepWS>() {
        @Override
        public StepWS createFromParcel(Parcel source) {
            return new StepWS(source);
        }

        @Override
        public StepWS[] newArray(int size) {
            return new StepWS[size];
        }
    };
}