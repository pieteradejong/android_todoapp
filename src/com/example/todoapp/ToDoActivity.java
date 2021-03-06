package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.TextView;


public class ToDoActivity extends Activity {
    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private final int REQUEST_CODE = 20;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }
    
    private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id){
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id){
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("text", ((TextView) item).getText());
				i.putExtra("pos", pos);
				Toast.makeText(getApplicationContext(), ((TextView) item).getText(),
				          Toast.LENGTH_SHORT).show();
                startActivityForResult(i, REQUEST_CODE);
			}
		});
	}

    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	todoAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    	     String text = data.getExtras().getString("text");
    	     int pos = data.getExtras().getInt("pos", 0);
    	     // replace item on the ArrayList, then notify Adapter that data has changed
    	     todoItems.set(pos, text);
    	     todoAdapter.notifyDataSetChanged();
    	     Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    	     // persist changes to todo.txt
			 writeItems();
    	  }
    	} 

    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		todoItems = new ArrayList<String>();
    	}
    }
    
    private void writeItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }
    
}
