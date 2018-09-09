package br.com.mobile10.avaliasim.data.dao;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.mobile10.avaliasim.data.interfaces.IUserDAO;
import br.com.mobile10.avaliasim.data.interfaces.OnCompleteOperationListener;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.util.Constants;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UserDAO implements IUserDAO {

    private DatabaseReference databaseReference;
    private FirebaseAuth authenticationManager;

    public UserDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("users");
        authenticationManager = FirebaseAuth.getInstance();
    }

    @Override
    public void findById(String userId, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onCompleteOperationListener.onCompletion(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void findSignInMethods(String email, OnCompleteOperationListener onCompleteOperationListener) {
        authenticationManager.fetchSignInMethodsForEmail(email).addOnCompleteListener(listener -> {
            List<String> signInMethods = listener.getResult().getSignInMethods();
            AtomicBoolean isRegistered = new AtomicBoolean(false);

            signInMethods.forEach(method -> {
                if ("password".equals(method))
                    isRegistered.set(true);
            });

            onCompleteOperationListener.onCompletion(isRegistered.get() ? 1 : 0);
        });
    }

    @Override
    public void create(String email, String password, OnCompleteOperationListener onCompleteOperationListener) {
        authenticationManager.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            //Cadastrou no Firebase auth
            if (task.isSuccessful()) {
                User user = new User();
                user.setEmail(authenticationManager.getCurrentUser().getEmail());
                databaseReference
                        .child(authenticationManager.getCurrentUser().getUid())
                        .setValue(user)
                        //Cadastrou no banco de usuários do aplicativo
                        .addOnCompleteListener(listener -> onCompleteOperationListener.onCompletion(listener.isSuccessful() ? 1 : 0)
                        );
            }
        });
    }

    @Override
    public void delete(String userId, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.child(userId).removeValue((dbError, dbReference) -> onCompleteOperationListener
                .onCompletion(dbError == null ? 1 : 0));
    }

    @Override
    public void update(User user, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.child(user.getId()).setValue(user, (dbError, dbReferece) -> onCompleteOperationListener
                .onCompletion(dbError == null ? 1 : 0));
    }

    public void uploadFile(Bitmap bitmap, final String userId) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        StorageReference mountainImagesRef = storageRef.child(userId).child("imgPerfil.jpg");
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask = mountainImagesRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//                Log.d("TAG", "Erro ao enviar foto: " + exception);
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                String urlFoto = "" + downloadUrl;
//                if (urlFoto != "")
//                    mDatabase.child("foto").setValue(urlFoto);
//                else
//                    Log.d("TAG", "Erro ao enviar foto");

//                fragmentPerfil.executeAsyncTaskGetUserInfo();
//                Log.d("downloadUrl-->", "" + downloadUrl);
//            }
//        });
    }

    public void editUserInfo(final User user, final String userId) {
//        mDatabase = FirebaseDatabase.getInstance().getReference();


//        mDatabase.child(Constants.DB_ROOT)
//                .child("users").child(userId)
//                .addListenerForSingleValueEvent(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                try {
//                                    writeNewPost(user, userId);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Log.d("TAG", "Erro");
//
//                            }
//                        });
    }
}