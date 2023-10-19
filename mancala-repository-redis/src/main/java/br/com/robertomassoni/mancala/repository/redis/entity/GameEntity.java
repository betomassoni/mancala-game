package br.com.robertomassoni.mancala.repository.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;
import java.util.UUID;

@Data
@RedisHash("GameEntity")
public class GameEntity {
    @Id
    @Indexed
    private UUID id;
    private List<BoardEntity> playersBoard;
    private String playerTurn;
    private String winner;
    private String status;
}
