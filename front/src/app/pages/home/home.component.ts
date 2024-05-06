import { Component } from '@angular/core';
import { GetDadosServiceService } from '../../services/get-dados-service.service';
import { PedidoDTO } from '../../shared/models/PedidoDTO';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
})
export class HomeComponent {

  constructor(
      private getDadosService: GetDadosServiceService
    ){
    // getDadosService.getDadosGeral();
  }
  

  pedido : PedidoDTO | null = null;

  teste = '1'
  
  tabIndex : number = 0

  onKeyDown(event: KeyboardEvent) {
    if (event.key === 'F1') {
      this.tabIndex = 0;
    }
    if (event.key === 'F2') {
      this.tabIndex = 1;
    }
    if (event.key === 'F3') {
      this.tabIndex = 3;
    }
  }

}
