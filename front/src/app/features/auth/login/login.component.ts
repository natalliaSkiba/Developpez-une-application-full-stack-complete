import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Location } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  formData = {
    identifier: '',
    password: ''
  }
  message = '';

  constructor(private http: HttpClient, private location: Location) { }

  onSubmit() {
    this.http
    .post('http://localhost:8080/auth/login', this.formData,{
      responseType: 'text'
    })
    .subscribe({
      next: (res) =>{
        this.message = res;
      },
      error:() =>{
        this.message = 'Login failed';
      }
    })
  }
  goBack() {
    this.location.back(); 
  }

}
