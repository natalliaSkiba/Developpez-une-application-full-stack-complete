import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  register(data: { username: string; email: string; password: string }): Observable<string> {
    return this.http.post(this.apiUrl + '/register', data, {
      responseType: 'text' //  потому что backend возвращает String
    });
  }

  login(data: { identifier: string; password: string }): Observable<string> {
    return this.http.post(this.apiUrl + '/login', data, {
      responseType: 'text'
    });
  }
}
