package br.com.mobile10.avaliasim.data.interfaces;

public interface IBaseDAO<T> {
    void findAll(OnCompleteOperationListener onCompleteOperationListener);

    void findById(String id, OnCompleteOperationListener onCompleteOperationListener);

    void create(T object, OnCompleteOperationListener onCompleteOperationListener);

    void delete(String id, OnCompleteOperationListener onCompleteOperationListener);

    void update(T object, OnCompleteOperationListener onCompleteOperationListener);
}
