import { Injectable } from '@angular/core';
import { ApiServicesService } from '../../services/api-services.service';
import { Page } from '../../shared/models/RestResponse';
import { ClienteDTO, EnderecoDTO } from '../../shared/models/ClienteDTO';

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
    return this._apiService.get('cliente/'+ telefone);
  }
  adicionaCliente(cliente: ClienteDTO) : Promise<ClienteDTO> {
    return this._apiService.post('cliente',cliente);
  }
  excluirCliente(id : number) : Promise<string> {
    return this._apiService.delete('cliente/'+ id);
  }
  getDadosEnderecoCliente(id : number) : Promise<EnderecoDTO> {
    return this._apiService.get('cliente/endereco/'+ id);
  }
  saveDadosEnderecoCliente(id : number, endereco : EnderecoDTO) : Promise<EnderecoDTO> {
    return this._apiService.post('cliente/endereco/'+id,endereco);
  }
}
