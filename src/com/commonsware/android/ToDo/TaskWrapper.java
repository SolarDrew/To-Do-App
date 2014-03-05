package com.commonsware.android.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Date;

public class TaskWrapper extends AdapterWrapper {
    Context ctxt=null;
    Date[] dates=null;
    String[] items=null;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private SharedPreferences prefs, listprefs;
    Date today=new Date();
    
    public TaskWrapper(Context ctxt, ListAdapter delegate) {
        super(delegate);
        
        this.ctxt=ctxt;
        this.items=new String[delegate.getCount()];
        this.dates=new Date[delegate.getCount()];
        listprefs=ctxt.getSharedPreferences("lists", 1);
        String listname=listprefs.getString("currentlist",
              "Empty List");
        prefs=ctxt.getSharedPreferences(listname, 0);
        
        for (int i=0;i<delegate.getCount();i++) {
            this.items[i]=prefs.getString("task"+Integer.toString(i),
                  "Task failed to load correctly");
            this.dates[i]=sdf.parse(
                prefs.getString("date"+Integer.toString(i),
                      sdf.format(new Date())), new ParsePosition(0));
        }
    }
    
    public View getView(int position, View convertView, 
          ViewGroup parent) {
        TextView row=(TextView)convertView;
        
        if (convertView==null) {
            row=(TextView)delegate.getView(position, null, parent); 
            if (dates[position].compareTo(today)<1) {
                row.setBackgroundColor(Color.RED);
            }
            else {
                row.setBackgroundColor(Color.GREEN);
                row.setTextColor(Color.BLACK);
            }
        }
        
        row.setText(items[position]);
        
        return(row);
    }
}
