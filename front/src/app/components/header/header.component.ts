import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  showMobileMenu = false;
  
  constructor(private router: Router) {}

  toggleMenu() {
    this.showMobileMenu = !this.showMobileMenu;
  }

  logout() {
    this.toggleMenu(); 
    localStorage.removeItem('token'); 
    this.router.navigate(['/login']);
  }
}

