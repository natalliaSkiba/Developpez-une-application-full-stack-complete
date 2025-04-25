import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/user.model";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8080/user';

    constructor(private http: HttpClient){}

getUserById(id:number): Observable<User>{
    return this.http.get<User>((`${this.apiUrl}/${id}`));
}
updateUser(id: number, data: Partial<User>): Observable<User> {
    return this.http.put<User>((`${this.apiUrl}/${id}`), data, {
        responseType: 'json'
    });
}
}

