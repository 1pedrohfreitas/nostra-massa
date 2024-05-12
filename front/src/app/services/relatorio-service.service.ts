import { Injectable } from '@angular/core';
import { ApiServicesService } from './api-services.service';

@Injectable({
  providedIn: 'root'
})
export class RelatorioServiceService {

  constructor(
    private _apiService: ApiServicesService
  ) { }

  gerarRelatorio(nome : string, conteudo : string) : Promise<string> {
    return this._apiService.post('relatorio',{
      nome,
      conteudo
    });
  }
}
