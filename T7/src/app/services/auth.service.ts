import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs'; // Добавили Observable
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.decodeToken();
  }

  login(credentials: any): Observable<any> {
    return this.http.post<any>('/api/auth/login', credentials).pipe(
      tap(res => {
        if (res.token) {
          localStorage.setItem('auth_token', res.token);
          this.decodeToken();
        }
      })
    );
  }

  private decodeToken() {
    const token = localStorage.getItem('auth_token');
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.currentUserSubject.next(payload);
      } catch (e) {
        console.error('Ошибка декодирования токена', e);
        this.currentUserSubject.next(null);
      }
    }
  }

  get isAdmin(): boolean {
    const user = this.currentUserSubject.value;
    const roles = user?.roles || [];
    return Array.isArray(roles) ? roles.includes('ROLE_ADMIN') : roles === 'ROLE_ADMIN';
  }

  get username(): string {
    return this.currentUserSubject.value?.sub || 'Guest';
  }

  getToken() {
    return localStorage.getItem('auth_token');
  }

  logout() {
    localStorage.removeItem('auth_token');
    this.currentUserSubject.next(null);
  }
}
