import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserFormComponent } from '../user-form/user-form.component';

@Component({
  selector: 'app-user-edit',
  standalone: true,

  imports: [UserFormComponent],
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

  onSave(updatedUser: any) {
    if (!updatedUser.roles?.length) {
      this.errorMessage = 'Выберите роль';
      return;
    }

    console.log('Сохранение отредактированного пользователя:', updatedUser);
    this.router.navigate(['/userlist']);
  }
}
