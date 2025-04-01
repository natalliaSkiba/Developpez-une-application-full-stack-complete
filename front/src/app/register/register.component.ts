import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Location } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  formData = {
    username: '',
    email: '',
    password: '',
  };

  message = '';

  constructor(private http: HttpClient, private location: Location) {}

  onSubmit() {
    this.http
      .post('http://localhost:8080/auth/register', this.formData, {
        responseType: 'text',
      })
      .subscribe({
        next: (res) => {
          this.message = res;
        },
        error: () => {
          this.message = 'Registration failed';
        },
      });
  }
  goBack() {
    this.location.back(); 
  }
}
