package com.example.volkanakyuz_vizeodev;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class TarihiYerActivity extends AppCompatActivity {

    FirebaseFirestore va_db;
    ArrayList<String> va_menuArrayAd;
    ArrayList<String> va_menuArrayAciklama;
    FirebaseStorage va_storage;
    StorageReference va_storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarihi_yer);
        TextView va_title = findViewById(R.id.title_va);
        va_title.setText("Tarihi Mekanlar");
        String va_index = getIntent().getStringExtra("va_index");
        ImageView va_imageView = findViewById(R.id.va_mekanimage);
        TextView va_isim_txt = findViewById(R.id.va_mekanisim);
        TextView va_bilgi_txt = findViewById(R.id.va_mekanbilgi);

        va_db = FirebaseFirestore.getInstance();
        va_storage = FirebaseStorage.getInstance();
        va_storageReference = va_storage.getReference();

        DocumentReference va_reference = va_db.collection("TarihiMekanlar").document("A7iuoizeZHmLXpIEaRYK");
        va_reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot va_document = task.getResult();
                    Map<String,Object> va_menu = va_document.getData();
                    va_menuArrayAd = (ArrayList<String>) va_menu.get("isim");
                    va_menuArrayAciklama = (ArrayList<String>) va_menu.get("aciklama");
                    if(va_document.exists()){
                        StorageReference va_imageReference = va_storageReference.child("mekanlar/mekan"+va_index+".jpg");
                        try{
                            final File va_tempFile = File.createTempFile("tempManzara","jpg");
                            va_imageReference.getFile(va_tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap va_btmap = BitmapFactory.decodeFile(va_tempFile.getAbsolutePath());
                                    va_imageView.setImageBitmap(va_btmap);
                                }
                            });
                        }
                        catch (IOException e){
                        }
                        if(va_index!=null){
                            int va_kontrol = Integer.parseInt(va_index);
                            va_isim_txt.setText(va_menuArrayAd.get(va_kontrol));
                            va_bilgi_txt.setText(va_menuArrayAciklama.get(va_kontrol));
                        }

                    }
                }
            }
        });
    }
}



