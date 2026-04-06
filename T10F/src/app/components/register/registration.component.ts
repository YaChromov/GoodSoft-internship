import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { UserService } from '../../services/user.service';
import { LanguageService } from '../../services/language.service';
import { UserFormComponent } from '../user-form/user-form.component';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [UserFormComponent],
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  private readonly userService: UserService = inject(UserService);
  private readonly router: Router = inject(Router);
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);
  private readonly authService: AuthService = inject(AuthService);

  public user = {
    login: '',
    password: '',
    roles: ['USER'],
    surname: '',
    name: '',
    patronymic: '',
    email: '',
    birthday: null as string | null
  };

  public t: any = this.langService.t;

  public ngOnInit(): void {

    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
      });
  }

  public onSubmit(userData: any): void {
    this.authService.register(userData)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (response: any): void => {
          this.handleRegistrationSuccess(response);
        },
        error: (err: any): void => {
          this.handleRegistrationError(err);
        }
      });
  }

  private handleRegistrationSuccess(response: any): void {
    console.log('Registration successful:', response);

    const targetRoute: string = this.authService.isAuthenticated()
      ? '/welcome'
      : '/login';

    void this.router.navigate([targetRoute]);
  }

  private handleRegistrationError(err: any): void {
    console.error('Registration failed:', err);

    const errorMessage: string = err.error?.message
      || this.t.saveError
      || 'Registration failed';

    alert(errorMessage);
  }
}
