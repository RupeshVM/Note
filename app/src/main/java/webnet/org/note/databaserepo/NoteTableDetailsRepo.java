package webnet.org.note.databaserepo;

import android.content.Context;

import java.util.List;

import webnet.org.note.context_wrapper.AppController;
import webnet.org.note.database.NOTE_TABLE;
import webnet.org.note.database.NOTE_TABLEDao;

public class NoteTableDetailsRepo {

    public static void insertOrUpdate(Context context, NOTE_TABLE note_table) {
        getNoteTableDao(context).insertOrReplace(note_table);
    }

    public static void clearProfileDetails(Context context) {
        getNoteTableDao(context).deleteAll();
    }

    public static void deleteProfileDetailsWithId(Context context, long id) {
        getNoteTableDao(context).delete(getProfileDetailForId(context, id));
    }

    public static List<NOTE_TABLE> getAllProfileDetails(Context context) {
        return getNoteTableDao(context).loadAll();
    }

    public static NOTE_TABLE getProfileDetailForId(Context context, long id) {
        return getNoteTableDao(context).load(id);
    }

    public static NOTE_TABLEDao getNoteTableDao(Context c) {
        return ((AppController) c.getApplicationContext()).getDaoSession().getNOTE_TABLEDao();
    }


}