package br.com.robertomassoni.mancala.repository.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("BoardEntity")
public class BoardEntity {
    private List<PitEntity> smallPits;
    private PitEntity bigPit;
    private String player;
}
