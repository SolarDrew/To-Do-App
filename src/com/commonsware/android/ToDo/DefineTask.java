package com.commonsware.android.ToDo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DefineTask extends Activity {
    String taskname;
    Button btn;
    EditText task;
    String[] items;
    int position;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.task);
        btn=(Button)findViewById(R.id.done);
        task=(EditText)findViewById(R.id.taskname);
        position=getIntent().getExtras().getInt("position");
        items=getIntent().getExtras().getStringArray("items");
        task.setText(items[position]);
        
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                taskname=task.getText().toString();
                Intent returnStuff=new Intent();
                items[position]=taskname;
                returnStuff.putExtra("items", items);
                setResult(RESULT_OK, returnStuff);
                finish();
            }
        });
    }
}
