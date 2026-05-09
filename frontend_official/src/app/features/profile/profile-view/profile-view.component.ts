import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html'
})
export class ProfileViewComponent implements OnInit {
  profile: any = { phone: '', address: '', city: '', country: '' };
  user: any = null;
  saveSuccess = false;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    const u = localStorage.getItem('currentUser');
    if (u) {
      this.user = JSON.parse(u);
      this.api.getProfile(this.user.id).subscribe({
        next: (data: any) => { if (data) this.profile = data; },
        error: () => {}
      });
    }
  }

  saveProfile() {
    this.api.updateProfile(this.user.id, this.profile).subscribe({
      next: () => { this.saveSuccess = true; setTimeout(() => this.saveSuccess = false, 3000); },
      error: () => alert('Failed to save profile.')
    });
  }
}
