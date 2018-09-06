package br.com.mobile10.avaliasim.data.interfaces;

import br.com.mobile10.avaliasim.model.Avaliacao2;

/**
 * Created by denmont on 16/04/2018.
 */

public interface FeatureDao {
    void avaliarFeatures(Avaliacao2 avaliacao, String userId, String status, String featureAvaliada);
}
