import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
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
export class OrderCreateComponent {
  private orderService = inject(OrderService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private langService = inject(LanguageService);

  t = this.langService.t;
  errorMessage: string = '';

  order: OrderRequest = {
    capacity: 1,
    apartmentClass: '',
    stayDays: 1
  };

  get currentUser(): string {
    return this.authService.username;
  }

  onSubmit() {
    this.errorMessage = '';

    this.orderService.createOrder(this.order).subscribe({
      next: () => {
        this.router.navigate(['/orders']);
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = this.t.saveError || 'Ошибка при создании заказа';
      }
    });
  }
}
