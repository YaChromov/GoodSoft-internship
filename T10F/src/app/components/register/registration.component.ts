import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { UserService } from '../../services/user.service';
import { LanguageService } from '../../services/language.service';
import { UserFormComponent } from '../user-form/user-form.component';
import { AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [UserFormComponent],
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  private userService = inject(UserService);
  private router = inject(Router);
  private langService = inject(LanguageService);
  private destroyRef = inject(DestroyRef);
  private authService = inject(AuthService);

  user = {
    login: '',
    password: '',
    roles: ['USER'],
    surname: '',
    name: '',
    patronymic: '',
    email: '',
    birthday: null
  };

  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
      });
  }

  onSubmit(userData: any) {
    this.authService.register(userData).subscribe({
      next: (response) => {
        console.log('Регистрация успешна', response);
        const targetRoute = this.authService.isAuthenticated() ? '/welcome' : '/login';

        this.router.navigate([targetRoute]);
      },
      error: (err: any) => {
        console.error('Ошибка при регистрации:', err);

        const errorMessage = err.error?.message || this.t.saveError || 'Registration failed';
        alert(errorMessage);
      }
    });
  }
}
