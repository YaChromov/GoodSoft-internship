import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.decodeToken();
  }

  register(userData: any): Observable<any> {
    return this.http.post<any>('/api/auth/register', userData).pipe(
      tap(res => {
        if (res && res.token) {
          this.handleAuthSuccess(res.token);
        }
      })
    );
  }

  login(credentials: any): Observable<any> {
    return this.http.post<any>('/api/auth/login', credentials).pipe(
      tap(res => {
        if (res && res.token) {
          this.handleAuthSuccess(res.token);
        }
      })
    );
  }

  private handleAuthSuccess(token: string) {
    localStorage.setItem('auth_token', token);
    this.decodeToken();
  }

  private decodeToken() {
    const token = localStorage.getItem('auth_token');
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.currentUserSubject.next(payload);
      } catch (e) {
        console.error('Ошибка декодирования токена', e);
        this.logout();
      }
    } else {
      this.currentUserSubject.next(null);
    }
  }

  getUserRole(): string {
    const user = this.currentUserSubject.value;
    if (!user || !user.roles) return 'GUEST';

    const roles = Array.isArray(user.roles) ? user.roles : [user.roles];

    if (roles.includes('ROLE_ADMIN') || roles.includes('ADMIN')) {
      return 'ADMIN';
    }
    if (roles.includes('ROLE_USER') || roles.includes('USER')) {
      return 'USER';
    }
    return 'GUEST';
  }

  get isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN';
  }

  get username(): string {
    return this.currentUserSubject.value?.sub || 'Guest';
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  logout() {
    localStorage.removeItem('auth_token');
    this.currentUserSubject.next(null);
  }

  public isAuthenticated(): boolean {
    return !!this.currentUserSubject.value;
  }
}
