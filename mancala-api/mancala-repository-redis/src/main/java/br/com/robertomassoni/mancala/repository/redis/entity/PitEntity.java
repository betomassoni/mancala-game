package br.com.robertomassoni.mancala.repository.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.redis.core.RedisHash;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("PitEntity")
public class PitEntity {
    private Integer index;
    private Integer seedCount;
}
