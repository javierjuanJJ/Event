package dam.androidjavierjuanuceda.u3t4event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView tvEventName, tvDate, tvHour;
    private RadioGroup rgPriority;
    private Button dpDate, tpTime, btAccept, btCancel;
    private EditText edPlace;
    private static String[] Months;
    private static Model event;
    static final int FECHA = (1990 - 81 - 9);
    private String priority = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);
        setUI();
    }

    private void setUI() {
        tvEventName = findViewById(R.id.tvEventName);
        tvDate = findViewById(R.id.tvdate);
        tvHour = findViewById(R.id.tvhour);
        btAccept = findViewById(R.id.btAccept);
        btCancel = findViewById(R.id.btCancel);
        rgPriority = findViewById(R.id.rgPriority);
        dpDate = findViewById(R.id.btnDatePickerDialog);
        tpTime = findViewById(R.id.btnTimePickerDialog);
        edPlace = findViewById(R.id.edPlace);

        btAccept.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        rgPriority.setOnCheckedChangeListener(this);

        Months = getResources().getStringArray(R.array.Months);
        if (event != null) {
            try {
                event = new Model((Model) getIntent().getSerializableExtra("EventData"));
                event.setDay(getIntent().getIntExtra("Dia", 28));
                event.setMonth(getIntent().getIntExtra("Mes", 11));
                event.setYear(getIntent().getIntExtra("Anyo", 2019 + FECHA));
            } catch (NullPointerException e) {
                event = new Model(getIntent().getExtras().getString("EventName"));
            }
        } else {
            event = new Model(getIntent().getExtras().getString("EventName"));
        }

        update_event(true);

        //TODO EX4: Llamar a los botones

        dpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
        tpTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora();
            }
        });
    }

    private void obtenerFecha() {
        //TODO EX4: interface obtener la fecha
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                event.setYear(year);
                event.setMonth(month);
                event.setDay(dayOfMonth);
                tvDate.setText(event.getDay() + " de " + Months[event.getMonth()] + " del " + event.getYear());
            }

        }, event.getYear(), event.getMonth(), event.getDay());
        recogerFecha.show();

    }

    private void obtenerHora() {
        //TODO EX4: interface obtener la hora
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                event.setHour(hourOfDay);
                event.setMinute(minute);
                tvHour.setText(event.getHour() + ":" + event.getMinute());
            }
        }, event.getHour(), event.getMinute(), true);

        recogerHora.show();
    }

    @Override
    public void onClick(View v) {
        setResult(RESULT_OK, new Intent().putExtras(new Bundle(eventData())));
        finish();
    }

    private Bundle eventData() {
        Bundle eventData = new Bundle();
        update_event(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) eventData.putSerializable("EventData", event);
        eventData.putString("Eventdata", getString(R.string.textPlace) + event.getPlace() + "\n" + getString(R.string.Priority) + priority + "\n" + getString(R.string.Month) + Months[event.getMonth()] + "\n" + getString(R.string.Day) + event.getDay() + "\n" + getString(R.string.Year) + event.getYear() + "\n" + getString(R.string.Hour) + event.getHour() + ":" + event.getMinute());
        return eventData;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbLow: priority = "Low";break;
            case R.id.rbNormal: priority = "Normal";break;
            case R.id.rbHigh: priority = "High";break;
        }
        event.setPriority(checkedId);
    }

    public void update_event(boolean restore) {
        if (restore) {
            tvEventName.setText(event.getTitle_Event());
            tvDate.setText(event.getDay() + " de " + Months[event.getMonth()] + " del " + event.getYear());
            tvHour.setText(event.getHour() + ":" + event.getMinute());
            rgPriority.check(event.getPriority());
            edPlace.setText(event.getPlace());
        } else {
            event.setTitle_Event(tvEventName.getText().toString());
            event.setPriority(rgPriority.getCheckedRadioButtonId());
            event.setPlace(edPlace.getText().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        update_event(false);
        outState.putSerializable("EventSave", event);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        event = new Model((Model) savedInstanceState.getSerializable("EventSave"));
        update_event(true);
    }
}
