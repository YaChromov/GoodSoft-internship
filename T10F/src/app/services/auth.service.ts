import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http: HttpClient = inject(HttpClient);

  private readonly currentUserSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  public readonly currentUser$: Observable<any> = this.currentUserSubject.asObservable();

  constructor() {
    this.decodeToken();
  }

  public register(userData: any): Observable<any> {
    return this.http.post<any>('/api/auth/register', userData).pipe(
      tap((res: any): void => {
        if (res?.token) {
          this.handleAuthSuccess(res.token);
        }
      })
    );
  }

  public login(credentials: any): Observable<any> {
    return this.http.post<any>('/api/auth/login', credentials).pipe(
      tap((res: any): void => {
        if (res?.token) {
          this.handleAuthSuccess(res.token);
        }
      })
    );
  }

  private handleAuthSuccess(token: string): void {
    localStorage.setItem('auth_token', token);
    this.decodeToken();
  }

  private decodeToken(): void {
    const token: string | null = localStorage.getItem('auth_token');
    if (token) {
      try {
        const payload: any = JSON.parse(atob(token.split('.')[1]));
        this.currentUserSubject.next(payload);
      } catch (e: unknown) {
        console.error('Ошибка декодирования токена', e);
        this.logout();
      }
    } else {
      this.currentUserSubject.next(null);
    }
  }

  public getUserRole(): string {
    const user: any = this.currentUserSubject.value;
    if (!user?.roles) return 'GUEST';

    const roles: string[] = Array.isArray(user.roles) ? user.roles : [user.roles];

    if (roles.includes('ROLE_ADMIN') || roles.includes('ADMIN')) {
      return 'ADMIN';
    }
    if (roles.includes('ROLE_USER') || roles.includes('USER')) {
      return 'USER';
    }
    return 'GUEST';
  }

  public get isAdmin(): boolean {
    return this.getUserRole() === 'ADMIN';
  }

  public get username(): string {
    return this.currentUserSubject.value?.sub || 'Guest';
  }

  public getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  public logout(): void {
    localStorage.removeItem('auth_token');
    this.currentUserSubject.next(null);
  }

  public isAuthenticated(): boolean {
    return !!this.currentUserSubject.value;
  }
}
