package com.commonsware.android.ToDo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefineTask extends Activity {
    String taskname;
    Button btn;
    EditText task;
    DatePicker date;
    String[] items;
    String[] dates;
    int position;
    Date olddate;
    Date newdate;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.task);
        btn=(Button)findViewById(R.id.done);
        task=(EditText)findViewById(R.id.taskname);
        date=(DatePicker)findViewById(R.id.datepicker);
        position=getIntent().getExtras().getInt("position");
        items=getIntent().getExtras().getStringArray("items");
        dates=getIntent().getExtras().getStringArray("dates");
        task.setText(items[position]);
        olddate=sdf.parse(dates[position], new ParsePosition(0));
        date.updateDate(1900+olddate.getYear(), olddate.getMonth(),
              olddate.getDate());
        
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                taskname=task.getText().toString();
                Intent returnStuff=new Intent();
                items[position]=taskname;
                newdate=new Date(date.getYear()-1900,
                                 date.getMonth(),
                                 date.getDayOfMonth());
                dates[position]=sdf.format(newdate);
                returnStuff.putExtra("items", items);
                returnStuff.putExtra("dates", dates);
                setResult(RESULT_OK, returnStuff);
                finish();
            }
        });
    }
}
