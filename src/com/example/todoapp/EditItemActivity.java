package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String text = getIntent().getStringExtra("text");
		int pos = getIntent().getIntExtra("pos");
		int code = getIntent().getIntExtra("code");
		EditText etTextItem = (EditText) findViewById(R.id.etItem);
		etTextItem.setText(text);
	}
	
	public void applyChanges(View v) {
		EditText etItem = (EditText) findViewById(R.id.etItem);
		Intent data = new Intent();
		data.putExtra("text", etItem.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
