import { Component, inject, OnInit, DestroyRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { AuthService } from '../../services/auth.service';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginData = {
    login: '',
    password: ''
  };
  public errorMessage: string = '';
  public t: any;

  private readonly authService: AuthService = inject(AuthService);
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly router: Router = inject(Router);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  public ngOnInit(): void {
    this.t = this.langService.t;

    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
        if (this.errorMessage) {
          this.errorMessage = this.t.loginError;
        }
      });
  }

  public onLogin(): void {
    this.authService.login(this.loginData)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (): void => {
          this.handleNavigation();
        },
        error: (err: unknown): void => {
          console.error('Auth error:', err);
          this.errorMessage = this.t.loginError;
        }
      });
  }

  private handleNavigation(): void {
    const route: string = this.authService.isAdmin
      ? '/order-pending'
      : '/order-create';

    void this.router.navigate([route]);
  }
}
