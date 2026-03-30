import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { PasswordModule } from 'primeng/password';
import { LanguageService } from '../../services/language.service';
import { UserService } from '../../services/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-password-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, PasswordModule],
  templateUrl: './password-edit.component.html',
  styleUrls: ['./password-edit.component.css']
})
export class PasswordEditComponent implements OnInit {
  private router = inject(Router);
  private langService = inject(LanguageService);
  private userService = inject(UserService);
  private destroyRef = inject(DestroyRef);

  passwordData = {
    oldPassword: '',
    newPassword: ''
  };
  errorMessage: string = '';
  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
        if (this.errorMessage) {
          this.errorMessage = this.t.passwordError;
        }
      });
  }

  onSubmit() {
    this.errorMessage = '';

    this.userService.changePassword(this.passwordData).subscribe({
      next: () => {
        console.log('Пароль успешно изменен');
        this.router.navigate(['/welcome']);
      },
      error: (err) => {
        console.error('Ошибка смены пароля:', err);
        this.errorMessage = this.t.passwordError;
      }
    });
  }
}
