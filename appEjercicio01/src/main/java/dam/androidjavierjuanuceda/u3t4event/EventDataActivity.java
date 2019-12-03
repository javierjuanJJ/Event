package dam.androidjavierjuanuceda.u3t4event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView tvEventName;
    private RadioGroup rgPriority;
    private DatePicker dpDate;
    private TimePicker tpTime;
    private EditText edPlace;
    private Button btAccept;
    private Button btCancel;
    private String priority = "Normal";
    private static String[] Months;
    private static Model event;
    static final int FECHA = (1990 - 81 - 9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);
        Resources res = getResources();
        Months = res.getStringArray(R.array.Months);

        if (event != null) {
            try {
                event = new Model((Model) getIntent().getSerializableExtra("EventData"));
                event.setDay(getIntent().getIntExtra("Dia", 28));
                event.setMonth(getIntent().getIntExtra("Mes", 11));
                event.setYear(getIntent().getIntExtra("Anyo", 2019 + FECHA));
            } catch (NullPointerException e) {
                event = new Model();
            }
        } else {
            event = new Model();
        }

        setUI();

        Bundle inputData = getIntent().getExtras();
        tvEventName.setText(inputData.getString("EventName"));
        edPlace.setText(event.getPlace());
    }

    private void setUI() {
        tvEventName = findViewById(R.id.tvEventName);
        btAccept = findViewById(R.id.btAccept);
        btCancel = findViewById(R.id.btCancel);
        rgPriority = findViewById(R.id.rgPriority);
        dpDate = findViewById(R.id.dpTime);
        tpTime = findViewById(R.id.tpTime);
        edPlace = findViewById(R.id.edPlace);

        btAccept.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        rgPriority.setOnCheckedChangeListener(this);

        dpDate.updateDate(event.getYear(), event.getMonth(), event.getDay());
        tpTime.setHour(event.getHour());
        tpTime.setMinute(event.getMinute());
        tvEventName.setText(event.getTitle_Event());

        tpTime.setIs24HourView(true);
        rgPriority.check(event.getPriority());
    }

    @Override
    public void onClick(View v) {
        setResult(RESULT_OK, new Intent().putExtras(new Bundle(eventData())));
        finish();
    }

    private Bundle eventData() {
        Bundle eventData = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            eventData.putSerializable("EventData", new Model(tvEventName.getText().toString(), rgPriority.getCheckedRadioButtonId(), edPlace.getText().toString(), dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute(), 0));
        eventData.putString("Eventdata", "PLACE: " + event.getPlace() + "\n Priority: " + priority + "\n" + "Month: " + Months[dpDate.getMonth()] + "\n" + "Day: " + dpDate.getDayOfMonth() + "\n" + "Year: " + dpDate.getYear() + "\n" + "Hour: " + event.getHour() + ":" + event.getMinute());
        return eventData;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbLow:
                priority = "Low";
                break;
            case R.id.rbNormal:
                priority = "Normal";
                break;
            case R.id.rbHigh:
                priority = "High";
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dpDate.setCalendarViewShown(((((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation()) == 3));
    }
}
