import { Routes } from '@angular/router';
import { PasswordEditComponent } from './components/password-edit/password-edit.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/register/registration.component';
import { OrderListComponent } from './components/order-list/order-list.component';
import { OrderCreateComponent } from './components/order-create/order-create.component';
import { OrderPendingComponent } from './components/order-pending/order-pending.component';

import { authGuard } from './guards/auth.guard';
import { guestGuard } from './guards/guest.guard';
import { roleGuard } from './guards/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [guestGuard]
  },
  {
    path: 'registration',
    component: RegistrationComponent,
    canActivate: [guestGuard]
  },
  {
    path: 'password-edit',
    component: PasswordEditComponent,
    canActivate: [authGuard]
  },

  {
    path: 'order-create',
    component: OrderCreateComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['USER'] }
  },
  {
    path: 'order-pending',
    component: OrderPendingComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'orders',
    component: OrderListComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ADMIN', 'USER'] }
  },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
