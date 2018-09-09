package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.User;

public interface IUserDAO {

    //    void uploadFile(Bitmap bitmap, String userId);

    void findById(String userId, OnCompleteOperationListener onCompleteOperationListener);
    void findSignInMethods(String email, OnCompleteOperationListener onCompleteOperationListener);
    void create(String email, String password, OnCompleteOperationListener onCompleteOperationListener);
    void delete(String userId, OnCompleteOperationListener onCompleteOperationListener);
    void update(User user, OnCompleteOperationListener onCompleteOperationListener);
}