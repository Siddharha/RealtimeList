package in.creativelizard.realtimelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etContent;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private long childIntoList;
    private CheckBox cbImpt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        initialize();
        setupToolbar();
        onActionPerform();
    }

    private void onActionPerform() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etContent.getText().toString().isEmpty()){
                    pushData();
                }
                finish();
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childIntoList = dataSnapshot.getChildrenCount()-1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void pushData() {

        String id = myRef.push().getKey();
        ListItem listItem = new ListItem();
        listItem.setId(id);
        listItem.setContent(etContent.getText().toString());
        listItem.setImportent(cbImpt.isChecked());
        listItem.setDatetime(getDatetimeStamp());
        listItem.setImportent(true);
        myRef.child(id).setValue(listItem);
    }

    private String getDatetimeStamp() {
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return s.format(new Date());
    }

    private void setupToolbar() {
        toolbar.setTitle("Add ToDo List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar);
        etContent = findViewById(R.id.etContent);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("todoList");
        cbImpt = findViewById(R.id.cbImpt);
    }

    @Override
    public void onBackPressed() {
        if(!etContent.getText().toString().isEmpty()){
            pushData();
        }
        finish();
    }


}
