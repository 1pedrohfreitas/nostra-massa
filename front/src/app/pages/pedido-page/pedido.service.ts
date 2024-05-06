import { Injectable } from '@angular/core';
import { ApiServicesService } from '../../services/api-services.service';
import { PedidoDTO, PedidoItemDTO } from '../../shared/models/PedidoDTO';
import { Page } from '../../shared/models/RestResponse';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  constructor(
    private _apiService: ApiServicesService
  ) { }

  criarPedido() : Promise<PedidoDTO> {
    return this._apiService.post('pedido/novo',{});
  }
  
  getDadosPedido(id : number) : Promise<PedidoDTO> {
    return this._apiService.get('pedido/'+ id);
  }
  getDadosPedidoDiario(id : number) : Promise<PedidoDTO> {
    return this._apiService.get('pedido/diario/'+ id);
  }
  atualizaDadosPedido(id : number, pedido : PedidoDTO) : Promise<PedidoDTO> {
    return this._apiService.put('pedido/'+ id,pedido);
  }

  adicionaItemPedido(idPedido : number , item : PedidoItemDTO){
    return this._apiService.post('pedido/itemPedido/'+idPedido,item)
  }
  removeItemPedido(idPedido : number ,idItem : number){
    return this._apiService.delete('pedido/itemPedido/'+idPedido+'/'+idItem)
  }
  
  cancelaPedido(id : number) : Promise<PedidoDTO> {
    return this._apiService.put('pedido/cancela/'+ id,null);
  }

  salvarEImprimir(pedido : PedidoDTO) : Promise<PedidoDTO> {
    return this._apiService.post('pedido/imprimir',pedido);
  }

  getDatasReferencias():Promise<Page<string>>{
    return this._apiService.get('pedido/datas')
  }

  getListaPedidos(data : string):Promise<Page<PedidoDTO>>{
    return this._apiService.get('pedido/datas/'+data)
  }

  

  //Apenas para Pizza

  getPedido(idItemPedido : number) : Promise<PedidoItemDTO>{
    return this._apiService.get('pedido/itemPedido/'+idItemPedido)
  }

  
}
