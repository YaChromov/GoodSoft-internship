import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUser = {
    username: 'admin_user',
    roles: ['USER', 'ADMIN']
  };

  get username(): string {
    return this.currentUser.username;
  }

  get isAdmin(): boolean {
    return this.currentUser.roles.includes('ADMIN');
  }
}
