import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  user = { email: '', password: '', firstName: '', lastName: '' };

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    this.auth.register(this.user).subscribe({
      next: () => {
        alert('Registration successful! Please login.');
        this.router.navigate(['/login']);
      },
      error: (err) => alert('Error: ' + err.error)
    });
  }
}