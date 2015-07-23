package com.example.contactsearch;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ContentAdapter extends CursorAdapter {

    ContentResolver	resolver;
	public ContentAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		resolver=context.getContentResolver();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView view =null;  
		LayoutInflater vi = null;  
		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		view =(TextView)vi.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);  
		return view; 
	}


	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		((TextView)view).setText(cursor  
                .getString(cursor.getColumnIndex("display_name"))); 
	}
	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		// TODO Auto-generated method stub
		Cursor cursor=resolver.query(MainActivity.uri, MainActivity.projection, "display_name like ?", new String[]{constraint.toString()+"%"}, null);
		return cursor;
	}

}
