package br.com.robertomassoni.mancala.repository.redis.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@RedisHash("BoardEntity")
public class BoardEntity {
    private List<PitEntity> smallPits;
    private PitEntity bigPit;
    private String player;
}
