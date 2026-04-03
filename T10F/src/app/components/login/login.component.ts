import { Component, inject, OnInit, DestroyRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router'; // Импорт RouterLink
import { AuthService } from '../../services/auth.service';
import { LanguageService } from '../../services/language.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private authService = inject(AuthService);
  private langService = inject(LanguageService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  loginData = { login: '', password: '' };
  errorMessage = '';

  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
        if (this.errorMessage) {
          this.errorMessage = this.t.loginError;
        }
      });
  }

  onLogin(): void {
    this.authService.login(this.loginData).subscribe({
      next: () => {
        if (this.authService.isAdmin) {
          this.router.navigate(['/order-pending']);
        } else {
          this.router.navigate(['/order-create']);
        }
      },
      error: () => {
        this.errorMessage = this.t.loginError;
      }
    });
  }
}
