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

public class ToDo extends ListActivity {
    TextView title;
    TextView selected;
    int num_lists=1;
    int selected_num;
    private SharedPreferences prefs;
    String[] items;
    public static final int ADD_ITEM=0;
    public static final int REMOVE_ITEM=1;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        prefs=getSharedPreferences("lists", 0);
        int ntasks=prefs.getInt("num_tasks", 1);
        items=new String[ntasks];
        for (int i=0;i<items.length;i++) {
            items[i]=prefs.getString(Integer.toString(i), "Fail");
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
            ed.putString(Integer.toString(i), items[i]);
        }
        ed.commit();
    }
    
    public void onListItemClick(ListView parent, View v, 
          int position, long id) {
        Intent intent=new Intent(this, DefineTask.class);
        intent.putExtra("position", position);
        intent.putExtra("items", items);
        startActivityForResult(intent, 1);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, 
          Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                items=data.getExtras().getStringArray("items");
                setListAdapter(new ArrayAdapter<String>(this, 
                      android.R.layout.simple_list_item_1, items));
                //String taskname=data.getStringExtra("taskname");
                //selected.setText(taskname);
                //items[selected_num]=taskname;
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
        //menu.add(Menu.NONE, 1, Menu.NONE, "Second option");
    }
    
    private void populateContextMenu(Menu menu) {
        menu.add(Menu.NONE, REMOVE_ITEM, Menu.NONE, "Remove task");
    }
    
    private boolean applyMenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case ADD_ITEM:
                String[] temp=new String[items.length+1];
                for (int i=0;i<items.length;i++) {
                    temp[i]=items[i];
                };
                items=temp;
                int position=items.length-1;
                items[position]="New task";
                Intent intent=new Intent(this, DefineTask.class);
                intent.putExtra("position", position);
                intent.putExtra("items", items);
                startActivityForResult(intent, 1);
                return(true);
            case REMOVE_ITEM:
                AdapterView.AdapterContextMenuInfo info=
                      (AdapterView.AdapterContextMenuInfo)
                      item.getMenuInfo();
                selected_num=info.position;
                String[] temp1=new String[items.length-1];
                int j=0;
                for (int i=0; i<items.length; i++) {
                    if (i != selected_num) {
                        temp1[j]=items[i];
                        j++;
                    };
                };
                items=temp1;
                setListAdapter(new ArrayAdapter<String>(this, 
                      android.R.layout.simple_list_item_1, items));
        }
        return(false);
    }
}
