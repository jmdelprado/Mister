import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 
  private apiUrl: string = 'http://localhost:8080'

  constructor( private http: HttpClient, private router: Router){}

  login(username: string, password:string): Observable<any>{
    return this.http.post<any>(this.apiUrl+'/login',{username,password})
  } 

  saveToken(token:string){
    localStorage.setItem('jwtToken',token);
  }

  getToken():string | null{
    return localStorage.getItem('jwtToken');
  }
}
