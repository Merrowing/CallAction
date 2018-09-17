package com.example.ostap.callaction;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.ostap.callaction.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Person>{


    private ArrayList listPersons = new ArrayList();
    private Context mContext;

    public CustomAdapter(@NonNull Context context, @LayoutRes List<Person> list){
        super(context, 0 , list);
        mContext = context;
        listPersons = (ArrayList) list;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.people_list_row,parent,false);

        final Person currentPerson = (Person) listPersons.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(currentPerson.getName());

        TextView release = (TextView) listItem.findViewById(R.id.phone_number);
        release.setText(currentPerson.getPhone());

        ImageButton removeButtn = listItem.findViewById(R.id.removeBttn);
        removeButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPersons.remove(currentPerson);
                try {
                    InternalStorage.writeObject(getContext(),"data", listPersons);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        });

        CheckBox box = listItem.findViewById(R.id.checkBox);
        box.setChecked(currentPerson.getCheck());

        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    currentPerson.setCheck(true);
                }else{
                    currentPerson.setCheck(false);
                }
                try {
                    InternalStorage.writeObject(getContext(),"data",listPersons);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        });

        SwipeLayout swipeLayout =  (SwipeLayout)listItem.findViewById(R.id.sample1);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, listItem.findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        return listItem;
    }
}
