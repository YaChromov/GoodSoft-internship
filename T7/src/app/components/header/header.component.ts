import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, NavigationEnd } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LanguageService, Lang } from '../../services/language.service';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  authService = inject(AuthService);
  langService = inject(LanguageService);
  private router = inject(Router);

  currentLang: Lang = 'ru';
  t = this.langService.t;

  isUserList$ = this.router.events.pipe(
    filter(event => event instanceof NavigationEnd),
    map((event: any) => event.urlAfterRedirects.includes('/userlist'))
  );

  ngOnInit(): void {
    this.langService.currentLang$.subscribe(lang => {
      this.currentLang = lang;
      this.t = this.langService.t;
    });
  }

  setLanguage(lang: Lang): void {
    this.langService.setLanguage(lang);
  }

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
