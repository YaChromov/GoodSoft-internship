import { Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserAddComponent } from './components/user-add/user-add.component';
import { UserEditComponent } from './components/user-edit/user-edit.component';
import { PasswordEditComponent } from './components/password-edit/password-edit.component';

export const routes: Routes = [
  { path: 'welcome', component: WelcomeComponent },
  { path: 'userlist', component: UserListComponent },
  { path: 'useradd', component: UserAddComponent },
  { path: 'useredit/:id', component: UserEditComponent },
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'loginedit', component: PasswordEditComponent },
  { path: '**', redirectTo: '/welcome' }
];

