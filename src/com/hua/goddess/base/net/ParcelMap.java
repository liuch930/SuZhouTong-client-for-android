package com.hua.goddess.base.net;

import java.util.HashMap;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author Evil
 *
 */

public class ParcelMap implements Parcelable {
	
	public Map<String,String> map;
	
	public ParcelMap() {
		this.map=new HashMap<String,String>();
	}

	public ParcelMap(Parcel source) {
		this.map=new HashMap<String,String>();
		readFromParcel(source);
	}
	
	public static final Creator<ParcelMap> CREATOR = new Creator<ParcelMap>(){

		public ParcelMap createFromParcel(Parcel source) {
			return new ParcelMap(source);
		}

		public ParcelMap[] newArray(int size) {
			return new ParcelMap[size];
		}
		
	};

	private void readFromParcel(Parcel in) {
		int count = in.readInt();
		for (int i = 0; i < count; i++) {
			map.put(in.readString(), in.readString());
		}
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(map.size());
        for (String s:map.keySet()) {
            dest.writeString(s);
            dest.writeString(map.get(s));
        }
	}
	
	public String get(String key) {
        return map.get(key);
    }
	
	public int size(){
		return map.size();
	}
 
    public void put(String key, String value) {
        map.put(key, value);
    }
}
