package com.hua.goddess.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class BusSiteVo implements Parcelable {
	private String Name;
	private String NoteGuid;
	private String Canton;
	private String Road;
	private String Sect;
	private String Direct;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNoteGuid() {
		return NoteGuid;
	}

	public void setNoteGuid(String noteGuid) {
		NoteGuid = noteGuid;
	}

	public String getCanton() {
		return Canton;
	}

	public void setCanton(String canton) {
		Canton = canton;
	}

	public String getRoad() {
		return Road;
	}

	public void setRoad(String road) {
		Road = road;
	}

	public String getSect() {
		return Sect;
	}

	public void setSect(String sect) {
		Sect = sect;
	}

	public String getDirect() {
		return Direct;
	}

	public void setDirect(String direct) {
		Direct = direct;
	}

	public static final Parcelable.Creator<BusSiteVo> CREATOR = new Creator<BusSiteVo>() {
		public BusSiteVo createFromParcel(Parcel source) {
			BusSiteVo bsv = new BusSiteVo();
			bsv.Name = source.readString();
			bsv.NoteGuid = source.readString();
			bsv.Canton = source.readString();
			bsv.Road = source.readString();
			bsv.Sect = source.readString();
			bsv.Direct = source.readString();
			return bsv;
		}

		@Override
		public BusSiteVo[] newArray(int size) {
			return new BusSiteVo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Name);
		dest.writeString(NoteGuid);
		dest.writeString(Canton);
		dest.writeString(Road);
		dest.writeString(Sect);
		dest.writeString(Direct);
	}

}
