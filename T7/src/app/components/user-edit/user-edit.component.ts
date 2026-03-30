import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserFormComponent } from '../user-form/user-form.component';
import { UserService, User } from '../../services/user.service';
import { LanguageService } from '../../services/language.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-user-edit',
  standalone: true,
  imports: [UserFormComponent, CommonModule],
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private userService = inject(UserService);
  private langService = inject(LanguageService);
  private destroyRef = inject(DestroyRef);

  user: User | null = null;
  allRoles: string[] = ['ADMIN', 'USER', 'MANAGER'];
  errorMessage: string = '';
  isLoading: boolean = false;

  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
      });

    const login = this.route.snapshot.paramMap.get('id');
    if (login) {
      this.loadUser(login);
    } else {
      this.errorMessage = this.t.urlParamError;
    }
  }

  loadUser(login: string) {
    this.isLoading = true;
    this.userService.getUserByLogin(login).subscribe({
      next: (userData) => {
        this.user = userData;
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = this.t.loadUserError;
        this.isLoading = false;
        console.error(err);
      }
    });
  }

  onSave(updatedUser: User) {
    if (!updatedUser.roles?.length) {
      this.errorMessage = this.t.roleRequiredError;
      return;
    }

    this.errorMessage = '';
    const currentLogin = this.user?.login;

    if (currentLogin) {
      this.userService.updateUser(currentLogin, updatedUser).subscribe({
        next: () => this.router.navigate(['/userlist']),
        error: (err) => {
          this.errorMessage = this.t.saveError + ': ' + (err.message || '');
          console.error(err);
        }
      });
    }
  }
}
