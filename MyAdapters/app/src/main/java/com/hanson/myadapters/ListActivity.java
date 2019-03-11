package com.hanson.myadapters;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/*
* ListActivity is to display the names of the students to the activity using student array
 */
public class ListActivity extends AppCompatActivity {

    public ListView listView; // listView to populate the list taking inputs from the student array

    DatabaseHelper myDatabaseObject;

    String name; // name is the name of student
    int id; // id is the id from student in a class

    /*
    * This will let user click on any item of the list and will display the data in details activity
     */
    private ListView.OnItemClickListener listItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toastMessage("Position Selected is: "+(position+1));
                Intent intent = new Intent(ListActivity.this,DetailsActivity.class);
                intent.putExtra("id",(position+1));//arrayStudent[position].id);
                startActivity(intent); // take user data from list item to the details activity
        }
    };

    /*
    * This method will execute at the start of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initComponents();
        fillList();
    }

    /*
    * This method is to initialize the components in the list Activity
     */
    public void initComponents(){

        myDatabaseObject = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listView);
        id = 0;
    }

    /**
     * This method to fill the new value in the list and to update when edited
     */
    public void fillList(){
        Cursor data = myDatabaseObject.getStudentData();
        ArrayList<String> listData = new ArrayList<>(); // This instance of ArrayList will take values from the student array
                                                        // will be used to populate the ListView
        while(data.moveToNext()){
            listData.add(data.getString(1)); //(arrayStudent[i].name);
        }
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter); // ListAdapter to show data in the list of Activity
        listView.setOnItemClickListener(listItemListener); // set the click listener to list items
    }

    /*
    * List item to display message to user
     */
    public void toastMessage(String str){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }
}
