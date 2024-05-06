import { Injectable } from '@angular/core';
import { ApiServicesService } from '../../services/api-services.service';
import { Page } from '../../shared/models/RestResponse';
import { Parametros } from '../../shared/models/Parametros';

@Injectable({
  providedIn: 'root'
})
export class ParametrosService {

  constructor(
    private _apiService: ApiServicesService
  ) { }
  getParametros(): Promise<Parametros> {
    return this._apiService.get('parametros');
  }
  atualizaParametros(parametros : Parametros): Promise<Parametros> {
    return this._apiService.put('parametros',parametros);
  }
  getListaImpressoras(): Promise<Page<string>> {
    return this._apiService.get('parametros/listaImpressoras');
  }
}
