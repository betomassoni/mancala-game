package br.com.robertomassoni.mancala.rest.api.mapper;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.rest.api.response.GameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameResponse mapFrom(final Game source);
}
