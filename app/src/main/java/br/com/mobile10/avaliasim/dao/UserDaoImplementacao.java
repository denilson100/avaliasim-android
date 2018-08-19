package br.com.mobile10.avaliasim.dao;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.mobile10.avaliasim.fragments.FragmentPerfil;
import br.com.mobile10.avaliasim.interfaces.UserDao;
import br.com.mobile10.avaliasim.modelo.User;
import br.com.mobile10.avaliasim.util.Constantes;

public class UserDaoImplementacao implements UserDao {

    private DatabaseReference mDatabase;
    private FragmentPerfil fragmentPerfil;

    public UserDaoImplementacao(FragmentPerfil fragmentPerfil) {
        this.fragmentPerfil = fragmentPerfil;
    }

    @Override
    public void uploadFile(Bitmap bitmap, final String userId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child(userId).child("imgPerfil.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("TAG", "Erro ao enviar foto: " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String urlFoto = "" + downloadUrl;

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                mDatabase = database.getReference(Constantes.DB_ROOT).child("users").child(userId);

                if (urlFoto != "")
                    mDatabase.child("foto").setValue(urlFoto);
                else
                    Log.d("TAG", "Erro ao enviar foto");

                fragmentPerfil.executeAsyncTaskGetUserInfo();
                Log.d("downloadUrl-->", "" + downloadUrl);
            }
        });
    }

    @Override
    public void editUserInfo(final User user, final String userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constantes.DB_ROOT)
                .child("users").child(userId)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    writeNewPost(user, userId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("TAG", "Erro");

                            }
                        });
    }

    private void writeNewPost(User user, String userId) {
        mDatabase.child(Constantes.DB_ROOT)
                .child("users").child(userId).child("nome").setValue(user.nome);
    }
}