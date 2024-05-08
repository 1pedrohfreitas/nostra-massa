import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';
import { ModalItemPizzaComponent } from './pedido-manual/modal-item-pizza/modal-item-pizza.component';
import { ModalEnderecoComponent } from '../../componentes/modal-endereco/modal-endereco.component';
import { ModalClienteComponent } from './pedido-manual/modal-cliente/modal-cliente.component';
import { ModalItemBebidaComponent } from './pedido-manual/modal-item-bebida/modal-item-bebida.component';
import { PreviaPedidoComponent } from './previa-pedido/previa-pedido.component';

@NgModule({
  declarations: [
    ModalItemPizzaComponent,
    ModalEnderecoComponent,
    ModalClienteComponent,
    ModalItemBebidaComponent,
    PreviaPedidoComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
  ],
  exports: [
    ModalItemPizzaComponent,
    ModalEnderecoComponent,
    ModalClienteComponent,
    ModalItemBebidaComponent,
    PreviaPedidoComponent,
  ],
})
export class PedidoModule { }
