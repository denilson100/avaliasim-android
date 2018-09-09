package br.com.mobile10.avaliasim.data.dao;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.mobile10.avaliasim.data.interfaces.IClockDAO;
import br.com.mobile10.avaliasim.util.Constants;

import static android.content.ContentValues.TAG;

public class ClockDAO implements IClockDAO {

    private DatabaseReference databaseReference;

    public ClockDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DB_ROOT).child("currentDate");
    }

    @Override
    public void synchronizeClock() {
        databaseReference.setValue(System.currentTimeMillis(), (dbError, dbReference) -> {
            if (dbError == null)
                Log.i(TAG, "Sincronizado");
            else
                Log.e(TAG, "Erro na sincronização da hora");
        });
    }
}
