import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { DatePickerModule } from 'primeng/datepicker';

@Component({
  selector: 'app-user-add',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    InputTextModule,
    PasswordModule,
    CheckboxModule,
    DatePickerModule
  ],
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent {
  user = {
    login: '',
    password: '',
    roles: [] as string[],
    surname: '',
    name: '',
    patronymic: '',
    email: '',
    birthday: null
  };

  allRoles = ['ADMIN', 'USER', 'MODERATOR'];

  constructor(private userService: UserService, private router: Router) {}

  onSubmit() {
    this.userService.createUser(this.user).subscribe({
      next: () => {
        alert('Пользователь успешно добавлен!');
        this.router.navigate(['/users']);
      },
      error: (err: any) => {
        console.error(err);
        alert('Ошибка сохранения!');
      }
    });
  }
}
