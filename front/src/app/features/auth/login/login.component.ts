import { Component, OnDestroy } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Location } from '@angular/common';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnDestroy {
  credentials = {
    identifier: '',
    password: '',
  };
  message = '';
  isError = false;
  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private location: Location,
    private router: Router
  ) {}

  onSubmit() {
    this.authService
      .login(this.credentials)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (res) => {
          localStorage.setItem('token', res.token);
          this.message = 'Connexion réussie !';
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
            this.message = 'Erreur inconnue';
          }
        },
      });
  }
  goBack() {
    this.location.back();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
