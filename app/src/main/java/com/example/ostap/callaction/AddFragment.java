package com.example.ostap.callaction;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ostap.callaction.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddFragment extends Fragment {
    View view;
    EditText name;
    EditText phone;
    Button addButtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_fragment, container, false);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        addButtn = view.findViewById(R.id.addItem);
        addButtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(getContext().getFilesDir(), "data");
                    List<Person> persons = new ArrayList<>();
                    if(file.exists()){
                        persons = (List<Person>) InternalStorage.readObject(getContext(), "data");
                    }
                    persons.add(new Person(name.getText().toString(), phone.getText().toString(), true));
                    InternalStorage.writeObject(getContext(), "data", persons);
                    name.getText().clear();
                    phone.getText().clear();
                    Toast toast = Toast.makeText(getContext(), "Add new record", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.clearFocus();
                getActivity().recreate();
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
