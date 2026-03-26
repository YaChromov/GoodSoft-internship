import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { PasswordModule } from 'primeng/password';

@Component({
  selector: 'app-password-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, PasswordModule],
  templateUrl: './password-edit.component.html',
  styleUrls: ['./password-edit.component.css']
})
export class PasswordEditComponent {
  passwordData = {
    oldPassword: '',
    newPassword: ''
  };
  errorMessage: string = '';

  constructor(private router: Router) {}

  onSubmit() {
    console.log('Смена пароля:', this.passwordData);
    this.router.navigate(['/welcome']);
  }
}
