import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
  private readonly http: HttpClient = inject(HttpClient);

  public changePassword(passwordData: any): Observable<void> {
    return this.http.patch<void>(`/api/users/change-password`, passwordData);
  }
}
