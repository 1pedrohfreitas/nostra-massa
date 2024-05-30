import { Injectable } from '@angular/core';
import { ApiServicesService } from './api-services.service';
import { Page } from '../shared/models/RestResponse';

@Injectable({
  providedIn: 'root'
})
export class AutoCompleteServiceService {

  constructor(
    private _apiService: ApiServicesService
  ) { }

  autoCompleteBairro(conteudo : string) : Promise<Page<string>> {
    return this._apiService.get('endereco/bairro/autoComplete/'+conteudo.toUpperCase().replaceAll(' ','_'));
  }

  autoCompleteRua(conteudo : string) : Promise<Page<string>> {
    return this._apiService.get('endereco/rua/autoComplete/'+conteudo.toUpperCase().replaceAll(' ','_'));
  }
}
