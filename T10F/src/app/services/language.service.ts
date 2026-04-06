import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TRANSLATIONS } from '../constants/translations';

export type Lang = 'ru' | 'en';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private readonly langSubject: BehaviorSubject<Lang> = new BehaviorSubject<Lang>(
    (localStorage.getItem('app_lang') as Lang) || 'ru'
  );

  public readonly currentLang$: Observable<Lang> = this.langSubject.asObservable();

  public get currentLang(): Lang {
    return this.langSubject.value;
  }

  public get t(): any {
    return TRANSLATIONS[this.currentLang];
  }

  public setLanguage(lang: Lang): void {
    localStorage.setItem('app_lang', lang);
    this.langSubject.next(lang);
  }
}
