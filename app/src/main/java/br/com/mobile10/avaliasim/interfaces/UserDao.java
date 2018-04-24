package br.com.mobile10.avaliasim.interfaces;

import android.graphics.Bitmap;

import br.com.mobile10.avaliasim.modelo.User;

public interface UserDao {
    public void uploadFile(Bitmap bitmap, String userId);

    public void editUserInfo(User user, String userId);
}
