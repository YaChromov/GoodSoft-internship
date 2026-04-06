import { Component, inject, OnInit, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { AuthService } from '../../services/auth.service';
import { LanguageService, Lang } from '../../services/language.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public readonly authService: AuthService = inject(AuthService);
  public readonly langService: LanguageService = inject(LanguageService);
  public currentLang: Lang = 'ru';
  public t: any = this.langService.t;

  private readonly router: Router = inject(Router);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  public ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((lang: Lang): void => {
        this.currentLang = lang;
        this.t = this.langService.t;
      });
  }

  public setLanguage(lang: Lang): void {
    this.langService.setLanguage(lang);
  }

  public onLogout(): void {
    this.authService.logout();
    void this.router.navigate(['/login']);
  }
}
