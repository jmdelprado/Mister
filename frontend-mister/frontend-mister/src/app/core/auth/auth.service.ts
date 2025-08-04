import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
 
  private apiUrl: string = 'https://mister-ei6q.onrender.com'

  constructor( private http: HttpClient, private router: Router){}

  login(username: string, password:string): Observable<any>{
    return this.http.post<any>(this.apiUrl+'/login',{username,password})
  } 

  saveToken(token:string){
    const decodedToken: any = jwtDecode(token);
    const authorities = JSON.parse(decodedToken.authorities);
    const isAdmin = authorities.some((auth:any)=> auth.authority === 'ROLE_ADMIN');
    localStorage.setItem('isAdmin',JSON.stringify(isAdmin));
    localStorage.setItem('jwtToken',token);
    console.log(localStorage.getItem('isAdmin'));
  }

  getToken():string | null{
    return localStorage.getItem('jwtToken');
  }

}
