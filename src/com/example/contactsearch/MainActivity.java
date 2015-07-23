package com.example.contactsearch;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	static Uri uri = ContactsContract.Contacts.CONTENT_URI;
	static String[] projection = { ContactsContract.Contacts._ID,
			ContactsContract.Contacts.DISPLAY_NAME // ,
	// ContactsContract.CommonDataKinds.Phone.NUMBER
	};
	ContentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView01);
		final TextView tv = (TextView) findViewById(R.id.TextView01);
		final ContentResolver resolver = this.getContentResolver();
		Cursor cursor = resolver.query(uri, projection, null, null, null);
		adapter = new ContentAdapter(this, cursor);
		autoCompleteTextView.setAdapter(adapter);
		autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				autoCompleteTextView.setText(((TextView) view).getText());
				Cursor c = (Cursor) parent.getItemAtPosition(position);
				String contactId = c.getString(c.getColumnIndex("_id"));
				tv.setText(((TextView) view).getText() + ":\n");
				// 查询电话类型的数据操作
				Cursor phones = resolver.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId,

						null, null);
				while (phones.moveToNext()) {
					String phoneNumber = phones.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					// 添加Phone的信息
					tv.append(phoneNumber+"\n");
				}
			}
		});
	}

}
