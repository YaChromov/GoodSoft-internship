import { Component, OnInit, Input, Output, EventEmitter, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';

import { LanguageService } from '../../services/language.service';
import { AuthService } from '../../services/auth.service';

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
  private readonly langService = inject(LanguageService);
  private readonly authService = inject(AuthService);
  private readonly destroyRef = inject(DestroyRef);

  @Input() public user: any = {};
  @Input() public allRoles: string[] = [];
  @Input() public title: string = '';
  @Input() public isEditMode: boolean = false;
  @Input() public errorMessage?: string;
  @Input() public hideRoles: boolean = false;

  @Output() public readonly formSubmit = new EventEmitter<any>();

  public t: any = this.langService.t;

  public ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
      });
  }

  public isSelfAdminLock(role: string): boolean {
    const currentLoggedInUser = this.authService.username;
    const isEditingSelf = this.isEditMode && this.user.login === currentLoggedInUser;
    return isEditingSelf && role === 'ROLE_ADMIN';
  }

  public onSubmit(): void {
    this.formSubmit.emit(this.user);
  }
}
