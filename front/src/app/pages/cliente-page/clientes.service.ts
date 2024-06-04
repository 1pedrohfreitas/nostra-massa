import { Injectable } from '@angular/core';
import { ApiServicesService } from '../../services/api-services.service';
import { Page } from '../../shared/models/RestResponse';
import { ClienteDTO } from '../../shared/models/ClienteDTO';
import { PedidoDTO } from '../../shared/models/PedidoDTO';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  constructor(
    private _apiService: ApiServicesService
  ) { }

  getListaClientes() : Promise<Page<ClienteDTO>> {
    return this._apiService.get('cliente');
  }
  getListaPedidosByClientes(telefone : string) : Promise<Page<PedidoDTO>> {
    return this._apiService.get('pedido/telefone/'+telefone);
  }

  getDadosClienteByTelefone(telefone : string) : Promise<ClienteDTO> {
    return this._apiService.get('cliente/telefone/'+ telefone);
  }
  getDadosClienteById(id : number) : Promise<ClienteDTO> {
    return this._apiService.get('cliente/'+ id);
  }
  adicionaCliente(cliente: ClienteDTO) : Promise<ClienteDTO> {
    cliente.enderecoDescricao = cliente.rua + ', N: ' + cliente.numero;

    if(cliente.bloco != null && cliente.bloco != ''){
      cliente.enderecoDescricao = cliente.enderecoDescricao + ', Bloco: ' + cliente.bloco;
    }

    if(cliente.apartamento != null && cliente.apartamento != ''){
      cliente.enderecoDescricao = cliente.enderecoDescricao + ', AP: ' + cliente.apartamento;
    }
    cliente.enderecoDescricao = cliente.enderecoDescricao + ', Bairro: ' + cliente.bairro;
    if(cliente.complemento != null && cliente.complemento != ''){
      cliente.enderecoDescricao = cliente.enderecoDescricao + ', Comp: ' + cliente.complemento;
    }
    return this._apiService.post('cliente',cliente);
  }
  excluirCliente(id : number) : Promise<string> {
    return this._apiService.delete('cliente/'+ id);
  }
}
