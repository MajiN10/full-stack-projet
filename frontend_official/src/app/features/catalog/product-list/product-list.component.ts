import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../core/services/api.service';
import { Product } from '../../../core/models/estore.models';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html'
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  categories: any[] = [];
  reviewsMap: { [productId: number]: any[] } = {};
  newReviewMap: { [productId: number]: any } = {};
  showReviewFormMap: { [productId: number]: boolean } = {};
  keyword = '';
  selectedCategoryId: number | null = null;

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadCategories();
    this.loadProducts();
  }

  loadCategories() {
    this.api.getCategories().subscribe(data => this.categories = data);
  }

  loadProducts() {
    this.api.getProducts(
      this.keyword || undefined,
      this.selectedCategoryId || undefined
    ).subscribe(data => this.products = data);
  }

  onSearch() { this.selectedCategoryId = null; this.loadProducts(); }

  onCategoryFilter(categoryId: number | null) {
    this.selectedCategoryId = categoryId;
    this.keyword = '';
    this.loadProducts();
  }

  getCatIcon(name: string): string {
    const map: { [k: string]: string } = {
      'Electronics': 'bi-cpu-fill',
      'Books': 'bi-book-fill',
      'Sport': 'bi-trophy-fill',
      'Clothing': 'bi-bag-fill',
    };
    return map[name] || 'bi-tag-fill';
  }

  onAddToCart(productId: any) {
    const userJson = localStorage.getItem('currentUser');
    if (!userJson) { alert('Please login first to add items to your cart!'); return; }
    const user = JSON.parse(userJson);
    this.api.addToCart(user.id, productId, 1).subscribe({
      next: () => alert('✅ Product added to cart!'),
      error: (err: any) => alert('Error: ' + (err.error || 'Something went wrong'))
    });
  }

  loadReviews(productId: number) {
    this.api.getReviews(productId).subscribe(data => { this.reviewsMap[productId] = data; });
  }

  toggleReviewForm(productId: number) {
    this.showReviewFormMap[productId] = !this.showReviewFormMap[productId];
    if (!this.newReviewMap[productId]) {
      this.newReviewMap[productId] = { rating: 5, comment: '', authorName: '' };
    }
  }

  submitReview(productId: number) {
    const userJson = localStorage.getItem('currentUser');
    if (!userJson) { alert('Please login to submit a review.'); return; }
    const user = JSON.parse(userJson);
    const review = {
      ...this.newReviewMap[productId],
      productId, userId: user.id,
      authorName: this.newReviewMap[productId].authorName || user.firstName || user.email
    };
    this.api.submitReview(review).subscribe({
      next: () => { this.showReviewFormMap[productId] = false; this.loadReviews(productId); },
      error: () => alert('Failed to submit review.')
    });
  }
}
