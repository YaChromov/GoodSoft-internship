import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    RouterLink
  ],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: any[] = [];

  currentUserLogin: string = 'admin';
  isAdmin: boolean = true;


  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {

    this.userService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
        console.log('Список пользователей обновлен:', data);
      },
      error: (err: any) => {
        console.error('Ошибка загрузки:', err);
        alert('Не удалось получить данные с сервера');
      }
    });
  }

  deleteUser(login: string) {
    if (confirm(`Вы уверены, что хотите удалить ${login}?`)) {
      this.userService.deleteUser(login).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.login !== login);
          console.log('Пользователь успешно удален');
        },
        error: (err: any) => {
          console.error('Ошибка удаления:', err);
          alert('Сервер отклонил запрос на удаление');
        }
      });
    }
  }
}
