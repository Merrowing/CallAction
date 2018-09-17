package com.example.ostap.callaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ostap.callaction.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Person> persons;
    ListView listView;
    CustomAdapter mAdapter;
    AddFragment addFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment = new AddFragment();

        listView = (ListView) findViewById(R.id.list_persons);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(addFragment);
            }
        });


        persons = new ArrayList<>();
        try {
            persons = (List<Person>)InternalStorage.readObject(this, "data");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mAdapter = new CustomAdapter(this,persons);
        listView.setAdapter(mAdapter);


    }
    @Override
    protected void onResume(){
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment);
        fragmentTransaction.addToBackStack("addFragment");
        fragmentTransaction.commit();
    }
}
