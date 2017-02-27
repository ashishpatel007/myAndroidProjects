package io.bitfountain.ashishpatel.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       mContacts = new ArrayList<Contact>();


        // Create 30 contacts
        for (int i = 0; i < 30; i++) {
            Contact contact1 = new Contact();
            contact1.setName("Ashish Patel");
            contact1.emails = new ArrayList<String>();
            contact1.emails.add("matt@parker.com");
            contact1.emails.add("m@parker.com");
            contact1.phoneNumbers = new ArrayList<String>();
            contact1.phoneNumbers.add("18000000000");
            contact1.phoneNumbers.add("18000000001");
            mContacts.add(contact1);

        }
        ListView listView = (ListView) findViewById(R.id.contact_list_view);
        listView.setAdapter(new ContactAdapter(mContacts));

        //Hide the Action Bar when scroll down
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > previousFirstItem) {       //This hides the action bar when firstVisibleItem is higher than 0
                    getSupportActionBar().hide();
                } else if (firstVisibleItem < previousFirstItem) {
                    getSupportActionBar().show();
                }

                previousFirstItem = firstVisibleItem;       //reset
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = mContacts.get(position);
                Intent i = new Intent(ContactListActivity.this, ContactViewActivity.class);
                i.putExtra(ContactViewActivity.EXTRA, contact); //Need to implement Serializable in Contact class
                startActivity(i);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private class ContactAdapter extends ArrayAdapter<Contact> {

        ContactAdapter(ArrayList<Contact> contacts) {
            super(ContactListActivity.this, R.layout.contact_list_row, R.id.contact_row_name, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { //Here we manipulate the view
            convertView = super.getView(position, convertView, parent);

            Contact contact = getItem(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.contact_row_name);
            nameTextView.setText(contact.getName());
            return convertView;
        }
    }
}
