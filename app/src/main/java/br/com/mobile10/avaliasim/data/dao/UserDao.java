package br.com.mobile10.avaliasim.data.dao;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.mobile10.avaliasim.data.interfaces.IUserDao;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.util.Constants;

public class UserDao implements IUserDao {

    private DatabaseReference mDatabase;

    public UserDao() {
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_ROOT);
    }

    //Retornar usuário pelo id
    public User findById(String uid) {
        DatabaseReference users = mDatabase.child("users").child(uid);
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseAuth.getInstance();
                FirebaseDatabase.getInstance();
            }
        });
//        users.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Object o = dataSnapshot.getValue();
//
//                // do your stuff here with value
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {}
//        });
        return new User();
    }


    public int create(User user) {
        return 0;
    }

    public int update(User user) {
        return 0;
    }

    public void delete(String uid) {

    }


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
                if (urlFoto != "")
                    mDatabase.child("foto").setValue(urlFoto);
                else
                    Log.d("TAG", "Erro ao enviar foto");

//                fragmentPerfil.executeAsyncTaskGetUserInfo();
//                Log.d("downloadUrl-->", "" + downloadUrl);
            }
        });
    }

    public void editUserInfo(final User user, final String userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child(Constants.DB_ROOT)
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
        mDatabase.child(Constants.DB_ROOT)
                .child("users").child(userId).child("nome").setValue(user.nome);
    }
}