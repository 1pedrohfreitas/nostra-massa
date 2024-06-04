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
    return this._apiService.post('pedido/itemPedido/'+idPedido,item);
  }
  //Novo
  getItemPedido(idItem : number){
    return this._apiService.get('pedido/itemPedido/'+idItem);
  }
  removeItemPedido(idPedido : number ,idItem : number){
    return this._apiService.delete('pedido/itemPedido/'+idPedido+'/'+idItem);
  }
  
  cancelaPedido(id : number) : Promise<PedidoDTO> {
    return this._apiService.put('pedido/cancela/'+ id,null);
  }

  salvarEImprimir(pedido : PedidoDTO) : Promise<PedidoDTO> {
    return this._apiService.post('pedido/imprimirPedido',pedido);
  }

  getDatasReferencias():Promise<Page<string>>{
    return this._apiService.get('pedido/datas');
  }

  getTaxaDeEntrega(bairro : string):Promise<number>{
    return this._apiService.get('endereco/bairro/taxaEntrega/'+ bairro);
  }

  getListaPedidos(data : string):Promise<Page<PedidoDTO>>{
    return new Promise((resolve,reject)=>{
      let dataPage :Page<PedidoDTO> = new Page;
      const dataContent :PedidoDTO[] = []
      this._apiService.get('pedido/datas/'+data).then(response =>{
        if(response != undefined){
          dataPage = response
          response.content.forEach((value : PedidoDTO)=>{
            const dataFormatada = new Date(value.dataPedido).toLocaleString('pt-BR', {
              year: 'numeric',
              month: '2-digit',
              day: '2-digit',
              hour: 'numeric',
              minute: 'numeric',
            }).replace(',','');
            value.dataPedido = dataFormatada
            value.enderecoDescricao = !value.entrega ? 'BUSCAR' : value.enderecoDescricao
            dataContent.push(value)
          })
          dataPage.content = dataContent;
        }
        resolve(dataPage)
      })
    })
  }

  

  //Apenas para Pizza

  getPedido(idItemPedido : number) : Promise<PedidoItemDTO>{
    return this._apiService.get('pedido/itemPedido/'+idItemPedido)
  }

  
}
