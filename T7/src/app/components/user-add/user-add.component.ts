import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserFormComponent } from '../user-form/user-form.component'; // Проверьте путь!

@Component({
  selector: 'app-user-add',
  standalone: true,
  imports: [UserFormComponent],
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

  onSubmit(userData: any) {
    this.userService.createUser(userData).subscribe({
      next: () => {
        alert('Пользователь успешно добавлен!');
        this.router.navigate(['/userlist']);
      },
      error: (err: any) => {
        console.error(err);
        alert('Ошибка сохранения!');
      }
    });
  }
}
