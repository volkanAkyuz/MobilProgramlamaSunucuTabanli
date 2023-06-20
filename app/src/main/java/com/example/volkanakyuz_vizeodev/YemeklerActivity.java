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

public class YemeklerActivity extends AppCompatActivity {

    FirebaseFirestore va_db;
    ArrayList<String> va_menuArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemekler);
        TextView va_title_va = findViewById(R.id.title_va);
        va_title_va.setText("Amasya Yöresel Yemekler");
        va_db = FirebaseFirestore.getInstance();

        DocumentReference va_reference = va_db.collection("Yemekler").document("ZxScIXxUl3z8JrPHc6dN");
        va_reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot va_document = task.getResult();
                    Map<String,Object> va_menu = va_document.getData();
                    va_menuArray = (ArrayList<String>) va_menu.get("isim");
                    if(va_document.exists()){
                        ListView va_listView = findViewById(R.id.va_YoreselYemeklerListView);
                        ArrayAdapter va_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, va_menuArray);
                        va_listView.setAdapter(va_adapter);
                        va_listViewOnClick(va_listView);
                    }
                }

            }
        });
    }
    void va_listViewOnClick(ListView listView_va)
    {
        listView_va.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent va_intent = new Intent(YemeklerActivity.this, YemekActivity.class);
                va_intent.putExtra("va_index", String.valueOf(i));
                startActivity(va_intent);
            }
        });
    }
}