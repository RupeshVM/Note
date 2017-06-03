package webnet.org.note.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import webnet.org.note.Adapter.NoteAdapter;
import webnet.org.note.R;
import webnet.org.note.database.NOTE_TABLE;
import webnet.org.note.databaserepo.NoteTableDetailsRepo;
import webnet.org.note.delegates.INoteClick;

public class MainActivity extends AppCompatActivity implements INoteClick {

    private LinearLayout activityMain;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<NOTE_TABLE> note_tables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        noteAdapter = new NoteAdapter(note_tables, getApplicationContext());
        noteAdapter.setNoteClick(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
        addData();
    }

    private void addData() {
        note_tables.addAll(NoteTableDetailsRepo.getAllProfileDetails(getApplicationContext()));
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivityForResult(new Intent(this, AddNoteActivity.class).putExtra("FLAG", 2).putExtra("ID", 0), 1000);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case 1000:
                        note_tables.clear();
                        addData();
                        break;
                }
                break;
        }
    }

    @Override
    public void onNoteClick(View view, int position, NOTE_TABLE note_table) {

        startActivityForResult(new Intent(this, AddNoteActivity.class).putExtra("FLAG", 1).putExtra("ID", note_table.getId()), 1000);
    }

    @Override
    public void onDeleteClick(View view, int position, NOTE_TABLE note_table) {
        NoteTableDetailsRepo.deleteProfileDetailsWithId(getApplicationContext(),note_table.getId());
        note_tables.clear();
        addData();

    }
}
