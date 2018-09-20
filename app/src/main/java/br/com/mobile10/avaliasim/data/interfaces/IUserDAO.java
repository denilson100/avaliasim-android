package br.com.mobile10.avaliasim.data.interfaces;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseUser;

import br.com.mobile10.avaliasim.model.User;

public interface IUserDAO extends IBaseDAO<User> {
    void findSignInMethods(String email, OnCompleteOperationListener onCompleteOperationListener);

    void create(String email, String password, OnCompleteOperationListener onCompleteOperationListener);

    void uploadFile(Bitmap bitmap, String userId, OnCompleteOperationListener onCompleteOperationListener);

    void signIn(String email, String password, OnCompleteOperationListener onCompleteOperationListener);

    void signOut();

    FirebaseUser getLoggedUser();
}