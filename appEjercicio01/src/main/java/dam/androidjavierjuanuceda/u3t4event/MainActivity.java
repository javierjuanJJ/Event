package dam.androidjavierjuanuceda.u3t4event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int REQUEST = 0;
    private EditText etEventName;
    private TextView tvCurrentData;
    private static Model event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
    }

    private void setUI() {
        etEventName = findViewById(R.id.etEventName);
        tvCurrentData = findViewById(R.id.btEditEventData);
        tvCurrentData.setText("");
        try {
            event = new Model((Model) getIntent().getSerializableExtra("EventData"));
            tvCurrentData.setText(getIntent().getStringExtra("Eventdata"));
        } catch (NullPointerException e) {
            tvCurrentData.setText("");
        }
    }

    public void editEventData(View view) {
        //TODO EX1: Bundle
        Intent intent = new Intent(this, EventDataActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("EventName", etEventName.getText().toString());
        intent.putExtras(bundle);
        intent.putExtra("EventData", event);

        if (event != null) {
            intent.putExtra("Dia", event.getDay());
            intent.putExtra("Mes", event.getMonth());
            intent.putExtra("Anyo", event.getYear());
        }
        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            tvCurrentData.setText(data.getStringExtra("Eventdata"));
            event = (Model) data.getSerializableExtra("EventData");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //TODO ex1: Save
        super.onSaveInstanceState(outState);
        outState.putString("Event_save", tvCurrentData.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //TODO ex1: Restore
        super.onRestoreInstanceState(savedInstanceState);
        tvCurrentData.setText(savedInstanceState.getString("Event_save"));
    }
}
