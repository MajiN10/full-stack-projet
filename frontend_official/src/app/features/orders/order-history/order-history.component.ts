import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html'
})
export class OrderHistoryComponent implements OnInit {
  orders: any[] = [];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    const user = JSON.parse(localStorage.getItem('currentUser') || '{}');
    if (user.id) {
      this.api.getOrderHistory(user.id).subscribe(data => this.orders = data);
    }
  }
}