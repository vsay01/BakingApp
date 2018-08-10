package com.baking.bakingapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BakingWS implements Parcelable {
    public Integer id;
    public String name;
    public List<IngredientWS> ingredients = null;
    public List<StepWS> steps = null;
    public Integer servings;
    public String image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);
    }

    public BakingWS() {
    }

    public BakingWS(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, IngredientWS.class.getClassLoader());
        this.steps = new ArrayList<>();
        in.readList(this.steps, StepWS.class.getClassLoader());
        this.servings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
    }

    public static final Parcelable.Creator<BakingWS> CREATOR = new Parcelable.Creator<BakingWS>() {
        @Override
        public BakingWS createFromParcel(Parcel source) {
            return new BakingWS(source);
        }

        @Override
        public BakingWS[] newArray(int size) {
            return new BakingWS[size];
        }
    };
}