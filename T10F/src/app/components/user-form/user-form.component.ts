import { Component, Input, Output, EventEmitter, OnInit, inject, DestroyRef } from '@angular/core';
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
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly authService: AuthService = inject(AuthService);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  @Input() public user: any = {};
  @Input() public allRoles: string[] = [];
  @Input() public title: string = '';
  @Input() public isEditMode: boolean = false;
  @Input() public errorMessage?: string;
  @Input() public hideRoles: boolean = false;

  public t: any;

  @Output() public readonly formSubmit: EventEmitter<any> = new EventEmitter<any>();

  public ngOnInit(): void {
    this.t = this.langService.t;

    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
      });
  }

  public isSelfAdminLock(role: string): boolean {
    const currentLoggedInUser: string = this.authService.username;
    const isEditingSelf: boolean = this.isEditMode && this.user.login === currentLoggedInUser;
    const isAdminRole: boolean = (role === 'ROLE_ADMIN');
    return isEditingSelf && isAdminRole;
  }

  public onSubmit(): void {
    this.formSubmit.emit(this.user);
  }
}
