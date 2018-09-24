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

import br.com.mobile10.avaliasim.data.interfaces.IFeatureDAO;
import br.com.mobile10.avaliasim.data.interfaces.OnCompleteOperationListener;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.util.Constants;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FeatureDAO implements IFeatureDAO {

    private DatabaseReference databaseReference;

    public FeatureDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("features");
    }

    @Override
    public void findAll(OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> features = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    features = (ArrayList) snapshot.getValue();
                onCompleteOperationListener.onCompletion(features);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void findById(String id, OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void create(Feature object, OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void delete(String id, OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void update(Feature object, OnCompleteOperationListener onCompleteOperationListener) {
    }
}
