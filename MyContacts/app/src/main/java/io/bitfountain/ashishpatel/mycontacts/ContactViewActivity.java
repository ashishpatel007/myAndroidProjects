package io.bitfountain.ashishpatel.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactViewActivity extends AppCompatActivity {

    public static final String EXTRA = "CVA_Contact";
    private static final String TAG = "ContactViewActivity";


    private int mColor;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Here pass in an empty point object and display getSize() will populate its data
        Display display = (Display) getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x;
        int height = point.y;

        /**
         * Note: You always need to know what the root element / parent element in the layout is
         */
        //Used ratio sizing to the RelativeLayout instead of ImageView
        RelativeLayout headerSection = (RelativeLayout) findViewById(R.id.contact_view_header);
        //Set the layout parameters here
        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, width * 9 / 16)); //The height is a ratio of the screen. Here we are using 16:9


        Contact contact = (Contact) getIntent().getSerializableExtra(EXTRA);
        TextView contactName = (TextView) findViewById(R.id.contact_view_name);
        contactName.setText(contact.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_view_toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_list);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.contact_view_edit) {
                    Intent i = new Intent(ContactViewActivity.this, ContactEditActivity.class);
                    i.putExtra(ContactEditActivity.EXTRA, mContact);
                    startActivity(i);
                    Log.d(TAG, "I want to edit!!");
                    return true;
                }
                return false;
            }
        });

        ListView listView = (ListView) findViewById(R.id.contact_view_fields);
        listView.setAdapter(new FieldsAdapter(contact.phoneNumbers, contact.emails));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunset); //This is for converting the image to Bitmap
        //For Palette you need to convert the image into a Bitmap
        Palette palette = Palette.generate(bitmap);
        mColor = palette.getDarkVibrantSwatch().getRgb();


    }

    //This is the inner class using BaseAdapter methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    private class FieldsAdapter extends BaseAdapter {

        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> phoneNumbers = new ArrayList<>();

        FieldsAdapter(ArrayList<String> phoneNumbers, ArrayList<String> emails) {
            this.emails = emails;
            this.phoneNumbers = phoneNumbers;

        }

        @Override
        public int getCount() {
            return phoneNumbers.size() + emails.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //If view is empty we have to create it
            if (convertView == null) {
                convertView = ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }

            //get the actual value

            String value = (String) getItem(position);

            TextView contactValue = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            contactValue.setText(value);

            ImageView iv = (ImageView) convertView.findViewById(R.id.field_icon);
            if(isFirst(position)){
                if(isEmail(position)){
                    iv.setImageResource(R.drawable.ic_email);

                }else{
                    iv.setImageResource(R.drawable.ic_call);
                }
            }

            iv.setColorFilter(mColor);


            return convertView;
        }

        private boolean isFirst(int position){
            if(position == 0 || position == phoneNumbers.size()){
                return true;
            }
            return false;

        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (isEmail(position)) {
                return emails.get(position - phoneNumbers.size());
            } else {
                return phoneNumbers.get(position);
            }

        }

        // If there is a phone number then you can enter an email
        private boolean isEmail(int position) {
            if (position > phoneNumbers.size() - 1) {
                return true;

            } else {
                return false;
            }

        }
    }

}
