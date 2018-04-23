package com.axon.ars;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Feeds implements KvmSerializable{
	
	public String Title;
    public String Description;
    public Feeds(){
    
    }
    public Feeds(String title,String description){
    	this.Title=title;
    	this.Description=description;
    }
 public Object getProperty(int arg0) {
        
        switch(arg0)
        {
        case 0:
            return  Title;
        case 1:
            return Description;
       
        }
        
        return null;
    }

    public int getPropertyCount() {
        return 2;
    }

    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
        case 0:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Title";
        case 1:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Description";
        default:break;
        }
    }

    public void setProperty(int index, Object value) {
        switch(index)
        {
        
        case 0:
            Title=value.toString();
            break;
        case 1:
        	Description=value.toString();
            break;
        default:
            break;
        }
    }



}
