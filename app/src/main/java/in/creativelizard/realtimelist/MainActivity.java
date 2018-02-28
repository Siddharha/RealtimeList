package in.creativelizard.realtimelist;

import android.content.ClipData;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private RecyclerView rlItems;
    private Toolbar toolbar;
    private ItemListAdapter itemListAdapter;
    private ArrayList<ListItem> arrayList;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private SwipeRefreshLayout swItems;
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setupToolbar();
        loadList();

    }


    private void loadList() {
        swItems.setRefreshing(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) {
                    arrayList.clear();
                    collectObjectList((Map<String, ListItem>) dataSnapshot.getValue());
                    itemListAdapter.notifyDataSetChanged();

                }else {
                    arrayList.clear();
                    itemListAdapter.notifyDataSetChanged();
                }

                swItems.setRefreshing(false);
                swItems.setEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        itemListAdapter.notifyDataSetChanged();
    }

    private void collectObjectList(Map<String, ListItem> value) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, ListItem> entry : value.entrySet()){

            Map itemX = (Map) entry.getValue();
            //Get phone field and append to list
            //Log.e("item",(String) itemX.get("content"));
            ListItem listItem = new ListItem();
            listItem.setContent((String) itemX.get("content"));
            listItem.setDatetime((String) itemX.get("datetime"));
            listItem.setImportent((boolean) itemX.get("importent"));
            listItem.setId((String) itemX.get("id"));
            arrayList.add(listItem);

        }

    }


    private void initialize() {
        swItems = findViewById(R.id.swItems);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("todoList");
        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this,
                                                        LinearLayoutManager.VERTICAL,
                                                        false);
        rlItems = findViewById(R.id.rlItems);
        toolbar = findViewById(R.id.toolbar);
        itemListAdapter = new ItemListAdapter(arrayList,R.layout.item_list_cell,this);
        rlItems.setLayoutManager(layoutManager);
        rlItems.setAdapter(itemListAdapter);
        rlItems.setItemAnimator(new DefaultItemAnimator());
        rlItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rlItems);



    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(getBaseContext(),AddTodoActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        itemListAdapter.removeItem(position);
    }

    public void removeFromFirebaseDB(ListItem item) {
        myRef.child(item.getId()).removeValue();
    }
}
