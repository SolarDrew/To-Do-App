package com.commonsware.android.ToDo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDo extends ListActivity {
    TextView title;
    TextView selected;
    int num_lists=1;
    int selected_num;
    private SharedPreferences prefs;
    String[] items;
    Date[] dates;
    String[] datestrings;
    Date today=new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    public static final int ADD_ITEM=0;
    public static final int REMOVE_ITEM=1;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        prefs=getSharedPreferences("lists", 0);
        int ntasks=prefs.getInt("num_tasks", 1);
        items=new String[ntasks];
        dates=new Date[ntasks];
        for (int i=0;i<items.length;i++) {
            items[i]=prefs.getString("task"+Integer.toString(i),
                                     "Fail");
            dates[i]=sdf.parse(
                prefs.getString("date"+Integer.toString(i),
                                sdf.format(today)),
                new ParsePosition(0));
        };
        
        setContentView(R.layout.main);
        
        setListAdapter(new ArrayAdapter<String>(this, 
              android.R.layout.simple_list_item_1, items));
        
        registerForContextMenu(getListView());
    }
    
    protected void onPause() {
        super.onPause();
        
        prefs=getSharedPreferences("lists", 0);
        SharedPreferences.Editor ed=prefs.edit();
        ed.putInt("num_tasks", items.length);
        for (int i=0;i<items.length;i++) {
            ed.putString("task"+Integer.toString(i), items[i]);
            ed.putString("date"+Integer.toString(i), 
                         sdf.format(dates[i]));
        }
        ed.commit();
    }
    
    public void onListItemClick(ListView parent, View v, 
          int position, long id) {
        Intent intent=new Intent(this, DefineTask.class);
        intent.putExtra("position", position);
        intent.putExtra("items", items);
        datestrings=new String[dates.length];
        for (int i=0; i<dates.length; i++) {
            datestrings[i]=sdf.format(dates[i]);
        };
        intent.putExtra("dates", datestrings);
        startActivityForResult(intent, 1);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, 
          Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                items=data.getExtras().getStringArray("items");
                datestrings=data.getExtras().getStringArray("dates");
                for (int i=0; i<dates.length; i++) {
                    dates[i]=sdf.parse(datestrings[i],
                                       new ParsePosition(0));
                };
                setListAdapter(new ArrayAdapter<String>(this, 
                      android.R.layout.simple_list_item_1, items));
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        populateMenu(menu);
        
        return(super.onCreateOptionsMenu(menu));
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
          ContextMenu.ContextMenuInfo menuInfo) {
        populateContextMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return(applyMenuChoice(item) || 
              super.onOptionsItemSelected(item));
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return(applyMenuChoice(item) || 
              super.onOptionsItemSelected(item));
    }
    
    private void populateMenu(Menu menu) {
        menu.add(Menu.NONE, ADD_ITEM, Menu.NONE, "Add task");
    }
    
    private void populateContextMenu(Menu menu) {
        menu.add(Menu.NONE, REMOVE_ITEM, Menu.NONE, "Remove task");
    }
    
    private boolean applyMenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case ADD_ITEM:
                String[] temp=new String[items.length+1];
                String[] datestrings=new String[dates.length+1];
                Date[] temp2=new Date[dates.length+1];
                for (int i=0;i<items.length;i++) {
                    temp[i]=items[i];
                    temp2[i]=dates[i];
                };
                items=temp;
                dates=temp2;
                int position=items.length-1;
                items[position]="New task";
                dates[position]=new Date();
                for (int i=0;i<items.length;i++) {
                    datestrings[i]=sdf.format(dates[i]);
                };
                
                Intent intent=new Intent(this, DefineTask.class);
                intent.putExtra("position", position);
                intent.putExtra("items", items);
                intent.putExtra("dates", datestrings);
                
                startActivityForResult(intent, 1);
                return(true);
            case REMOVE_ITEM:
                AdapterView.AdapterContextMenuInfo info=
                      (AdapterView.AdapterContextMenuInfo)
                      item.getMenuInfo();
                selected_num=info.position;
                String[] temptasks=new String[items.length-1];
                Date[] tempdates1=new Date[dates.length-1];
                int j=0;
                for (int i=0; i<items.length; i++) {
                    if (i != selected_num) {
                        temptasks[j]=items[i];
                        tempdates1[j]=dates[i];
                        j++;
                    };
                };
                items=temptasks;
                dates=tempdates1;
                setListAdapter(new ArrayAdapter<String>(this, 
                      android.R.layout.simple_list_item_1, items));
        }
        return(false);
    }
}
