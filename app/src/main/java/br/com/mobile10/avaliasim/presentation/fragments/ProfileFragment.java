package br.com.mobile10.avaliasim.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.presentation.activity.MainIntroPermission;
import br.com.mobile10.avaliasim.util.CodeUtils;
import br.com.mobile10.avaliasim.util.ImageUtility;
import br.com.mobile10.avaliasim.util.InterfaceUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by denmont on 20/04/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class ProfileFragment extends Fragment {

    private ImageUtility imageUtility = new ImageUtility();
    private FloatingActionButton editBtn;
    private FloatingActionButton logoutBtn;
    private CircleImageView profileImg;
    private TextView profileName;
    private TextView profileEmail;

    private FirebaseAuth authenticationManager;
    private FirebaseUser loggedUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        authenticationManager = FirebaseAuth.getInstance();
        loggedUser = authenticationManager.getCurrentUser();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);

        profileName.setText(loggedUser.getDisplayName());
        profileEmail.setText(loggedUser.getEmail());

        //Transferir para DAO?
        if (loggedUser.getPhotoUrl() != null)
            InterfaceUtils.loadImageFromUrl(CodeUtils.makeURL(loggedUser.getPhotoUrl().toString()), this::onLoadedBitmap);
        else
            profileImg.setImageResource(R.drawable.ic_person_black_24dp);
    }

    private void initializeViews(View view) {
        editBtn = view.findViewById(R.id.edit_btn);
        logoutBtn = view.findViewById(R.id.logout_btn);
        profileImg = view.findViewById(R.id.profile_image);
        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);

        editBtn.setOnClickListener(this::alertEditInfo);
        logoutBtn.setOnClickListener(this::alertSair);
        profileImg.setOnClickListener(this::onProfileImgClick);
    }

    public void onLoadedBitmap(Bitmap bmp) {
        Activity activity = getActivity();
        activity.runOnUiThread(() -> {
            ViewSwitcher vs = activity.findViewById(R.id.profile_view_switcher);
            CircleImageView profileImg = (CircleImageView) vs.getNextView();
            profileImg.setImageBitmap(bmp);
            vs.showNext();
        });
    }

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
//        if (resultCode == Activity.RESULT_OK) {
//            Uri imageUri = imageUtility.getPickImageResultUri(data, getActivity());
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//                UserDaoImplementacao dao = new UserDaoImplementacao(this);
//                dao.uploadFile(bitmap, loggedUser.getUid());
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(getActivity(), "Ocorreu um erro. Tente novamente.", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    public void editUserInfo() {
//        UserDaoImplementacao dao = new UserDaoImplementacao(this);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Diego Victor")
                .setPhotoUri(Uri.parse("https://usatftw.files.wordpress.com/2017/05/spongebob.jpg?w=1000&h=600&crop=1"))
                .build();

        loggedUser.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Toast.makeText(getContext(), "Perfil editado com sucesso", LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Erro!", LENGTH_SHORT).show();
        });

//        dao.editUserInfo(user, userId);
    }

    private void alertEditInfo(View v) {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_edit_info, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);
        final EditText editNome = promptsView.findViewById(R.id.edit_nome);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Editar",
                        (dialog, id) -> {
//
//                            String nome = editNome.getText().toString();
//                            User user = new User(nome, null);
                            editUserInfo();
//
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
////                                    executeAsyncTaskGetUserInfo();
//                                }
//                            }, 1000);

                        })
                .setNegativeButton("Cancelar", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }

    private void alertSair(View v) {
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
                            authenticationManager.signOut();
                        })
                .setNegativeButton("Cancelar", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }
}
