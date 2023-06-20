package com.example.volkanakyuz_vizeodev;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore va_db;
    ArrayList<String> va_menuArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView va_title = findViewById(R.id.title_va);
        va_title.setText("ANA MENÜ");
        va_db = FirebaseFirestore.getInstance();
        DocumentReference va_reference = va_db.collection("AnaMenu").document("AZh52nsrtezFRfXUoSYv");
        va_reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot va_document = task.getResult();
                    Map<String, Object> va_menu = va_document.getData();
                    va_menuArray = (ArrayList<String>) va_menu.get("isim");
                    if (va_document.exists()) {
                        ListView va_listView = findViewById(R.id.va_mainMenuListView);
                        ArrayAdapter va_arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, va_menuArray);
                        va_listView.setAdapter(va_arrayAdapter);
                        va_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent va_intent;
                                switch (i) {
                                    case 0:
                                        va_intent = new Intent(MainActivity.this, Tarihce.class);
                                        break;
                                    case 1:
                                        va_intent = new Intent(MainActivity.this, TarihiMekanlarActivity.class);
                                        break;
                                    case 2:
                                        va_intent = new Intent(MainActivity.this, YemeklerActivity.class);
                                        break;
                                    case 3:
                                    default:
                                        va_intent = new Intent(MainActivity.this, TatlilarActivity.class);
                                        break;
                                }
                                startActivity(va_intent);
                            }
                        });

                    }
                }
            }
        });
    }
}