package com.axon.ars;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import android.R.string;

public class IncidentInfo implements KvmSerializable{

	public int Id;
	public String Latitude;
	public String Longitude;
	public String Title;
	public String Info;
	
	public IncidentInfo(){}
	public IncidentInfo(int id,String latitude,String longitude,String title,String info){
		this.Id=id;
		this.Latitude=latitude;
		this.Longitude=longitude;
		this.Title=title;
		this.Info=info;
	}

    public Object getProperty(int arg0) {
        
        switch(arg0)
        {
        case 0:
            return  Id;
        case 1:
            return Latitude;
        case 2:
            return Longitude;
        case 3:
            return Title;
        case 4:
            return Info;
        }
        
        return null;
    }

    public int getPropertyCount() {
        return 5;
    }

    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
        case 0:
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "Id";
            break;
        case 1:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Latitude";
        case 2:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Longitude";
        case 3:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Title";
            break;
        case 4:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Info";
            break;
        default:break;
        }
    }

    public void setProperty(int index, Object value) {
        switch(index)
        {
        case 0:
            Id=Integer.parseInt(value.toString());
            break;
        case 1:
            Latitude=value.toString();
            break;
        case 2:
            Longitude=value.toString();
            break;
        case 3:
            Title=value.toString();
            break;
        case 4:
            Info=value.toString();
            break;
        default:
            break;
        }
    }



}
