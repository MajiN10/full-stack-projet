import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './features/catalog/product-list/product-list.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { CartViewComponent } from './features/cart/cart-view/cart-view.component';
import { OrderHistoryComponent } from './features/orders/order-history/order-history.component';
import { ProfileViewComponent } from './features/profile/profile-view/profile-view.component';

const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'products', component: ProductListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'cart', component: CartViewComponent },
  { path: 'orders', component: OrderHistoryComponent },
  { path: 'profile', component: ProfileViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
