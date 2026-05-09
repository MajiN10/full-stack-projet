import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../../core/services/api.service';

@Component({
  selector: 'app-cart-view',
  templateUrl: './cart-view.component.html'
})
export class CartViewComponent implements OnInit {
  cart: any = null;
  total: number = 0;

  constructor(private api: ApiService, private router: Router) { }

  ngOnInit(): void {
    this.loadCart();
  }

  get userId(): number | null {
    const u = localStorage.getItem('currentUser');
    return u ? JSON.parse(u).id : null;
  }

  loadCart() {
    if (!this.userId) { this.router.navigate(['/login']); return; }
    this.api.getCart(this.userId).subscribe({
      next: (data: any) => { this.cart = data; this.calculateTotal(); },
      error: () => { this.cart = { items: [] }; }
    });
  }

  calculateTotal() {
    this.total = this.cart?.items?.reduce(
      (sum: number, item: any) => sum + item.unitPrice * item.quantity, 0
    ) ?? 0;
  }

  updateQuantity(itemId: number, quantity: number) {
    if (!this.userId) return;
    if (quantity < 1) { this.removeItem(itemId); return; }
    this.api.updateCartItem(this.userId, itemId, quantity).subscribe({
      next: (data: any) => { this.cart = data; this.calculateTotal(); },
      error: (err: any) => alert('Update failed: ' + (err.error || 'Unknown error'))
    });
  }

  removeItem(itemId: number) {
    if (!this.userId) return;
    this.api.removeCartItem(this.userId, itemId).subscribe({
      next: (data: any) => { this.cart = data; this.calculateTotal(); },
      error: (err: any) => alert('Remove failed: ' + (err.error || 'Unknown error'))
    });
  }

  onCheckout() {
    if (!this.userId) return;
    this.api.placeOrder(this.userId).subscribe({
      next: (order: any) => {
        alert('✅ Order #' + order.id + ' placed successfully!');
        this.cart = { items: [] };
        this.total = 0;
        this.router.navigate(['/orders']);
      },
      error: (err: any) => alert('Checkout failed: ' + (err.error || 'Unknown error'))
    });
  }
}
