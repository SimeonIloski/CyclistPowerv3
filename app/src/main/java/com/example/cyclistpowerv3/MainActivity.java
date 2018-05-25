package com.example.simeon.cyclistpowerv3;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
DatabaseReference url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText name=(EditText) findViewById(R.id.editTextName);
        final EditText email=(EditText) findViewById(R.id.editTextEmail);
        final EditText password=(EditText) findViewById(R.id.editTextPassword);
        Button buttonSignIn=(Button) findViewById(R.id.button1);
        final Button buttonSignUp=(Button) findViewById(R.id.button2);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=name.getText().toString();
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                User user1=new User();
                user1.setEmail(userEmail);
                user1.setName(userName);
                user1.setPassword(userPassword);
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference();
                databaseReference.child("Users").child(user1.getName()).child("email").setValue(user1.getEmail());
                databaseReference.child("Users").child(user1.getName()).child("name").setValue(user1.getName());
                databaseReference.child("Users").child(user1.getName()).child("password").setValue(user1.getPassword());
            }
        });
        url=FirebaseDatabase.getInstance().getReference();
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                String userName=name.getText().toString();
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                User user=new User();
                user.setEmail(userEmail);
                user.setName(userName);
                user.setPassword(userPassword);
                final User user1=user;
                databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean founded=false;
                        for(DataSnapshot d:dataSnapshot.getChildren()){
                            User  user2=d.getValue(User.class);
                            if(user2.equals(user1)){
                                founded=true;
                                Toast.makeText(getApplicationContext(),"Signing in user "+user2.getName(),Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),CyclistSettingsActivity.class);
                                intent.putExtra("username",user2.getName());
                                startActivity(intent);
                                break;
                            }
                           else {
                                founded=false;
                            }
                        }
                        if(!founded){
                            Toast.makeText(getApplicationContext(), "User not found please SignUP", Toast.LENGTH_LONG).show();
                        }
                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    }

