package com.example.firestoreapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference docRef = db.collection("Users").document("94GPLHfe0jIpNCjgo8sx");
    private final CollectionReference collectionReference = db.collection("Users");
    private Button saveBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button readBtn;
    private TextView textView;
    private EditText nameET;
    private EditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        textView = findViewById(R.id.text);
        saveBtn = findViewById(R.id.SaveBTN);
        deleteBtn = findViewById(R.id.deleteBTN);
        updateBtn = findViewById(R.id.updateBTN);
        readBtn = findViewById(R.id.readBTN);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToNewDocument();
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllDocumentsInCollections();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSpecificDocument();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
            }
        });

    }

    private void saveDataToNewDocument() {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        Friend friend = new Friend(name, email);
        collectionReference.add(friend).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String docId = documentReference.getId();
            }
        });
    }

    private void getAllDocumentsInCollections() {
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                    Friend friend = snapshots.toObject(Friend.class);
                    data += "Name : " + friend.getName() + " Email : " + friend.getEmail() + "\n";
                }
                textView.setText(data);

            }
        });
    }

    private void updateSpecificDocument() {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        docRef.update("name", name);
        docRef.update("email", email);

    }

    private void deleteAll() {
        docRef.delete();
    }
}