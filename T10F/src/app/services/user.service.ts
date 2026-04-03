import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

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
  constructor(private http: HttpClient) {}

  changePassword(passwordData: any): Observable<void> {
    return this.http.patch<void>(`/api/users/change-password`, passwordData);
  }
}
