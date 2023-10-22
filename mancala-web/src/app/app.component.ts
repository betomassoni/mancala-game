import {Component, OnInit} from '@angular/core';
import { Game } from './model/Game';
import { Board } from './model/Board';
import { GameService } from '../app/service/GameService';
import { ToastrService } from 'ngx-toastr';

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
  helpMessage: string | null = null;
  winner: string | null = null;
  player1Turn:boolean = true;

  constructor(private gameService: GameService, private toastr: ToastrService) { }

  ngOnInit() {
      this.createGame();
  }

  createGame() {
    this.gameService.createGame().subscribe(
      (response: Game) => {
        this.game = response;
        this.boardPlayer1 = response.players_board.find((board: Board)  => board.player === 'PLAYER_1');
        this.boardPlayer2 = response.players_board.find((board: Board) => board.player === 'PLAYER_2');
        this.helpMessage = "It's player 1's turn!";
        this.player1Turn = true;
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

        if (this.game.player_turn === 'PLAYER_1') {
          this.helpMessage = "It's player 1's turn!";
          this.player1Turn = true;
        } else {
          this.helpMessage = "It's player 2's turn!";
          this.player1Turn = false;
        }

        if (this.game.winner !== undefined) {
          this.helpMessage = this.game.winner === 'PLAYER_1' ? 'Player 1 is the winner!' : 'Player 2 is the winner!';
        }

      },
      error: (error: any) => {
        this.toastr.error(error.error.message, 'Error');
      }
    });
  }
}


