package br.com.robertomassoni.mancala.repository.redis.mapper;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.repository.redis.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameEntityMapper {

    GameEntityMapper INSTANCE = Mappers.getMapper(GameEntityMapper.class);

    Game mapFrom(final GameEntity source);

    GameEntity mapFrom(final Game source);
}
