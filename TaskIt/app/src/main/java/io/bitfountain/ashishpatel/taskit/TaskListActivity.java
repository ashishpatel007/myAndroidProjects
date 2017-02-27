package io.bitfountain.ashishpatel.taskit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListActivity extends Activity {

    private static final String TAG = "TaskListActivity";
    private static final String EXTRA = "TaskExtra";
    private static final int EDIT_TASK_REQUEST = 10;
    private static final int CREATE_TASK_REQUEST = 20;
    private ArrayList<Task> mTasks;
    private int mLastPositionClicked;
    private TaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //Initialize the ArrayList mTasks

        mTasks = new ArrayList<Task>();
        mTasks.add(new Task());
        mTasks.get(0).setName("Task 1");
        mTasks.get(0).setDueDate(new Date());
        mTasks.add(new Task());
        mTasks.get(1).setName("Task 2");
        mTasks.get(1).setDone(true);
        mTasks.add(new Task());
        mTasks.get(2).setName("Task 3");
        mTasks.get(2).setDueDate(new Date());


        ListView listView = (ListView) findViewById(R.id.task_list);
        mAdapter = new TaskAdapter(mTasks);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLastPositionClicked = position;
                Intent i = new Intent(TaskListActivity.this, TaskActivity.class);
                Task task = (Task) parent.getAdapter().getItem(position);
                i.putExtra(TaskListActivity.EXTRA, task);
                startActivityForResult(i, EDIT_TASK_REQUEST);

            }
        });

        listView.getSelectedItemPosition();

        // This is for the contextual action bar
        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);

        registerForContextMenu(listView);   //registers the Context Menu for this view
    }

    /**
     * This is the onActivityResult method
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EDIT_TASK_REQUEST:
                if (resultCode == RESULT_OK) {
                    Task task = (Task) data.getSerializableExtra(TaskActivity.EXTRA);
                    mTasks.set(mLastPositionClicked, task);
                    mAdapter.notifyDataSetChanged();        //update the TaskAdapter view
                }
                break;
            case CREATE_TASK_REQUEST:
                if (resultCode == RESULT_OK) {
                    Task task = (Task) data.getSerializableExtra(TaskActivity.EXTRA);
                    mTasks.add(task);                   //Add the task the list
                    mAdapter.notifyDataSetChanged();    //update the TaskAdapter view

                }
                break;
            default:


        }


    }

    /**
     * This adds items to the Options menu at the top of the app
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_task_list, menu); //Takes the menu and turns it into what is shown on the screen
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_task) {
            Intent i = new Intent(TaskListActivity.this, TaskActivity.class);
            startActivityForResult(i, CREATE_TASK_REQUEST);

            Log.d(TAG, "Clicked add action");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * This is the floating context menu
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_task_list_context, menu);
    }

    /**
     * This is the method for deleting a task with a popup menu
     * when you click and hold on the Task
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_task) {
            //AdapterContextMenuInfo gives us info on what was clicked
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //From there we get the position
            mTasks.remove(menuInfo.position);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    //create an inner class TaskAdapter
    private class TaskAdapter extends ArrayAdapter<Task> {
        TaskAdapter(ArrayList<Task> tasks) {
            super(TaskListActivity.this, R.layout.task_list_row, R.id.task_item_name, tasks); // used our own layout -> task_list_row
            //NOTE: the id had to be referenced for the custom layout
        }

        //This is for customizing by changing to other views or to have data besides toString() results
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView is used to manipulate and access elements inside that view
            convertView = super.getView(position, convertView, parent);
            //gets the index of the Item selected
            Task task = getItem(position);
            //Took the task_item_name layout and converted it into a TextView
            TextView taskName = (TextView) convertView.findViewById(R.id.task_item_name);
            //Set the task names
            taskName.setText(task.getName());

            //Get the checkbox
            CheckBox doneBox = (CheckBox) convertView.findViewById(R.id.task_item_done);
            doneBox.setChecked(task.isDone());

            return convertView;
        }
    }
}
