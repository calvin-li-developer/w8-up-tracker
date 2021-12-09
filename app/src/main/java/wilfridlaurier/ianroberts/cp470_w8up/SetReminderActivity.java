package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SetReminderActivity extends AppCompatActivity {

    EditText title;
    TextView time;
    TextView date;
    Button timeSet;
    Button dateSet;
    Button setReminder;
    TimePicker chooseTime;
    DatePicker chooseDate;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        title = findViewById(R.id.title);
        time = findViewById(R.id.timeSet);
        date = findViewById(R.id.dateSet);
        timeSet = findViewById(R.id.timeSetButton);
        dateSet = findViewById(R.id.dateSetButton);
        chooseTime = findViewById(R.id.chooseTime);
        chooseDate = findViewById(R.id.chooseDate);
        setReminder = findViewById(R.id.setReminder);


        Date currDateTime = Calendar.getInstance().getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

        time.setText(timeFormat.format(currDateTime));
        date.setText(dateFormat.format(currDateTime));

        timeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate.setVisibility(View.INVISIBLE);
                chooseTime.setVisibility(View.VISIBLE);
            }
        });

        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTime.setVisibility(View.INVISIBLE);
                chooseDate.setVisibility(View.VISIBLE);
            }
        });

        chooseTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Time newTime = new Time(i, i1, 0);
                time.setText(timeFormat.format(newTime));
            }
        });

        chooseDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                date.setText(dateFormat.format(cal.getTime()));
            }
        });

        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reminders.add(time.getText().toString());
                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Reminder", time.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }

}