package br.com.mobile10.avaliasim.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.DetalhesAvaliacao;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapterMyAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingUserInfo;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.User;
import br.com.mobile10.avaliasim.util.ImageUtility;

/**
 * Created by denmont on 20/04/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class ProfileFragment extends Fragment implements RecyclerViewAdapterMyAvaliacoes.OnItemClicked {

    private ImageUtility imageUtility = new ImageUtility();
    private FirebaseAuth mAuth;
    private ImageView imgUser;
    private TextView txtNome, txtEmail;
    private View viewNaoLogado;
    private String userName;

    RecyclerView rView;
    RecyclerViewAdapterMyAvaliacoes rcAdapter;
    View imgVazio;
    FirebaseUser users;
    List<String> idsList = new ArrayList<>();
    List<Avaliacao2> myAvaliacoesList = new ArrayList<>();

    public ProfileFragment() {
        this.setArguments(new Bundle());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        users = mAuth.getCurrentUser();


//        if (user != null) {
//            executeAsyncTaskGetMyListIdsAvaliacoes();
//            executeAsyncTaskGetUserInfo();
//        } else {
//        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton editBtn = view.findViewById(R.id.edit_btn);
        FloatingActionButton logoutBtn = view.findViewById(R.id.logout_btn);

        editBtn.setOnClickListener(this::alertEditInfo);
        logoutBtn.setOnClickListener(this::alertSair);

//        imgVazio = view.findViewById(R.id.vazio);
//        txtNome = view.findViewById(R.id.nome);
//        txtEmail = view.findViewById(R.id.email);
//

//
//        imgUser = rootView.findViewById(R.id.escolher_imagem);
//        imgUser.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                int permissionCheckStorageRead = ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                if(permissionCheckStorageRead != PackageManager.PERMISSION_GRANTED) {
//                    //Permissio
//                    Intent intent2 = new Intent(getActivity(), MainIntroPermission.class);
//                    startActivity(intent2);
//                } else
//                    // Escolhe imagem
//                    startActivityForResult(imageUtility.getPickImageChooserIntent(getActivity()), 200);
//            }
//        });
//
//        rView = rootView.findViewById(R.id.recycler_view);
//        rView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        rView.setHasFixedSize(true);

    }

    //    private void executeAsyncTaskGetMyListIdsAvaliacoes() {
//        new LoadingIdsListMyAvaliacoes(this, users.getUid()).execute();
//    }
//    private void executeAsyncTaskGetMyListAvaliacoes(List<String> idsList) {
//        new LoadingMyAvaliacoes(this, idsList).execute();
//    }
//
    public void executeAsyncTaskGetUserInfo() {
        new LoadingUserInfo(this, users.getUid()).execute();
    }
//
//    public void getListIdMyAvaliacoes(List<String> idsList) {
//        this.idsList = idsList;
//        executeAsyncTaskGetMyListAvaliacoes(idsList);
//    }
//
//    public void setListMyAvaliacoes(List<Avaliacao2> itemList) {
//        // Recebe todos avaliacoes
//        List<Avaliacao2> listMyAvaliacoes = new ArrayList<>();
//
//        for (Avaliacao2 avaliacao : itemList)
//            for (String id : idsList)
//                if (avaliacao.idAvaliacao.equalsIgnoreCase(id))
//                    listMyAvaliacoes.add(avaliacao);
//
//        if (listMyAvaliacoes.size() != 0)
//            this.myAvaliacoesList = listMyAvaliacoes;
//
//        atualizarLista(listMyAvaliacoes);
//    }
//
//    public void atualizarLista(List<Avaliacao2> listAvaliacoes) {
//        try {
//            rcAdapter = new RecyclerViewAdapterMyAvaliacoes(this, listAvaliacoes);
//            if (listAvaliacoes.size() != 0)
//                imgVazio.setVisibility(View.GONE);
//            else
//                imgVazio.setVisibility(View.VISIBLE);
//
//            rView.setItemAnimator(new SlideInUpAnimator());
//            rView.setAdapter(rcAdapter);
//            rcAdapter.setOnClick(this);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onItemClick(int position) {
        //My avaliacoes
        Avaliacao2 av = new Avaliacao2(
                myAvaliacoesList.get(position).author,
                myAvaliacoesList.get(position).cidade,
                myAvaliacoesList.get(position).estado,
                myAvaliacoesList.get(position).features,
                myAvaliacoesList.get(position).idAvaliacao,
                myAvaliacoesList.get(position).timeStemp,
                myAvaliacoesList.get(position).title,
                myAvaliacoesList.get(position).type,
                myAvaliacoesList.get(position).visible,
                myAvaliacoesList.get(position).listaAvaliacoes);

        Intent intent = new Intent(getActivity(), DetalhesAvaliacao.class);
        intent.putExtra("avaliacao", av);
        startActivity(intent);
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            Uri imageUri = imageUtility.getPickImageResultUri(data, getActivity());
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//                uploadFile(bitmap, users.getUid());
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(getActivity(), "Ocorreu um erro. Tente novamente.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public void updateUI(User user) {
////        try {
////            userName = user.nome;
////            txtNome.setText(user.nome);
////            txtEmail.setText(users.getEmail());
////            UrlImageViewHelper.setUrlDrawable(imgUser, user.foto + "", R.drawable.ic_person_black_24dp);
////        } catch (NullPointerException e) {
////            e.printStackTrace();
////        }
//    }
//
//    @Override
//    public void uploadFile(Bitmap bitmap, String userId) {
//        UserDaoImplementacao dao = new UserDaoImplementacao(this);
//        dao.uploadFile(bitmap, userId);
//    }
//
//    @Override
//    public void editUserInfo(User user, String userId) {
//        UserDaoImplementacao dao = new UserDaoImplementacao(this);
//        dao.editUserInfo(user, userId);
//    }
//
    private void alertEditInfo(View v) {

        Toast.makeText(getContext(), "EDIÇÃO", Toast.LENGTH_SHORT).show();

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_edit_info, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);
        final EditText editNome = promptsView.findViewById(R.id.edit_nome);
        editNome.setText(userName);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Editar",
                        (dialog, id) -> {

                            String nome = editNome.getText().toString();
                            User user = new User(nome, null);
//                            editUserInfo(user, users.getUid());

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    executeAsyncTaskGetUserInfo();
                                }
                            }, 1000);

                        })
                .setNegativeButton("Descartar", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }
//

    private void alertSair(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Deslogar da aplicação?");

        alertDialogBuilder
                .setPositiveButton("Deslogar",
                        (dialog, id) -> {
                            mAuth.signOut();
                            getFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                    .replace(R.id.login_fragment, new LoginFragment())
                                    .commit();
                        })
                .setNegativeButton("Cancelar", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }
}
