package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.User;

public interface IUserDao {
//    void uploadFile(Bitmap bitmap, String userId);
//    void editUserInfo(User user, String userId);

    User findById(String uid);
    int create(User user);
    int update(User user);
    void delete(String uid);
}