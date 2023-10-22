import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from '../model/Game';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  createGame(): Observable<Game> {
    return this.http.post<Game>('http://localhost:8080/api/v1/mancala', null);
  }

  sowPit(gameId: string, player: string, pitIndex: number): Observable<Game> {
    var body = {'game_id': gameId, 'player': player, 'pit_index': pitIndex};
    return this.http.put<Game>('http://localhost:8080/api/v1/mancala', body);
  }
}