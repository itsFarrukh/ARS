package rssPackage;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axon.ars.R;

public class LazyAdapter extends BaseAdapter {
    public static String iid="-1";
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
   
    public static boolean[] selectedContacts;
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
    	
    	activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ViewHolder holder;
       
       
    	
    	
        if(convertView==null)
        {
        	holder = new ViewHolder();
        
        	convertView = inflater.inflate(R.layout.list_layout, null);

            holder.Name = (TextView)convertView.findViewById(R.id.title); // title
            holder.Reviews = (TextView)convertView.findViewById(R.id.artist); // artist name
          
            convertView.setTag(holder);
        }

        else { holder = (ViewHolder) convertView.getTag();
    } 
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
              // Setting all values in listview
              
        holder.Name.setText(song.get("title"));
        holder.Reviews.setText(Html.fromHtml(song.get("desc")));
       

        return convertView;
    }
    class ViewHolder { 
    	TextView Name;
    	TextView Reviews;
    }
}