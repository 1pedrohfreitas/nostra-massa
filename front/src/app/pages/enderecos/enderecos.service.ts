import { Injectable } from '@angular/core';
import { BairroDTO, RuaDTO } from '../../shared/models/ClienteDTO';
import { ApiServicesService } from '../../services/api-services.service';
import { Page } from '../../shared/models/RestResponse';

@Injectable({
  providedIn: 'root'
})
export class EnderecosService {

  constructor(
    private _apiService: ApiServicesService
  ) { }
  adicionaRua(rua : RuaDTO) : Promise<RuaDTO>{
    return this._apiService.post('endereco/rua',rua);
  }

  getRua(id : number) : Promise<RuaDTO>{
    return this._apiService.get('endereco/rua/'+ id);
  }

  deleteRua(id : number) : Promise<string>{
    return this._apiService.delete('endereco/rua/'+ id);
  }

  getListaDeRuas() : Promise<Page<RuaDTO>>{
    return this._apiService.get('endereco/rua');
  }

  atualizaRua(id : number, rua : RuaDTO) : Promise<RuaDTO>{
    return this._apiService.put('endereco/rua/'+ id,rua);
  }
  
  adicionaBairro(bairro : BairroDTO) : Promise<BairroDTO>{
    return this._apiService.post('endereco/bairro',bairro);
  }

  getBairro(id : number) : Promise<BairroDTO>{
    return this._apiService.get('endereco/bairro/'+ id);
  }

  deleteBairro(id : number) : Promise<string>{
    return this._apiService.delete('endereco/bairro/'+ id);
  }

  getListaDeBairros() : Promise<Page<BairroDTO>>{
    return this._apiService.get('endereco/bairro');
  }

  atualizaBairro(id : number, bairro : BairroDTO) : Promise<BairroDTO>{
    return this._apiService.put('endereco/bairro/'+ id,bairro);
  }
}
