import { Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserAddComponent } from './components/user-add/user-add.component';
import { UserEditComponent } from './components/user-edit/user-edit.component';
import { PasswordEditComponent } from './components/password-edit/password-edit.component';
import { LoginComponent } from './components/login/login.component';

import { authGuard } from './guards/auth.guard';
import { guestGuard } from './guards/guest.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [guestGuard]
  },
  {
    path: 'welcome',
    component: WelcomeComponent,
    canActivate: [authGuard]
  },
  {
    path: 'loginedit',
    component: PasswordEditComponent,
    canActivate: [authGuard]
  },

  {
    path: 'userlist',
    component: UserListComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'useradd',
    component: UserAddComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'useredit/:id',
    component: UserEditComponent,
    canActivate: [authGuard],
    data: { roles: ['ADMIN'] }
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
