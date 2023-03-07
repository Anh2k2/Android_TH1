package com.example.myapplicationtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> ContacList;
    private EditText etSreach;
    private  Adapter ListAdapter;
    private ListView lstContact;
    Button btxoa,btthem;
    int dem = 0;
    int selectedid = -1;
    private MyDB db;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.idsua:
                Intent intent = new Intent(MainActivity.this,
                        User.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", ContacList.get(selectedid).getId());
                bundle.putString("Name", ContacList.get(selectedid).getName());
                bundle.putString("Phone", ContacList.get(selectedid).getPhone());
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.iddem:
                String[] selectName =  ContacList.get(selectedid).getName().split("\\s");
                for (Contact contact: ContacList
                     ) {
                    String[] partsName = contact.getName().split("\\s");
                    if(selectName[selectName.length-1].compareTo(partsName[partsName.length-1]) == 0){
                        dem++;
                        }
                    }
                String result = "Có " + dem + " người tên " + selectName[selectName.length-1];
                Toast.makeText(MainActivity.this,
                        result, Toast.LENGTH_LONG).show();
                dem=0;
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idtang:
                Collections.sort(ContacList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                lstContact.setAdapter(ListAdapter);
                break;
            case R.id.idgiam:
                Collections.sort(ContacList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o2.getName().compareTo(o1.getName());
                    }
                });
                lstContact.setAdapter(ListAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        if(requestCode==100 && resultCode==200 )
        {
            //đặt vào listData
            db.addContact(new Contact(id,"img1",name, phone, "p1","s1"));
            //ContacList.add(new Contact(id,"img1",name, phone, "p1","s1"));

        }
        else if(requestCode==200 && resultCode==201)
            db.UpdateContact(id,new Contact(id,"img1",name, phone, "p1","s1"));
            //ContacList.set(selectedid,new Contact(id,"img1",name, phone, "p1","s1"));
        //cập nhật adapter
        ListAdapter.notifyDataSetChanged();
        resetData();
    }
    private void resetData(){
        db = new MyDB(MainActivity.this, "ContacDBt",null,1);
        ContacList  = db.GetAllContact();
        ListAdapter = new Adapter(ContacList, MainActivity.this);
        lstContact.setAdapter(ListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContacList = new ArrayList<Contact>();
        ArrayList<Contact> ContacList1 = new ArrayList<Contact>();

        db = new MyDB(this, "ContacDBt",null,1);
//        db.addContact(new Contact(1, "img1", "Nguyen Van An", "0337693148", "p1", "s1"));
//        db.addContact(new Contact(2, "img2","Tran Van Ba", "0337801993", "p2", "s2"));
//        db.addContact(new Contact(3, "img3", "Hoang Thuy Dung", "0337793145", "p3", "s3"));
//        db.addContact(new Contact(4, "img4", "Dinh Thi Tuyen", "0339693148", "p4", "s4"));
//        db.addContact(new Contact(5, "img5", "Dang Van Hien", "0337693479", "p5", "s5"));
        ContacList = db.GetAllContact();

        lstContact = findViewById(R.id.listview);
        ListAdapter = new Adapter(ContacList, this);

        btthem = findViewById(R.id.button);
        btxoa = findViewById(R.id.button2);
        etSreach = findViewById(R.id.editTextTextPersonName);
        lstContact.setAdapter(ListAdapter);
        btthem.setOnClickListener(v -> {
//            Toast.makeText(MainActivity.this,
//                    a.phone, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, User.class);
            startActivityForResult(intent,100);

        });
        btxoa.setOnClickListener((view -> {
            for (int i = 0; i < ContacList.size(); i++) {
                if(ContacList.get(i).isCheck()) {
                    ContacList1.add(ContacList.get(i));
                    db.deleteContact(ContacList.get(i).getId());
                }
            }

            ContacList.removeAll(ContacList1);
            lstContact.setAdapter(ListAdapter);
        }));
        registerForContextMenu(lstContact);


        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedid = position;
                return false;
            }
        });
        etSreach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ListAdapter.getFilter().filter(s.toString());
                ListAdapter.notifyDataSetChanged();
                lstContact.setAdapter(ListAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}