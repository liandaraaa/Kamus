package com.made.lianda.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Indonesia implements Parcelable {

    private int id;
    private String kata;
    private String terjemah;

    public Indonesia() {
    }

    public Indonesia(int id, String kata, String terjemah) {
        this.id = id;
        this.kata = kata;
        this.terjemah = terjemah;
    }

    protected Indonesia(Parcel in) {
        id = in.readInt();
        kata = in.readString();
        terjemah = in.readString();
    }

    public static final Creator<Indonesia> CREATOR = new Creator<Indonesia>() {
        @Override
        public Indonesia createFromParcel(Parcel in) {
            return new Indonesia(in);
        }

        @Override
        public Indonesia[] newArray(int size) {
            return new Indonesia[size];
        }
    };

    public Indonesia(String kata, String terjemah) {
        this.kata = kata;
        this.terjemah = terjemah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getTerjemah() {
        return terjemah;
    }

    public void setTerjemah(String terjemah) {
        this.terjemah = terjemah;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(kata);
        parcel.writeString(terjemah);
    }
}
