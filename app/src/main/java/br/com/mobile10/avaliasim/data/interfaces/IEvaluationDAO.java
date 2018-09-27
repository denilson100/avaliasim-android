package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.Evaluation;

public interface IEvaluationDAO extends IBaseDAO<Evaluation> {

    void findTotalEvaluation(Evaluation objec, OnCompleteOperationListener onCompleteOperationListener);

    void avaliar(Evaluation object, String userId, String featureAvaliada, String status, OnCompleteOperationListener onCompleteOperationListener);


}
