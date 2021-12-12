package com.example.assignment4;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    //Primary Key
    public int id;

    public String title;
    public String image;
    public String description;

    Recipe(){}
    public Recipe(int inputId, String titleInput, String imageInput)
    {
        this.description = "";
        this.id = inputId;
        this.title = titleInput;
        this.image = imageInput;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setRecipe(String recipe) {this.description = recipe;}

    public void setImage(String image){this.image = image;}

    public String getTitle() {return title;}

    public String getImage() {return image;}

    public int getId() {return id;}

    public String getDescription() {return description;}
}
