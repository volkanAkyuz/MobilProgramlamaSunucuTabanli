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

public class Tarihce extends AppCompatActivity {
    FirebaseFirestore va_db;
    TextView va_tarihce;
    FirebaseStorage va_fs;
    ImageView va_img;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarihce);
        TextView va_title = findViewById(R.id.title_va);
        va_title.setText("Amasya Tarihçesi");
        va_db = FirebaseFirestore.getInstance();
        va_tarihce = findViewById(R.id.va_aciklama);
        DocumentReference reference = va_db.collection("Tarihce").document("wHEStAkLZu8BNufXz9xo");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String tarihceAciklama = documentSnapshot.get("tarihce").toString();
                    va_tarihce.setText(tarihceAciklama);
                }
            }
        });

        va_fs = FirebaseStorage.getInstance();
        StorageReference va_rfc = va_fs.getReference();
        StorageReference va_rfcDosyaYolu = va_rfc.child("tarihce/amasya_manzara.jpg");

        try{
            final File tempFile = File.createTempFile("tempManzara","jpg");
            va_rfcDosyaYolu.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap va_btmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                    va_img = findViewById(R.id.va_manzaraResim);
                    va_img.setImageBitmap(va_btmap);
                }
            });
        }
        catch (IOException e){
        }
    }
}