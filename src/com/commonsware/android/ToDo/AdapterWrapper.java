package com.commonsware.android.RateListView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.database.DataSetObserver;
import android.view.KeyEvent;

public class AdapterWrapper implements ListAdapter {
    ListAdapter delegate=null;
    
    public AdapterWrapper(ListAdapter delegate) {
        this.delegate=delegate;
    }
    
    public int getCount() {
        return(delegate.getCount());
    }
    
    public Object getItem(int position) {
        return delegate.getItem(position);
    }
    
    public long getItemId(int position) {
        return(delegate.getItemId(position));
    }
    
    public View getView(int position, View convertView, 
                        ViewGroup parent) {
        return(delegate.getView(position, convertView, parent));
    }
    
    public void registerDataSetObserver(DataSetObserver observer) {
        delegate.registerDataSetObserver(observer);
    }
    
    public boolean hasStableIds() {
        return(delegate.hasStableIds());
    }
    
    public boolean isEmpty() {
        return(delegate.isEmpty());
    }
    
    public int getViewTypeCount() {
        return(delegate.getViewTypeCount());
    }
    
    public int getItemViewType(int position) {
        return(delegate.getItemViewType(position));
    }
    
    public void unregisterDataSetObserver(DataSetObserver observer) {
        delegate.unregisterDataSetObserver(observer);
    }
    
    public boolean areAllItemsEnabled() {
        return(delegate.areAllItemsEnabled());
    }
    
    public boolean isEnabled(int position) {
        return(delegate.isEnabled(position));
    }
}
