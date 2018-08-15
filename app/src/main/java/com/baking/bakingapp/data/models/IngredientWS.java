package com.baking.bakingapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientWS implements Parcelable {
    public Double quantity;
    public String measure;
    public String ingredient;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    public IngredientWS() {
    }

    public IngredientWS(Parcel in) {
        this.quantity = (Double) in.readValue(Integer.class.getClassLoader());
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<IngredientWS> CREATOR = new Parcelable.Creator<IngredientWS>() {
        @Override
        public IngredientWS createFromParcel(Parcel source) {
            return new IngredientWS(source);
        }

        @Override
        public IngredientWS[] newArray(int size) {
            return new IngredientWS[size];
        }
    };
}