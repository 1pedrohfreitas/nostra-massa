import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { InputSelectOption } from '../../../componentes/input-select/input-select';
import { PedidoDTO } from '../../../shared/models/PedidoDTO';
import { PedidoService } from '../pedido.service';
import { LocalStorageServiceService } from '../../../services/local-storage-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-lista-pedidos',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './page-lista-pedidos.component.html',
  styleUrl: './page-lista-pedidos.component.scss'
})
export class PageListaPedidosComponent {
  datasPedidos : InputSelectOption[] = []
  listaPedidos : PedidoDTO[] = [];
  quantidadeItems : number = 0
  dataReferencia : string = '';

  constructor(
    private _pedidoService: PedidoService,
    private _localStorage: LocalStorageServiceService,
    private router: Router,
  ){
    this._pedidoService.getDatasReferencias().then((response)=>{
      
      this.datasPedidos =  this._localStorage.converteListaItemParaOption(response.content,'','',true);
      if(this.datasPedidos.length > 0){
        this.setDataFilter(this.datasPedidos[0])
        this.dataReferencia = this.datasPedidos[0].value;
      }
            
    })
  }

  goToEditItem(idPedido : number | undefined){
    this.router.navigate(['/pedido/manual',{
      idPedido : idPedido
    }])
  }

  setDataFilter(dataSelect : InputSelectOption){
    this._pedidoService.getListaPedidos(dataSelect.value).then((response)=>{
      this.listaPedidos = response.content
      this.quantidadeItems = response.totalElements;
    })
  }
}
