package com.akilsrg.mgmcete_mart;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatSection extends AppCompatActivity {

    Button sendBtn;
    EditText msgEt,senderEt;
    ListView listView;
    ArrayAdapter msgAdapter;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_chat_section);


        getSupportActionBar().setTitle("Chat Room");


        myRef = database.getReference("groupchat");





        sendBtn=findViewById(R.id.sendBtn);
        senderEt=findViewById(R.id.senderEt);
        msgEt=findViewById(R.id.msgEt);
        listView=findViewById(R.id.listview);


        ArrayList<String> msgList=new ArrayList<String>();

        msgAdapter=new ArrayAdapter<String>(this,R.layout.listitem,msgList);

        listView.setAdapter(msgAdapter);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sender=senderEt.getText().toString();
                String msg=msgEt.getText().toString();


                if(msg.length()>0 && sender.length()>0)
                {
                    // msgAdapter.add(sender+">"+msg);


                    myRef.push().setValue(sender+">"+msg);


                    msgEt.setText("");
                }



            }
        });




        loadMsg();


    }

    private void loadMsg()
    {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                msgAdapter.add(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}