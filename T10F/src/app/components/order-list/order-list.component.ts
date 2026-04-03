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
  orders: OrderResponse[] = [];
  loading: boolean = true;

  private orderService = inject(OrderService);
  private langService = inject(LanguageService);
  private destroyRef = inject(DestroyRef);
  public authService = inject(AuthService);

  t = this.langService.t;

  ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.t = this.langService.t;
      });

    this.loadOrders();
  }

  loadOrders(): void {
    this.loading = true;
    this.orderService.getOrders()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (data) => {
          this.orders = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Failed to load orders:', err);
          this.loading = false;
          alert(this.t.loadOrdersError || 'Error loading orders');
        }
      });
  }

  getTranslatedStatus(status: string): string {
    if (!status) return '';
    const key = ('status' + status.charAt(0).toUpperCase() + status.slice(1).toLowerCase()) as keyof TranslationKeys;
    return (this.t as any)[key] || status;
  }

  payOrder(id: number): void {
    if (confirm(`${this.t.paidCol}? #${id}`)) {
      this.orderService.payOrder(id).subscribe({
        next: () => {
          this.loadOrders();
        },
        error: (err) => {
          console.error('Payment error:', err);
          alert('Ошибка при проведении оплаты');
        }
      });
    }
  }

  deleteOrder(id: number): void {
    const confirmMsg = `${this.t.deleteOrderConfirm} ${id}?`;

    if (confirm(confirmMsg)) {
      this.orderService.deleteOrder(id).subscribe({
        next: () => {
          this.orders = this.orders.filter(o => o.id !== id);
        },
        error: (err) => {
          console.error('Delete error:', err);
          alert(this.t.deleteServerError || 'Ошибка при удалении');
        }
      });
    }
  }
}
