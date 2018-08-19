package br.com.mobile10.avaliasim.interfaces;

import android.graphics.Bitmap;

import br.com.mobile10.avaliasim.modelo.User;

public interface UserDao {
    void uploadFile(Bitmap bitmap, String userId);
    void editUserInfo(User user, String userId);
}