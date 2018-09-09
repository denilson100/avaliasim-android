package br.com.mobile10.avaliasim.data.interfaces;

//Callback passado como parâmetro nas funções CRUD dos DAOs. Define o que fazer no término das operações dos DAOs.
public interface OnCompleteOperationListener {
    void onCompletion(Object result);
}
