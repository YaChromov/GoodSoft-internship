import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { OrderStatus } from '../models/order-status.enum';

export interface OrderRequest {
  capacity: number;
  apartmentClass: string;
  stayDays: number;
}

export interface OrderResponse {
  id: number;
  clientUsername: string;
  adminUsername?: string;
  capacity: number;
  apartmentClass: string;
  stayDays: number;
  status: OrderStatus;
  paid: boolean;
  createdAt: string;
  processedAt?: string;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private readonly http: HttpClient = inject(HttpClient);
  private readonly authService: AuthService = inject(AuthService);

  private readonly apiUrl: string = '/api/orders';

  public getOrders(): Observable<OrderResponse[]> {
    const url: string = this.authService.isAdmin ? this.apiUrl : `${this.apiUrl}/my`;
    return this.http.get<OrderResponse[]>(url);
  }

  public createOrder(order: OrderRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(this.apiUrl, order);
  }

  public deleteOrder(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public payOrder(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/pay`, {});
  }

  public updateStatus(
    id: number,
    newStatus: OrderStatus.CONFIRMED | OrderStatus.REJECTED
  ): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/status`, {}, {
      params: { newStatus }
    });
  }
}
