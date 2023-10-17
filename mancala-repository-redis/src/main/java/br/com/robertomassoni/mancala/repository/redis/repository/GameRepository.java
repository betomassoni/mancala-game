package br.com.robertomassoni.mancala.repository.redis.repository;

import br.com.robertomassoni.mancala.repository.redis.entity.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, UUID> {

}
