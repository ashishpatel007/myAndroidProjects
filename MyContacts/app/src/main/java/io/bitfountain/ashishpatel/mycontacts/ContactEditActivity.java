package io.bitfountain.ashishpatel.mycontacts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ContactEditActivity extends AppCompatActivity {

    public static final String EXTRA = "CEA_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
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

        Contact contact = (Contact) getIntent().getSerializableExtra(EXTRA);


        EditText editName = (EditText) findViewById(R.id.contact_edit_name);
        editName.setText(contact.getName());


        //Get the linear layout here
        LinearLayout phoneNumberSection = (LinearLayout) findViewById(R.id.phonenumber_section);

        for (int i = 0; i < contact.phoneNumbers.size(); i++) {
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(contact.phoneNumbers.get(i));
            phoneNumberSection.addView(et);
        }

    }

}
