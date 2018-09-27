package br.com.mobile10.avaliasim.data.dao;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.mobile10.avaliasim.data.interfaces.IDeliverableDAO;
import br.com.mobile10.avaliasim.data.interfaces.IEvaluationDAO;
import br.com.mobile10.avaliasim.data.interfaces.OnCompleteOperationListener;
import br.com.mobile10.avaliasim.model.Deliverable;
import br.com.mobile10.avaliasim.model.Evaluation;
import br.com.mobile10.avaliasim.model.Feature;
import br.com.mobile10.avaliasim.model.MyDate;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.model.Vote;
import br.com.mobile10.avaliasim.util.Constants;
import br.com.mobile10.avaliasim.util.Format;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EvaluationDAO implements IEvaluationDAO {

    private DatabaseReference databaseReference;
    private Evaluation evaluation;
    private String deriverableId;
    private String total;
    private int result;

    public EvaluationDAO(String deriverableId) {
        this.deriverableId = deriverableId;
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
                    List<Feature> featureList = new ArrayList<Feature>();
                    for (DataSnapshot list : snapshot.child("ratingList").getChildren()) {

                        String featureName = list.getKey();

                        List<MyDate> listDate = new ArrayList<MyDate>();
                        for (DataSnapshot data : list.getChildren()) {

                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = null;
                            String stringDate = data.getKey();
                            try {
                                date = formatter.parse(stringDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            int positive = (int) data.child("positive").getChildrenCount();
                            int negative = (int) data.child("negative").getChildrenCount();

                            MyDate myDate1 = new MyDate(positive, negative, date);
                            listDate.add(myDate1);

                        }

                        Feature feature = new Feature(featureName, listDate);
                        featureList.add(feature);

                    }

                    Evaluation evaluation = new Evaluation();
                    evaluation.setNameAuthor(authorName);
                    evaluation.setPhotoAuthor(authorPhoto);
                    evaluation.setFeatures(features);
                    evaluation.setFeaturesList(featureList);
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
    public void findTotalEvaluation(Evaluation evaluation, OnCompleteOperationListener onCompleteOperationListener) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("evaluations").child(Constants.DELIVERABLE.getId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot av : dataSnapshot.getChildren()) {

                    if (evaluation.getId().equalsIgnoreCase(av.getKey())) {

                        for (DataSnapshot list : av.child("ratingList").getChildren()) {

                            for (DataSnapshot feature : list.getChildren()) {

                                result += (int) feature.child("positive").getChildrenCount();
                                result += (int) feature.child("negative").getChildrenCount();
                                total = "Total: " + result + " avaliações";

                            }

                        }

                    }

                }

                onCompleteOperationListener.onCompletion(total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void avaliar(Evaluation object, String userId, String featureAvaliada, String status, OnCompleteOperationListener onCompleteOperationListener) {
        String dataAtual = Format.Date(Constants.DATA_ATUAL);
        String key = databaseReference.child("ratingList").child(featureAvaliada).child(dataAtual).child(status).child(userId).getKey();
        Vote voto = new Vote(userId);
        Map<String, Object> postValuesVoto = voto.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        String caminhoPrivado = "/" + object.getId() + "/ratingList/" + featureAvaliada + "/" + dataAtual + "/" + status + "/";
        childUpdates.put(caminhoPrivado + key, postValuesVoto);

        databaseReference.updateChildren(childUpdates);

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
    public void create(Evaluation object, OnCompleteOperationListener onCompleteOperationListener) {
        if (object != null) {
            String key = databaseReference.child(Constants.DB_ROOT).push().getKey();
            Map<String, Object> postValues = object.toMap();
            databaseReference.child(key).setValue(postValues)
                    .addOnCompleteListener(listener -> onCompleteOperationListener.onCompletion(listener.isSuccessful() ? 1 : 0));
        }
    }

    @Override
    public void delete(String id, OnCompleteOperationListener onCompleteOperationListener) {
    }

    @Override
    public void update(Evaluation object, OnCompleteOperationListener onCompleteOperationListener) {
    }
}
