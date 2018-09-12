package br.com.mobile10.avaliasim.data.dao;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
                user.setId(authenticationManager.getCurrentUser().getUid());
                user.setName(authenticationManager.getCurrentUser().getDisplayName());
                user.setEmail(authenticationManager.getCurrentUser().getEmail());
                user.setPhotoUrl(authenticationManager.getCurrentUser().getPhotoUrl().toString());
                user.setPhoneNumber(authenticationManager.getCurrentUser().getPhoneNumber());
                databaseReference
                        .child(authenticationManager.getCurrentUser().getUid())
                        .setValue(user)
                        .addOnCompleteListener(listener -> onCompleteOperationListener.onCompletion(listener.isSuccessful() ? 1 : 0));
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

    @Override
    public void uploadFile(Bitmap bitmap, String userId, OnCompleteOperationListener onCompleteOperationListener) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();

        StorageReference profileImageReference = FirebaseStorage.getInstance().getReference()
                .child(userId)
                .child("profileImage.jpg");
        UploadTask uploadTask = profileImageReference.putBytes(data);
        uploadTask
                .addOnFailureListener(ex -> ex.printStackTrace())
                .addOnSuccessListener(taskSnapshot -> databaseReference
                        .child(userId)
                        .child("photoUrl")
                        .setValue(taskSnapshot.getDownloadUrl().toString())
                        .addOnCompleteListener(listener -> onCompleteOperationListener.onCompletion(listener.isSuccessful() ? 1 : 0)));
    }

    @Override
    public void signIn(String email, String password, OnCompleteOperationListener onCompleteOperationListener) {
        authenticationManager.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> onCompleteOperationListener.onCompletion(task.isSuccessful() ? 1 : 0));
    }

    @Override
    public void signOut() {
        authenticationManager.signOut();
    }

    @Override
    public FirebaseUser getLoggedUser() {
        return authenticationManager.getCurrentUser();
    }

    @Override
    public void findAll(OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void create(User object, OnCompleteOperationListener onCompleteOperationListener) {
    }
}