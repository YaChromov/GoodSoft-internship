import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService, OrderResponse } from '../../services/order.service';
import { LanguageService } from '../../services/language.service';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-order-pending',
  standalone: true,
  imports: [CommonModule, TableModule, ButtonModule],
  templateUrl: './order-pending.component.html',
  styleUrls: ['./order-pending.component.css']
})
export class OrderPendingComponent implements OnInit {
  private orderService = inject(OrderService);
  private langService = inject(LanguageService);

  t = this.langService.t;
  pendingOrders: OrderResponse[] = [];

  ngOnInit() {
    this.loadPending();
  }

  loadPending() {
    this.orderService.getOrders().subscribe({
      next: (allOrders) => {
        this.pendingOrders = allOrders.filter(o => o.status === 'PENDING');
      },
      error: (err) => console.error(this.t.loadOrdersError, err)
    });
  }

  changeStatus(id: number, status: 'CONFIRMED' | 'REJECTED') {
    const question = status === 'CONFIRMED'
      ? `${this.t.confirmStatus}?`
      : `${this.t.rejectStatus}?`;

    if (confirm(`${question} (ID: #${id})`)) {
      this.orderService.updateStatus(id, status).subscribe({
        next: () => {
          this.pendingOrders = this.pendingOrders.filter(o => o.id !== id);
        },
        error: (err) => {
          console.error(err);
          alert(this.t.saveError);
        }
      });
    }
  }
}
