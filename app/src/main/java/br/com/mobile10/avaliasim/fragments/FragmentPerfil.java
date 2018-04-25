package br.com.mobile10.avaliasim.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.mobile10.avaliasim.R;
import br.com.mobile10.avaliasim.activity.DetalhesAvaliacao;
import br.com.mobile10.avaliasim.activity.Main4Activity;
import br.com.mobile10.avaliasim.activity.MainIntroPermission;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapter;
import br.com.mobile10.avaliasim.adapter.RecyclerViewAdapterMyAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingIdsListMyAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingMyAvaliacoes;
import br.com.mobile10.avaliasim.asyncTask.LoadingUserInfo;
import br.com.mobile10.avaliasim.auth.EmailPasswordActivity;
import br.com.mobile10.avaliasim.dao.UserDaoImplementacao;
import br.com.mobile10.avaliasim.interfaces.UserDao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.User;
import br.com.mobile10.avaliasim.util.ImageUtility;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by denmont on 20/04/2018.
 */

public class FragmentPerfil extends Fragment implements RecyclerViewAdapterMyAvaliacoes.OnItemClicked, UserDao {

    private static final String ARG_SECTION_NUMBER = "section_number";
    View loadingIndicatorMyAvaliacoes;
    View imgVazio;
    List<String> idsList = new ArrayList<>();
    List<Avaliacao2> myAvaliacoesList = new ArrayList<Avaliacao2>();

    public RecyclerView.LayoutManager lLayout;
    RecyclerViewAdapterMyAvaliacoes rcAdapter;
    RecyclerView rView;

    private int PICK_IMAGE_REQUEST = 1;
    private ImageUtility imageUtility = new ImageUtility();

    private FirebaseAuth mAuth;
    FirebaseUser users;
    private ImageView imgUser;
    private TextView txtNome, txtEmail;
    private AppCompatButton btIrParaLogin;
    private View viewNaoLogado;
    private FloatingActionButton fabEdit, fabExit;

    private String userName;

    public static FragmentPerfil newInstance(int sectionNumber) {
        FragmentPerfil fragment = new FragmentPerfil();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
//        loadingIndicatorMyAvaliacoes = (View) rootView.findViewById(R.id.loading_indicator_myavaliacoes);

        imgVazio = (View) rootView.findViewById(R.id.vazio);
        txtNome = (TextView) rootView.findViewById(R.id.nome);
        txtEmail = (TextView) rootView.findViewById(R.id.email);
        viewNaoLogado = (View) rootView.findViewById(R.id.nao_logado);

        btIrParaLogin = (AppCompatButton) rootView.findViewById(R.id.login);
        btIrParaLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getActivity(), EmailPasswordActivity.class);
                startActivity(intent2);
                getActivity().finish();
            }
        });

        fabEdit = (FloatingActionButton) rootView.findViewById(R.id.fab_edit);
        fabEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertEditInfo();
            }
        });

        fabExit = (FloatingActionButton) rootView.findViewById(R.id.fab_exit);
        fabExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertSair();
            }
        });

        imgUser = (ImageView) rootView.findViewById(R.id.escolher_imagem);
        imgUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int permissionCheckStorageRead = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if(permissionCheckStorageRead != PackageManager.PERMISSION_GRANTED) {
                    //Permissio
                    Intent intent2 = new Intent(getActivity(), MainIntroPermission.class);
                    startActivity(intent2);
                } else {
                    // Escolhe imagem
                    startActivityForResult(imageUtility.getPickImageChooserIntent(getActivity()), 200);
                }
            }
        });

        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        users = user;

        if (user != null) {
            executeAsyncTaskGetMyListIdsAvaliacoes();
            executeAsyncTaskGetUserInfo();
        } else {
            viewNaoLogado.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(users != null)
            inflater.inflate(R.menu.menu_user, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sair :
                alertSair();
                break;
            case R.id.action_edit:
                alertEditInfo();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void executeAsyncTaskGetMyListIdsAvaliacoes() {
        new LoadingIdsListMyAvaliacoes(this, users.getUid()).execute();
    }

    private void executeAsyncTaskGetMyListAvaliacoes(List<String> idsList) {
        new LoadingMyAvaliacoes(this, idsList).execute();
    }

    public void executeAsyncTaskGetUserInfo() {
        new LoadingUserInfo(this, users.getUid()).execute();
    }


    public void getListIdMyAvaliacoes(List<String> idsList) {
        this.idsList = idsList;
        executeAsyncTaskGetMyListAvaliacoes(idsList);
    }

    public void setListMyAvaliacoes(List<Avaliacao2> itemList) {
        // Recebe todos avaliacoes
        List<Avaliacao2> listMyAvaliacoes = new ArrayList<>();
        for (Avaliacao2 avaliacao : itemList) {
            for (String id : idsList) {
                if (avaliacao.idAvaliacao.equalsIgnoreCase(id)) {
                    listMyAvaliacoes.add(avaliacao);
                }
            }
        }
        if (listMyAvaliacoes.size() != 0)
            this.myAvaliacoesList = listMyAvaliacoes;
            atualizarLista(listMyAvaliacoes);
    }

    public void atualizarLista(List<Avaliacao2> listAvaliacoes) {
        try {
            rcAdapter = new RecyclerViewAdapterMyAvaliacoes(this, listAvaliacoes);
            if (listAvaliacoes.size() != 0) {
                imgVazio.setVisibility(View.GONE);
            } else {
                imgVazio.setVisibility(View.VISIBLE);
            }
            rView.setItemAnimator(new SlideInUpAnimator());
            rView.setAdapter(rcAdapter);
            rcAdapter.setOnClick(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
//            getActivity().showToast("Erro");
        }
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri =  imageUtility.getPickImageResultUri(data, getActivity());

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                uploadFile(bitmap, users.getUid());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"Ocorreu um erro. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void updateUI(User user) {
        try {
            userName = user.nome;
            txtNome.setText(user.nome);
            txtEmail.setText(users.getEmail());
            UrlImageViewHelper.setUrlDrawable(imgUser, user.foto + "", R.drawable.ic_person_black_24dp);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uploadFile(Bitmap bitmap, String userId) {
        UserDaoImplementacao dao = new UserDaoImplementacao(this);
        dao.uploadFile(bitmap, userId);
    }

    @Override
    public void editUserInfo(User user, String userId) {
        UserDaoImplementacao dao = new UserDaoImplementacao(this);
        dao.editUserInfo(user, userId);
    }

    public void alertEditInfo() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_edit_info, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);
        final EditText editNome = (EditText) promptsView.findViewById(R.id.edit_nome);
        editNome.setText(userName);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Editar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String nome = editNome.getText().toString();
                                User user = new User(nome, null);
                                editUserInfo(user, users.getUid());

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        executeAsyncTaskGetUserInfo();
                                    }
                                }, 1000);

                            }
                        })
                .setNegativeButton("Descartar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }

    public void alertSair() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.alert_sair, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Sair",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    mAuth.signOut();
                                } catch (Exception e) {
                                }
                                Intent intent2 = new Intent(getActivity(), EmailPasswordActivity.class);
                                startActivity(intent2);
                                getActivity().finish();

                            }
                        })
                .setNegativeButton("Descartar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        alertDialog.show();
    }
}
