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

import br.com.mobile10.avaliasim.data.interfaces.IDeliverableDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.OnCompleteOperationListener;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.util.Constants;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EvaluationDAO implements IEvaluationDAO {

    private DatabaseReference databaseReference;

    public EvaluationDAO(String deriverableId) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("evaluations").child(deriverableId);
    }

    @Override
    public void findAll(OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Evaluation> evaluations = new ArrayList<>();
                dataSnapshot.getChildren().forEach(snapshot -> {
                    String authorName = (String) snapshot.child("authorName").getValue();
                    String authorPhoto = (String) snapshot.child("authorPhoto").getValue();

                    List<String> features = new ArrayList<>();
                    for (DataSnapshot snapFeature : snapshot.child("features").getChildren()) {
                        String featute = (String) snapFeature.getValue();
                        features.add(featute);
                    }

                    Evaluation evaluation = new Evaluation();
                    evaluation.setNameAuthor(authorName);
                    evaluation.setPhotoAuthor(authorPhoto);
                    evaluation.setFeatures(features);
                    evaluation.setId(snapshot.getKey());
                    evaluations.add(evaluation);
                });
                onCompleteOperationListener.onCompletion(evaluations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void findById(String deliverableName, OnCompleteOperationListener onCompleteOperationListener) {
        //TODO: gerar id para os produtos e serviços
        databaseReference.child(deliverableName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onCompleteOperationListener.onCompletion(dataSnapshot.getValue(Deliverable.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void create(Deliverable object, OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void delete(String id, OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void update(Deliverable object, OnCompleteOperationListener onCompleteOperationListener) {
    }
}
