import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';
import { LanguageService } from '../../services/language.service';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule, RouterLink],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: any[] = [];

  authService = inject(AuthService);
  private userService = inject(UserService);
  private langService = inject(LanguageService);
  private destroyRef = inject(DestroyRef);

  t = this.langService.t;

  ngOnInit() {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
      });
    this.loadUsers();
  }

  get currentUserLogin(): string {
    return this.authService.username;
  }

  loadUsers() {
    this.userService.getUsers().subscribe({
      next: (data) => { this.users = data; },
      error: (err) => {
        console.error(err);
        alert(this.t.loadUsersError);
      }
    });
  }

  deleteUser(login: string) {
    if (login === this.currentUserLogin) {
      alert(this.t.selfDeleteError);
      return;
    }

    if (confirm(`${this.t.deleteConfirm} ${login}?`)) {
      this.userService.deleteUser(login).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.login !== login);
        },
        error: (err) => {
          console.error(err);
          alert(this.t.deleteServerError);
        }
      });
    }
  }
}
