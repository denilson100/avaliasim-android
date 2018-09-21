package br.com.mobile10.avaliasim.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.IOException;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.data.dao.UserDAO;
import br.com.mobile10.avaliasim.model.User;
import br.com.mobile10.avaliasim.presentation.activity.MainIntroPermission;
import br.com.mobile10.avaliasim.presentation.activity.UserEditionActivity;
import br.com.mobile10.avaliasim.util.Alerts;
import br.com.mobile10.avaliasim.util.CodeUtils;
import br.com.mobile10.avaliasim.util.ImageUtility;
import br.com.mobile10.avaliasim.util.InterfaceUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by denmont on 20/04/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ProfileFragment extends Fragment {

    private View fragmentView;
    private ViewSwitcher vs;
    private ImageUtility imageUtility = new ImageUtility();
    private CircleImageView profileImg;
    private TextView profileName;
    private TextView profileEmail;

    private UserDAO userDAO;
    private User loggedUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentView = view;
        userDAO = new UserDAO();
        userDAO.getLoggedUser();
        userDAO.findById(FirebaseAuth.getInstance().getCurrentUser().getUid(), this::onUserLoaded);
    }

    private void onUserLoaded(Object result) {
        if (result != null) {
            loggedUser = (User) result;
            initializeViews(fragmentView);

            profileName.setText(loggedUser.getName());
            profileEmail.setText(loggedUser.getEmail());
            UrlImageViewHelper.setUrlDrawable(profileImg, loggedUser.getPhotoUrl(), R.drawable.ic_account_circle_black_24dp);

        } else {
            loggedUser = new User();
            loggedUser.setName("Sem nome");
            initializeViews(fragmentView);

            profileName.setText(loggedUser.getName());
            profileEmail.setText(loggedUser.getEmail());
            UrlImageViewHelper.setUrlDrawable(profileImg, loggedUser.getPhotoUrl(), R.drawable.ic_account_circle_black_24dp);

        }
    }

    private void initializeViews(View view) {
        profileImg = view.findViewById(R.id.profile_image);
        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);

        view.findViewById(R.id.edit_btn).setOnClickListener(this::onEditBtnClick);
        view.findViewById(R.id.logout_btn).setOnClickListener(this::onLogoutBtnClick);
        profileImg.setOnClickListener(this::onProfileImgClick);
    }

    //TODO: reformular este método
    public void onProfileImgClick(View v) {
        int permissionCheckStorageRead = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheckStorageRead != PackageManager.PERMISSION_GRANTED) {
            Intent intent2 = new Intent(getActivity(), MainIntroPermission.class);
            startActivity(intent2);
        } else
            startActivityForResult(imageUtility.getPickImageChooserIntent(getActivity()), 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //TODO: mudar para content loading progress bar
            ProgressDialog progressDialog = InterfaceUtils.showProgressDialog(getContext(), "Carregando imagem...");
            Uri imageUri = imageUtility.getPickImageResultUri(data, getActivity());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                userDAO.uploadFile(bitmap, loggedUser.getId(), result -> {
                    profileImg.setImageBitmap(bitmap);
                    InterfaceUtils.hideProgressDialog(progressDialog);
                });
            } catch (IOException e) {
                e.printStackTrace();
                Alerts.toast(getActivity(), "Ocorreu um erro. Tente novamente.");
            }
        }
    }

    private void onEditBtnClick(View v) {
        Intent intent = new Intent(v.getContext(), UserEditionActivity.class);
        intent.putExtra("loggedUser", loggedUser);
        v.getContext().startActivity(intent);
    }

    private void onLogoutBtnClick(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Deslogar da aplicação?");

        alertDialogBuilder
                .setPositiveButton("Deslogar",
                        (dialog, id) -> {
                            getFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                    .replace(R.id.profile_fragment, new LoginFragment())
                                    .commit();
                            userDAO.signOut();
                        })
                .setNegativeButton("Cancelar", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }
}
