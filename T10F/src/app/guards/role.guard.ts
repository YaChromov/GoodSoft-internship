import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const expectedRoles = route.data['roles'] as Array<string>;

  const userRole = authService.getUserRole();

  if (!expectedRoles) {
    return true;
  }


  if (authService.isAuthenticated() && expectedRoles.includes(userRole)) {
    return true;
  }

  console.warn('Access denied: Insufficient permissions');
  router.navigate(['/login']);
  return false;
};
