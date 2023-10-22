import {Component, OnInit} from '@angular/core';
import { Game } from './model/Game';
import { Board } from './model/Board';
import { GameService } from '../app/service/GameService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'mancala-web';
  game: Game | null = null;
  boardPlayer1: Board | undefined = undefined; 
  boardPlayer2: Board | undefined = undefined;
  errorMessage: string | null = null;

  constructor(private gameService: GameService) { }

  ngOnInit() {
      this.createGame();
  }

  createGame() {
    this.gameService.createGame().subscribe(
      (response: Game) => {
        this.game = response;
        this.boardPlayer1 = response.players_board.find((board: Board)  => board.player === 'PLAYER_1');
        this.boardPlayer2 = response.players_board.find((board: Board) => board.player === 'PLAYER_2');
      }
    );
  }

  sowPit(player: string, pitIndex: number) {
    if (this.game)
    this.gameService.sowPit(this.game?.id, player, pitIndex).subscribe({
      next: (response: Game) => {
        this.game = response;
        this.boardPlayer1 = response.players_board.find((board: Board)  => board.player === 'PLAYER_1');
        this.boardPlayer2 = response.players_board.find((board: Board) => board.player === 'PLAYER_2');
      },
      error: (error: any) => {
        this.errorMessage = error.error.message;
      }
    });
  }
}


