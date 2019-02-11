package by.home.white.tasks.reclrView;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import by.home.white.tasks.R;
import by.home.white.tasks.entities.Note;

public class ReclrAdapter extends RecyclerView.Adapter<ReclrAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;
        private final CheckBox noteCheckBox;
        private final TextView noteDateView;
        //private final ImageView noteImageView;
        private final TextView noteDateToView;


        private ViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textView);
            noteCheckBox = itemView.findViewById(R.id.checkBox);
            noteDateView = itemView.findViewById(R.id.textViewForDate);
            //noteImageView = itemView.findViewById(R.id.imageView);
            noteDateToView = itemView.findViewById(R.id.textViewForDateTo);


        }
    }

    private final LayoutInflater mInflater;
    private List<Note> mNotes; // Cached copy of words
    private Context mContext;// Cached copy of words
    //private ArrayList<Bitmap> mPhotos;

    public ReclrAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.my_note_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.noteItemView.setText(current.getNote());
            if (current.getPriority().equals(Note.Priority.HIGH.toString())) {
                holder.noteItemView.setTextColor(Color.RED);
            }
            else if (current.getPriority().equals(Note.Priority.MED.toString())) {
                holder.noteItemView.setTextColor(Color.MAGENTA);
            }
            else {
                holder.noteItemView.setTextColor(Color.YELLOW);
            }

            if (current.isChecked()) {
                holder.noteItemView.setPaintFlags(holder.noteItemView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                holder.noteItemView.setPaintFlags(0);
            }
            holder.noteCheckBox.setChecked(current.isChecked());
            SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");

            try {
                Date date = format.parse(current.getDate());
                SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String simpledate = simpleDate.format(date);
                holder.noteDateView.setText(simpledate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                Date Pdate = format.parse(current.getPendingDate());
                SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String cursimpledate = simpleDate.format(Pdate);
                holder.noteDateToView.setText("To: " + cursimpledate);
            } catch (ParseException e) {
                e.printStackTrace();
            }



            //holder.noteImageView.setImageBitmap(current.getPhoto());
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText("No Note");
        }
    }

    public void setNotes(List<Note> words){
        mNotes = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public Note getNoteAtPosition (int position) {
        return mNotes.get(position);
    }
}
