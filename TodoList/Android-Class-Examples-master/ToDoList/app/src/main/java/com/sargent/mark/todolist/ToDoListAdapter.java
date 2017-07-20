package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.DBHelper;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;

/**
 * Created by mark on 7/4/17.
 */


public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    // Added parameter ,catergory and status in ItemClickListener method
    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, long id, String category, String status);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // to get refresh todo list data
            this.notifyDataSetChanged();
        }
    }
//Added Todocat,Todostat,category and status as varialbe
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView due;
        TextView descr;
        TextView Todocat;
        CheckBox Todostat;
        String duedate;
        String description;
        String category;
        String status;

        long id;

        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            Todocat = (TextView)  view.findViewById(R.id.MainTextTo);  // gets the textbox of  ID :MainTextTo from layout Xml files
            Todostat = (CheckBox) view.findViewById(R.id.status);    // gets the Checkbox of  ID :status from layout Xml files
            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_TODO_CATEGORY));   // get category from table
            status = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_TODO_STATUS));   // get status from table

            due.setText(duedate);
            descr.setText(description);
            Todocat.setText(category);  // sets the category text to TextView

            //it checks if status is equal to 'Done' then it set checked TODO vice versa
            try {
                if (status.equals("Done")) {
                    Todostat.setChecked(true);
                } else {
                    Todostat.setChecked(false);
                }
            } catch (NullPointerException e) {
                Todostat.setChecked(false);
            }


            Todostat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper helper = new DBHelper(v.getContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    MainActivity.updateTodoStatus(db, id, Todostat.isChecked());
                }
            });

            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            // passed parameter of category and status
            listener.onItemClick(pos, description, duedate, id, category, status);
        }
    }

}
