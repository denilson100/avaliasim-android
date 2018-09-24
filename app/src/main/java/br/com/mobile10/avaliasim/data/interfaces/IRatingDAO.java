package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.Rating;

public interface IRatingDAO extends IBaseDAO<Rating> {
    void findAllByDeliverableId(String deliverableId, OnCompleteOperationListener onCompleteOperationListener);
}