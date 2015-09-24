package com.android.BE;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by appslure on 6/19/2015.
 */
public class CategoryBE implements Parcelable{
    public CategoryBE() {

    }

    public CategoryBE(Parcel in)
    {
        readFromParcel(in);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private String title;
    private String content;
    private String publish_date;
    private String publisher;
    private String image_url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.publish_date);
        dest.writeString(this.publisher);
        dest.writeString(this.image_url);

    }
    public void readFromParcel(Parcel in)
    {

        this.title = in.readString();
        this.content = in.readString();
        this.publish_date = in.readString();
        this.publisher = in.readString();
        this.image_url = in.readString();

    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public CategoryBE createFromParcel(Parcel in)
        {
            return new CategoryBE(in);
        }

        @Override
        public Object[] newArray(int size)
        {
            return new CategoryBE[size];
        }
    };
}
