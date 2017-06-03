package webnet.org.note.delegates;

import android.view.View;

import webnet.org.note.database.NOTE_TABLE;

/**
 * Created by rup on 6/2/17.
 */

public interface INoteClick {
    void onNoteClick(View view, int position, NOTE_TABLE note_table);
    void onDeleteClick(View view ,int position, NOTE_TABLE note_table);
}
