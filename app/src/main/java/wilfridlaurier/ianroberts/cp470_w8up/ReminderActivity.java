package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ArrayList<String> reminders;
    ArrayAdapter<String> reminderList;
    ListView rListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        fab = findViewById(R.id.fabAdd);

        reminders = new ArrayList<String>();
        reminderList = new ArrayAdapter<String>(ReminderActivity.this,android.R.layout.simple_list_item_1, reminders);
        rListView = findViewById(R.id.reminderList);
        rListView.setAdapter(reminderList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReminderActivity.this, SetReminderActivity.class);
                startActivityForResult(intent, 10);
            }
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        super.onActivityResult(requestCode, responseCode, data);
        if (responseCode == Activity.RESULT_OK){
            reminderList.add(data.getStringExtra("Reminder"));
        }
    }
}