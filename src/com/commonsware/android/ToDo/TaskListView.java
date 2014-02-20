package com.commonsware.android.ToDo;

import android.widget.ListView;
import android.widget.ListAdapter;
import android.content.Context;
import android.util.AttributeSet;

public class TaskListView extends ListView {
    public TaskListView(Context context) {
        super(context);
    }

    public TaskListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskListView(Context context, AttributeSet attrs, 
          int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(new TaskWrapper(getContext(), adapter));
    }
}
