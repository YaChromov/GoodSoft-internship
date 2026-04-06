import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { PasswordModule } from 'primeng/password';
import { LanguageService } from '../../services/language.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-password-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, PasswordModule],
  templateUrl: './password-edit.component.html',
  styleUrls: ['./password-edit.component.css']
})
export class PasswordEditComponent implements OnInit {
  private readonly router: Router = inject(Router);
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly userService: UserService = inject(UserService);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  public passwordData = {
    oldPassword: '',
    newPassword: ''
  };
  public errorMessage: string = '';
  public t: any = this.langService.t;

  public ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
        if (this.errorMessage) {
          this.errorMessage = this.t.passwordError;
        }
      });
  }

  public onSubmit(): void {
    this.errorMessage = '';

    this.userService.changePassword(this.passwordData)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (): void => {
          this.handleSuccess();
        },
        error: (err: unknown): void => {
          this.handleError(err);
        }
      });
  }

  private handleSuccess(): void {
    console.log('Password successfully changed');
    void this.router.navigate(['/orders']);
  }

  private handleError(err: unknown): void {
    console.error('Password change failed:', err);
    this.errorMessage = this.t.passwordError || 'Ошибка при смене пароля';
  }
}
