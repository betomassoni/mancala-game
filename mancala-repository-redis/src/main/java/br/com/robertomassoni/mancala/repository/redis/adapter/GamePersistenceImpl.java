package br.com.robertomassoni.mancala.repository.redis.adapter;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.exception.GameNotFoundException;
import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import br.com.robertomassoni.mancala.repository.redis.mapper.GameEntityMapper;
import br.com.robertomassoni.mancala.repository.redis.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class GamePersistenceImpl implements GamePersistence {

    private final GameRepository repository;

    @Override
    public Game save(Game game) {
        var entity = GameEntityMapper.INSTANCE.mapFrom(game);
        var result = repository.save(entity);
        return GameEntityMapper.INSTANCE.mapFrom(result);
    }

    @Override
    public Game findById(UUID id) {
        var result = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(String.format("Game with id %s not found", id)));
        return GameEntityMapper.INSTANCE.mapFrom(result);
    }
}
