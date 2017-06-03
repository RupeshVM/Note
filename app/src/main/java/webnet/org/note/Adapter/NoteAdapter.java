package webnet.org.note.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import webnet.org.note.R;
import webnet.org.note.database.NOTE_TABLE;
import webnet.org.note.delegates.INoteClick;
import webnet.org.note.utils.Utility;

/**
 * Created by rup on 6/2/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NOTE_TABLE> note_tables;
    Context context;
    INoteClick noteClick;

    public NoteAdapter(List<NOTE_TABLE> note_tables, Context context) {
        this.note_tables = note_tables;
        this.context = context;
    }

    public INoteClick getNoteClick() {
        return noteClick;
    }

    public void setNoteClick(INoteClick noteClick) {
        this.noteClick = noteClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NOTE_TABLE note_table = note_tables.get(position);
        holder.title.setText(note_table.getTITLE());
        holder.desctiption.setText(note_table.getDESCRIPTION());
        Timestamp stamp = new Timestamp(Long.parseLong(note_table.getDATE()));
        Date date = new Date(stamp.getTime());
        holder.date.setText("" + date);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteClick.onNoteClick(v, position, note_table);
            }
        });

        Bitmap bitmap = Utility.decodeBase64(note_table.getIMAGE());
        holder.imageView.setImageBitmap(bitmap);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteClick.onDeleteClick(v, position, note_table);
            }
        });

    }

    @Override
    public int getItemCount() {
        return note_tables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title;
        private TextView desctiption;
        private TextView date;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);

            desctiption = (TextView) view.findViewById(R.id.desctiption);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
