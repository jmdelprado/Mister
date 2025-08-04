import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from '../../../models/player';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  
  private apiUrl:string = 'https://mister-ei6q.onrender.com/mister/players';

  constructor(private http:HttpClient){}

  getPlayers():Observable<Player[]>{
    return this.http.get<Player[]>(this.apiUrl);
  }

  searchPlayers(searhTerm:string):Observable<Player[]>{
    return this.http.get<Player[]>(this.apiUrl+'/search/'+searhTerm)
  }

  createPlayer(playerData: FormData) {
  return this.http.post(`${this.apiUrl}/create`, playerData);
  }

  updatePlayer(id:number,player:Player):Observable<Player>{
    return this.http.put<Player>(this.apiUrl+'/'+id,player);
  }

  deletePlayer(id:number):Observable<Player>{
    return this.http.delete<Player>(this.apiUrl+'/'+id)
  }
}
