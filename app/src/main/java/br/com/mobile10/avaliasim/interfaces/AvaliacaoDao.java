package br.com.mobile10.avaliasim.interfaces;

import br.com.mobile10.avaliasim.modelo.Avaliacao;
import br.com.mobile10.avaliasim.modelo.Avaliacao2;
import br.com.mobile10.avaliasim.modelo.Produto;

/**
 * Created by denmont on 16/04/2018.
 */

public interface AvaliacaoDao {
    void newAvaliacao(Avaliacao2 avaliacao, String userId);
}
