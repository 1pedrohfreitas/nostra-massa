import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { PedidoService } from './pedido.service';

@Component({
  selector: 'app-pedido-page',
  standalone: true,
  imports: [RouterOutlet,RouterLink, RouterLinkActive],
  templateUrl: './pedido-page.component.html',
})
export class PedidoPageComponent {

  constructor(
    private router: Router,
    private _pedidoService : PedidoService,
  ){

  }

  criarPedidoManual(){
    this.router.navigate(['/pedido/manual',{
    }])
  }

}
