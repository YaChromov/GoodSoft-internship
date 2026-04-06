import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';

import { OrderService, OrderResponse } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { LanguageService } from '../../services/language.service';
import { TranslationKeys } from '../../constants/translations';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    TagModule
  ],
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  private readonly orderService: OrderService = inject(OrderService);
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);
  public readonly authService: AuthService = inject(AuthService);

  public orders: OrderResponse[] = [];
  public loading: boolean = true;
  public t: TranslationKeys = this.langService.t;

  public ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
      });

    this.loadOrders();
  }

  public loadOrders(): void {
    this.loading = true;
    this.orderService.getOrders()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data: OrderResponse[]): void => {
          this.orders = data;
          this.loading = false;
        },
        error: (err: unknown): void => {
          this.handleLoadError(err);
        }
      });
  }

  public getTranslatedStatus(status: string): string {
    if (!status) return '';

    const key = `status${status.charAt(0).toUpperCase()}${status.slice(1).toLowerCase()}` as keyof TranslationKeys;
    return this.t[key] ? (this.t[key] as string) : status;
  }

  public payOrder(id: number): void {
    const confirmMsg: string = `${this.t.paidCol}? #${id}`;

    if (confirm(confirmMsg)) {
      this.orderService.payOrder(id)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: (): void => {
            this.loadOrders();
          },
          error: (err: unknown): void => {
            console.error('Payment error:', err);
            alert('Ошибка при проведении оплаты');
          }
        });
    }
  }

  public deleteOrder(id: number): void {
    const confirmMsg: string = `${this.t.deleteOrderConfirm} ${id}?`;

    if (confirm(confirmMsg)) {
      this.orderService.deleteOrder(id)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: (): void => {
            this.orders = this.orders.filter((o: OrderResponse) => o.id !== id);
          },
          error: (err: unknown): void => {
            console.error('Delete error:', err);
            alert(this.t.deleteServerError || 'Ошибка при удалении');
          }
        });
    }
  }

  private handleLoadError(err: unknown): void {
    console.error('Failed to load orders:', err);
    this.loading = false;
    alert(this.t.loadOrdersError || 'Error loading orders');
  }
}
