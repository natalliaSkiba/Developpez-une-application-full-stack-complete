import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl =  `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  register(data: { username: string; email: string; password: string }): Observable<string> {
    return this.http.post(this.apiUrl + '/register', data, {
      responseType: 'text' 
    });
  }

  login(data: { identifier: string; password: string }): Observable<{token:string}> {
    return this.http.post<{token:string}>(this.apiUrl + '/login', data)};
}
