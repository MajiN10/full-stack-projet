import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  credentials = { email: '', password: '' };

  constructor(private auth: AuthService, private router: Router) {}

  onLogin() {
    this.auth.login(this.credentials).subscribe({
      next: (user) => {
        // Save user to localStorage so login "sticks" if we refresh
        localStorage.setItem('currentUser', JSON.stringify(user));
        alert('Welcome back, ' + user.firstName);
        this.router.navigate(['/products']);
      },
      error: () => alert('Invalid email or password')
    });
  }
}