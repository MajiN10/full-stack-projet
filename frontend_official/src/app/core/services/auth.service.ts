import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { User } from '../models/estore.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';
  
  // This tracks if a user is logged in across the whole app
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) { }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/register`, user);
  }

  login(credentials: any): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, credentials).pipe(
      tap(user => this.currentUserSubject.next(user)) // Save user in memory
    );
  }

  logout() {
    this.currentUserSubject.next(null);
  }
}