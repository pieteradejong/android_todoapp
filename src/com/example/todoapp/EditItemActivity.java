package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private int pos;
	private int code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String text = getIntent().getStringExtra("text");
		pos = getIntent().getExtras().getInt("pos", 0);
		code = getIntent().getIntExtra("code", 0);
		EditText etTextItem = (EditText) findViewById(R.id.etItem);
		// ensure focus on text field
		etTextItem.requestFocus();
		// set text field value
		etTextItem.setText(text);
		// cursor after last character:
		etTextItem.setSelection(etTextItem.getText().length());
	}
	
	public void applyChanges(View v) {
		EditText etItem = (EditText) findViewById(R.id.etItem);
		Intent data = new Intent();
		data.putExtra("text", etItem.getText().toString());
		data.putExtra("pos", pos);
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
