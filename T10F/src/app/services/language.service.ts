import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { TRANSLATIONS } from '../constants/translations';


export type Lang = 'ru' | 'en';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private langSubject = new BehaviorSubject<Lang>(
    (localStorage.getItem('app_lang') as Lang) || 'ru'
  );

  public currentLang$ = this.langSubject.asObservable();

  get currentLang(): Lang {
    return this.langSubject.value;
  }


  get t() {
    return TRANSLATIONS[this.currentLang];
  }

  setLanguage(lang: Lang): void {
    localStorage.setItem('app_lang', lang);
    this.langSubject.next(lang);
  }
}
