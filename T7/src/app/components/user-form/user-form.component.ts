import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';


import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';

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
export class UserFormComponent {
  @Input() user: any = {};
  @Input() allRoles: string[] = [];
  @Input() title: string = '';
  @Input() isEditMode: boolean = false;
  @Input() errorMessage?: string;

  @Output() formSubmit = new EventEmitter<any>();


  isSelfAdminLock(role: string): boolean {
    const currentUserLogin = 'admin';
    return this.isEditMode && this.user.login === currentUserLogin && role === 'ADMIN';
  }

  onSubmit() {
    this.formSubmit.emit(this.user);
  }
}
