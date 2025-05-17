import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/user.model";
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UserService {
        
  private apiUrl =  `${environment.apiUrl}/user`;

    constructor(private http: HttpClient){}


getProfile():Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/profile`);
}
updateUser( data: Partial<User>): Observable<{token: string}> {
    return this.http.put<{token: string}>((`${this.apiUrl}/profile`), data);
    
}
}

