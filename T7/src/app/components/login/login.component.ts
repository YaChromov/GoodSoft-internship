import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LanguageService } from '../../services/language.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private authService = inject(AuthService);
  private langService = inject(LanguageService);
  private router = inject(Router);

  loginData = { login: '', password: '' };
  errorMessage = '';

  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$.subscribe(() => {
      this.t = this.langService.t;
      if (this.errorMessage) {
        this.errorMessage = this.t.loginError;
      }
    });
  }

  onLogin(): void {
    this.authService.login(this.loginData).subscribe({
      next: () => {
        this.router.navigate(['/welcome']);
      },
      error: () => {
        this.errorMessage = this.t.loginError;
      }
    });
  }
}
