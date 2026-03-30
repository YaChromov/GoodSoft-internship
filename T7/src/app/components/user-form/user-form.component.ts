import { Component, Input, Output, EventEmitter, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';


import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';

import { LanguageService } from '../../services/language.service';
import { AuthService } from '../../services/auth.service';
import { TranslationKeys } from '../../constants/translations';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    InputTextModule,
    PasswordModule,
    CheckboxModule,
    DatePickerModule,
    ButtonModule
  ],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  // Используем inject() для чистоты кода, как в остальном проекте
  private langService = inject(LanguageService);
  private authService = inject(AuthService);

  @Input() user: any = {};
  @Input() allRoles: string[] = [];
  @Input() title: string = '';
  @Input() isEditMode: boolean = false;
  @Input() errorMessage?: string;

  @Output() formSubmit = new EventEmitter<any>();

  t: TranslationKeys = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$.subscribe(() => {
      this.t = this.langService.t;
    });
  }

  isSelfAdminLock(role: string): boolean {
    const currentLoggedInUser = this.authService.username;

    const isEditingSelf = this.isEditMode && this.user.login === currentLoggedInUser;
    const isAdminRole = (role === 'ROLE_ADMIN');

    return isEditingSelf && isAdminRole;
  }

  onSubmit() {

    this.formSubmit.emit(this.user);
  }
}
