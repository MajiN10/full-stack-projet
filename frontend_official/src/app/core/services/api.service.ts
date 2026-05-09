import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/estore.models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getProducts(keyword?: string, categoryId?: number): Observable<Product[]> {
    let params = '';
    if (keyword) params = `?keyword=${encodeURIComponent(keyword)}`;
    else if (categoryId) params = `?categoryId=${categoryId}`;
    return this.http.get<Product[]>(`${this.baseUrl}/products${params}`);
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/products/${id}`);
  }

  getCategories(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/categories`);
  }

  addToCart(userId: number, productId: number, quantity: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/cart/add?userId=${userId}&productId=${productId}&quantity=${quantity}`, {});
  }

  getCart(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/cart/${userId}`);
  }

  updateCartItem(userId: number, itemId: number, quantity: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/cart/update?userId=${userId}&itemId=${itemId}&quantity=${quantity}`, {});
  }

  removeCartItem(userId: number, itemId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/cart/remove/${itemId}?userId=${userId}`);
  }

  placeOrder(userId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/orders?userId=${userId}`, {});
  }

  getOrderHistory(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/orders/user/${userId}`);
  }

  getReviews(productId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/reviews/product/${productId}`);
  }

  submitReview(review: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/reviews`, review);
  }

  getProfile(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/users/${userId}/profile`);
  }

  updateProfile(userId: number, profile: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/users/${userId}/profile`, profile);
  }
}
