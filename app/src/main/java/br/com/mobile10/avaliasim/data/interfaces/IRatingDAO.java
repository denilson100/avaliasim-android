package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.Rating;

public interface IRatingDAO {
    void findAll(OnCompleteOperationListener onCompleteOperationListener);
    void findById(String ratingId, OnCompleteOperationListener onCompleteOperationListener);
    void create(Rating rating, OnCompleteOperationListener onCompleteOperationListener);
    void delete(String ratingId, OnCompleteOperationListener onCompleteOperationListener);
    void update(Rating rating, OnCompleteOperationListener onCompleteOperationListener);
}