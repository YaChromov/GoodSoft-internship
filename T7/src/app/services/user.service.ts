import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface User {
  login: string;
  name: string;
  surname: string;
  patronymic?: string;
  email: string;
  roles: string[];
  birthday: Date | string | null;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = '/api/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getUserByLogin(login: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${login}`).pipe(
      catchError(this.handleError)
    );
  }

  createUser(user: User): Observable<User> {
    const payload = this.preparePayload(user);
    return this.http.post<User>(this.apiUrl, payload).pipe(
      catchError(this.handleError)
    );
  }

  updateUser(login: string, user: User): Observable<void> {
    const payload = this.preparePayload(user);
    return this.http.put<void>(`${this.apiUrl}/${login}`, payload).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(login: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${login}`).pipe(
      catchError(this.handleError)
    );
  }

  private preparePayload(user: User): any {
    const payload = { ...user };
    if (payload.birthday instanceof Date) {
      payload.birthday = this.formatDate(payload.birthday);
    }
    return payload;
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }


  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Произошла неизвестная ошибка';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Ошибка: ${error.error.message}`;
    } else {
      errorMessage = `Код ошибки: ${error.status}, сообщение: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  changePassword(passwordData: any): Observable<void> {
    return this.http.patch<void>(`/api/users/change-password`, passwordData);
  }
}
