import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    InputTextModule,
    CheckboxModule,
    DatePickerModule,
    ButtonModule
  ],
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  user: any = {
    login: '',
    roles: [],
    surname: '',
    name: '',
    patronymic: '',
    email: '',
    birthday: null
  };

  allRoles: string[] = ['ADMIN', 'USER', 'MANAGER'];
  currentUserLogin: string = 'admin';
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const userId = this.route.snapshot.paramMap.get('id');
    if (userId) {
      this.loadUser(userId);
    }
  }

  loadUser(login: string) {
    this.user = {
      login: login,
      roles: ['ADMIN', 'USER'],
      surname: 'Иванов',
      name: 'Иван',
      patronymic: 'Иванович',
      email: 'ivanov@mail.com',
      birthday: new Date('1990-05-15')
    };
  }

  isSelfAdminLock(role: string): boolean {
    return this.user.login === this.currentUserLogin && role === 'ADMIN';
  }

  onSave() {
    if (!this.user.roles?.length) {
      this.errorMessage = 'Выберите роль';
      return;
    }
    console.log('Сохранение:', this.user);
    this.router.navigate(['/userlist']);
  }
}
