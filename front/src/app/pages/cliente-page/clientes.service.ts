import { Injectable } from '@angular/core';
import { ApiServicesService } from '../../services/api-services.service';
import { Page } from '../../shared/models/RestResponse';
import { ClienteDTO } from '../../shared/models/ClienteDTO';

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

  getDadosClienteByTelefone(telefone : string) : Promise<ClienteDTO> {
    return this._apiService.get('cliente/telefone/'+ telefone);
  }
  getDadosClienteById(id : number) : Promise<ClienteDTO> {
    return this._apiService.get('cliente/'+ id);
  }
  adicionaCliente(cliente: ClienteDTO) : Promise<ClienteDTO> {
    return this._apiService.post('cliente',cliente);
  }
  excluirCliente(id : number) : Promise<string> {
    return this._apiService.delete('cliente/'+ id);
  }
}
