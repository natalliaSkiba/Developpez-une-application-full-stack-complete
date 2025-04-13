import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

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
  isError = true;

  constructor(private authService: AuthService, private location: Location, private router: Router) {}

  onSubmit() {
    this.authService.register(this.formData).subscribe({
        next: (res) => {
          this.message = res;
          this.isError = false;
          this.formData = { username: '', email: '', password: '' };

          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 1500);
        },
        error: (err) => {
          this.message = err.error;
          this.isError = true;
        },
      });
  }
  goBack() {
    this.location.back(); 
  }
}
