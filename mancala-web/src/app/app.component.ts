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
  playerTurnMessage: string | null = null;

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
        this.playerTurnMessage = "It's player 1's turn!";
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
          this.playerTurnMessage = "It's player 1's turn!";
        } else {
          this.playerTurnMessage = "It's player 2's turn!";
        }


      },
      error: (error: any) => {
        this.toastr.error(error.error.message, 'Error');
      }
    });
  }
}


