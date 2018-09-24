package br.com.mobile10.avaliasim.data.dao;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.data.interfaces.IRatingDAO;
import br.com.mobile10.avaliasim.data.interfaces.OnCompleteOperationListener;
import br.com.mobile10.avaliasim.model.Rating;
import br.com.mobile10.avaliasim.util.Constants;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RatingDAO implements IRatingDAO {

    private DatabaseReference databaseReference;

    public RatingDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("ratings");
    }

    @Override
    public void findAll(OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Rating> ratings = new ArrayList<>();
                dataSnapshot.getChildren().forEach(snapshot -> ratings.add(snapshot.getValue(Rating.class)));
                onCompleteOperationListener.onCompletion(ratings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void findById(String ratingId, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.child(ratingId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onCompleteOperationListener.onCompletion(dataSnapshot.getValue(Rating.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void create(Rating rating, OnCompleteOperationListener onCompleteOperationListener) {
        rating.setId(databaseReference.push().getKey());
        databaseReference.child(rating.getId()).setValue(rating).addOnCompleteListener(task -> onCompleteOperationListener
                .onCompletion(task.isSuccessful() ? 1 : 0));
    }

    @Override
    public void delete(String ratingId, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.child(ratingId).removeValue((dbError, dbReference) -> onCompleteOperationListener
                .onCompletion(dbError == null ? 1 : 0));
    }

    @Override
    public void update(Rating rating, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.child(rating.getId()).setValue(rating, (dbError, dbReferece) -> onCompleteOperationListener
                .onCompletion(dbError == null ? 1 : 0));
    }

    @Override
    public void findAllByDeliverableId(String deliverableId, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.orderByChild("deliverable").equalTo(deliverableId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Rating> ratings = new ArrayList<>();
                dataSnapshot.getChildren().forEach(snapshot -> ratings.add(snapshot.getValue(Rating.class)));
                onCompleteOperationListener.onCompletion(ratings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
