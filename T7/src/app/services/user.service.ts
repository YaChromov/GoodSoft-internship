import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = '/api/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getUserByLogin(login: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${login}`);
  }

  createUser(user: any): Observable<any> {
    const payload = { ...user };

    if (payload.birthday instanceof Date) {
      payload.birthday = this.formatDate(payload.birthday);
    }

    return this.http.post<any>(this.apiUrl, payload);
  }

  updateUser(login: string, user: any): Observable<any> {
    const payload = { ...user };
    if (payload.birthday instanceof Date) {
      payload.birthday = this.formatDate(payload.birthday);
    }
    return this.http.put<any>(`${this.apiUrl}/${login}`, payload);
  }

  deleteUser(login: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${login}`);
  }


  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
