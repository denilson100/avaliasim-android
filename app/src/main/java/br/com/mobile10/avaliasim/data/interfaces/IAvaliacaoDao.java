package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.Avaliacao2;

/**
 * Created by denmont on 16/04/2018.
 */

public interface IAvaliacaoDao {
    void newAvaliacao(Avaliacao2 avaliacao, String userId);
}
