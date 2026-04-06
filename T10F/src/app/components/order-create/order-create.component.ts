import { Component, inject, OnInit, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { OrderService, OrderRequest } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { LanguageService } from '../../services/language.service';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-order-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, InputTextModule],
  templateUrl: './order-create.component.html',
  styleUrls: ['./order-create.component.css']
})
export class OrderCreateComponent implements OnInit {
  public t: any;
  public errorMessage: string = '';
  public order: OrderRequest = {
    capacity: 1,
    apartmentClass: '',
    stayDays: 1
  };

  private readonly orderService: OrderService = inject(OrderService);
  private readonly authService: AuthService = inject(AuthService);
  private readonly router: Router = inject(Router);
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  public get currentUser(): string {
    return this.authService.username;
  }

  public ngOnInit(): void {
    this.t = this.langService.t;

    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
      });
  }

  public onSubmit(): void {
    this.errorMessage = '';

    this.orderService.createOrder(this.order)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (): void => {
          this.handleSuccessNavigation();
        },
        error: (err: unknown): void => {
          this.handleError(err);
        }
      });
  }

  private handleSuccessNavigation(): void {
    void this.router.navigate(['/orders']);
  }

  private handleError(err: unknown): void {
    console.error('Order creation failed:', err);
    this.errorMessage = this.t.saveError || 'Ошибка при создании заказа';
  }
}
