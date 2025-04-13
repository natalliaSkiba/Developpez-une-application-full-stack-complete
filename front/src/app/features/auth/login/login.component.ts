import { Component } from '@angular/core';

import { Location } from '@angular/common';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  credentials = {
    identifier: '',
    password: ''
  }
  message = '';
  isError = false;

  constructor(private authService: AuthService, private location: Location, private router: Router) {}

  onSubmit() {
    this.authService.login(this.credentials)
    .subscribe({
      next: (res) =>{
        this.message = res;
        this.isError = false;

        setTimeout(() => {
          this.router.navigate(['/feed']); 
        }, 1000);
      },

      error: (err) => {
        if (err.status === 401) {
          this.isError = true;
          this.message = err.error; // "Identifiants incorrects"
        } else {
          this.isError = true;
          this.message = "Erreur inconnue";
        }
  }})
  }
  goBack() {
    this.location.back(); 
  }

}
