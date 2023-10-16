package br.com.robertomassoni.mancala.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pit {
    private Integer id;
    private Integer seedCount;

    public void addOneSeed() {
        this.seedCount++;
    }

    public void removeAllSeeds() {
        this.seedCount = 0;
    }

    public void addSeed(final Integer numberOfSeeds) {
        this.seedCount += numberOfSeeds;
    }
}
