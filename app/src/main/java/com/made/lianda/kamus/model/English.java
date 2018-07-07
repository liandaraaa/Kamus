package com.made.lianda.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class English implements Parcelable {

    private int id;
    private String word;
    private String translate;

    public English() {
    }

    public English(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public English(int id, String word, String translate) {
        this.id = id;
        this.word = word;
        this.translate = translate;
    }

    protected English(Parcel in) {
        id = in.readInt();
        word = in.readString();
        translate = in.readString();
    }

    public static final Creator<English> CREATOR = new Creator<English>() {
        @Override
        public English createFromParcel(Parcel in) {
            return new English(in);
        }

        @Override
        public English[] newArray(int size) {
            return new English[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(word);
        parcel.writeString(translate);
    }
}
