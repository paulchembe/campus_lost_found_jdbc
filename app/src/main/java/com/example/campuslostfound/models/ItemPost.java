package com.example.campuslostfound.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemPost implements Parcelable {
    public long id;
    public String type;
    public String title;
    public String description;
    public String category;
    public String location;
    public long date;
    public String photoUri;
    public String contact;
    public String status;

    public ItemPost(){}

    protected ItemPost(Parcel in) {
        id = in.readLong();
        type = in.readString();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        location = in.readString();
        date = in.readLong();
        photoUri = in.readString();
        contact = in.readString();
        status = in.readString();
    }

    public static final Creator<ItemPost> CREATOR = new Creator<ItemPost>() {
        @Override
        public ItemPost createFromParcel(Parcel in) { return new ItemPost(in); }
        @Override
        public ItemPost[] newArray(int size) { return new ItemPost[size]; }
    };

    @Override public int describeContents(){return 0;}
    @Override public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(id);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(location);
        dest.writeLong(date);
        dest.writeString(photoUri);
        dest.writeString(contact);
        dest.writeString(status);
    }
}
