package br.com.mobile10.avaliasim.presentation.asyncTask;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.mobile10.avaliasim.presentation.fragments.ProfileFragment;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.util.Constants;


/**
 * Created by denmont on 13/04/2018.
 * Pega os dados do evento geral
 */

public class LoadingUserInfo extends AsyncTask<Void, Void, User> {

    private ProfileFragment fragmentPerfil;
    private String userId;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("users");
    private User user;

    public LoadingUserInfo(ProfileFragment fragmentPerfil, String userId) {
        this.fragmentPerfil = fragmentPerfil;
        this.userId = userId;
    }

    @Override
    protected User doInBackground(Void... voids) {

        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                String nome = (String) dataSnapshot.child("nome").getValue();
//                String foto = (String) dataSnapshot.child("foto").getValue();
//                User user1 = new User(nome, foto);
//                user = user1;
//
//                onPostExecute(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return user;
    }

    @Override
    protected void onPostExecute(User item) {
        super.onPostExecute(item);
//        if(item != null)
//            fragmentPerfil.updateUI(user);
    }
}
