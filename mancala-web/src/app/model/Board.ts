import { Pit } from "./Pit";

export class Board {
    big_pit: Pit;
    player: string;
    small_pits: Pit[];
  
    constructor(big_pit: Pit, player: string, small_pits: Pit[]) {
      this.big_pit = big_pit;
      this.player = player;
      this.small_pits = small_pits;
    }
  }
  