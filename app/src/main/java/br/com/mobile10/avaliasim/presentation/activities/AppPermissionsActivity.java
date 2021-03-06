package br.com.mobile10.avaliasim.presentation.activities;

import android.Manifest;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import br.com.mobile10.avaliasim.R;


/**
 * Created by denmont on 04/02/2018.
 */

public class AppPermissionsActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add slides, edit configuration...
        addSlide(new SimpleSlide.Builder()
                .title("Permissão acesso as Imagens")
                .description("Precisamos de permissão para acessar suas fotos.")
                .image(R.drawable.image_512)
                .background(R.color.branco)
                .backgroundDark(R.color.colorPrimaryDark)
                .scrollable(false)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .build());
    }
}
