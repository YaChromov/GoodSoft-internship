import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { OrderService, OrderResponse } from '../../services/order.service';
import { LanguageService } from '../../services/language.service';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { OrderStatus } from '../../models/order-status.enum';

@Component({
  selector: 'app-order-pending',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule],
  templateUrl: './order-pending.component.html',
  styleUrls: ['./order-pending.component.css']
})
export class OrderPendingComponent implements OnInit {
  private readonly orderService: OrderService = inject(OrderService);
  private readonly langService: LanguageService = inject(LanguageService);
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  public readonly OrderStatus = OrderStatus;

  public t: any = this.langService.t;
  public pendingOrders: OrderResponse[] = [];
  public loading: boolean = false;

  public ngOnInit(): void {
    this.langService.currentLang$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((): void => {
        this.t = this.langService.t;
      });

    this.loadPending();
  }

  public loadPending(): void {
    this.loading = true;
    this.orderService.getOrders()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (allOrders: OrderResponse[]): void => {
          this.pendingOrders = allOrders.filter(
            (o: OrderResponse) => o.status === OrderStatus.PENDING
          );
          this.loading = false;
        },
        error: (err: unknown): void => {
          this.handleError(this.t.loadOrdersError, err);
          this.loading = false;
        }
      });
  }

  public changeStatus(
    id: number,
    status: OrderStatus.CONFIRMED | OrderStatus.REJECTED
  ): void {
    const question: string = status === OrderStatus.CONFIRMED
      ? `${this.t.confirmStatus}?`
      : `${this.t.rejectStatus}?`;

    if (confirm(`${question} (ID: #${id})`)) {
      this.orderService.updateStatus(id, status)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: (): void => {
            this.pendingOrders = this.pendingOrders.filter((o: OrderResponse) => o.id !== id);
          },
          error: (err: unknown): void => {
            this.handleError(this.t.saveError, err);
            alert(this.t.saveError);
          }
        });
    }
  }

  private handleError(message: string, err: unknown): void {
    console.error(message, err);
  }
}
