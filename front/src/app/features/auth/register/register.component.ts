import { Component, OnDestroy } from '@angular/core';
import { Location } from '@angular/common';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnDestroy {
  formData = {
    username: '',
    email: '',
    password: '',
  };

  message = '';
  isError = true;
  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private location: Location,
    private router: Router
  ) {}

  onSubmit() {
    this.authService
      .register(this.formData)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
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
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
