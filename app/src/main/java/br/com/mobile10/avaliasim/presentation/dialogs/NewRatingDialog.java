package br.com.mobile10.avaliasim.presentation.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.robertlevonyan.views.chip.Chip;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.FeatureDAO;
import br.com.mobile10.avaliasim.data.interfaces.IFeatureDAO;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewRatingDialog extends Dialog {
    private View view;
    private Activity activity;
    private IFeatureDAO featureDAO;

    public NewRatingDialog(@NonNull Activity context) {
        super(context);
        activity = context;
        featureDAO = new FeatureDAO();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(activity).inflate(R.layout.new_rating_dialog, null);
        setContentView(view);
        featureDAO.findAll(this::onFeaturesLoaded);
    }

    private void onFeaturesLoaded(Object result) {
        List<String> features = (List) result;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, features);
        LinearLayout chipsContainer = findViewById(R.id.chips_container);
        MaterialBetterSpinner materialBetterSpinner = findViewById(R.id.feature_spinner);
        materialBetterSpinner.setAdapter(adapter);

        materialBetterSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO: evitar adicionar o que já estiver na lista
                //Não está sendo possível remover o chip pelo seu listener
                Chip chip = new Chip(activity);
                chip.setChipText(materialBetterSpinner.getText().toString());
                chip.setClosable(true);
                chip.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                chip.setOnCloseClickListener(view -> {
                    Toast.makeText(activity, "TESTE", Toast.LENGTH_SHORT).show();
                    view.post(() -> {
                        chipsContainer.removeView(view);
                        chipsContainer.invalidate();
                        chipsContainer.postInvalidate();
                        chipsContainer.refreshDrawableState();
                        view.invalidate();
                        view.postInvalidate();
                        view.refreshDrawableState();
                    });
                });
                chipsContainer.addView(chip);
                view.invalidate();
            }
        });

        findViewById(R.id.rate_btn).setOnClickListener(this::onRateBtnClick);
    }

    private void onRateBtnClick(View view) {

    }
}
