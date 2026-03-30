import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { LanguageService } from '../../services/language.service';
import { UserFormComponent } from '../user-form/user-form.component';

@Component({
  selector: 'app-user-add',
  standalone: true,
  imports: [UserFormComponent],
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {
  private userService = inject(UserService);
  private router = inject(Router);
  private langService = inject(LanguageService);

  user = {
    login: '',
    password: '',
    roles: [] as string[],
    surname: '',
    name: '',
    patronymic: '',
    email: '',
    birthday: null
  };

  allRoles = ['ADMIN', 'USER', 'MODERATOR'];

  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$.subscribe(() => {
      this.t = this.langService.t;
    });
  }

  onSubmit(userData: any) {
    this.userService.createUser(userData).subscribe({
      next: () => {
        this.router.navigate(['/userlist']);
      },
      error: (err: any) => {
        console.error(err);
        alert(this.t.saveError);
      }
    });
  }
}
