package br.com.robertomassoni.mancala.repository.redis.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("PitEntity")
public class PitEntity {
    private Integer index;
    private Integer seedCount;
}
