import { Component } from '@angular/core';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  constructor(public router: Router) {}

  isAuthRoute(): boolean {
    return this.router.url.includes('/login') || this.router.url.includes('/register');
  }
}
