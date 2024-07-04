import { Injectable } from '@angular/core';
import { FrontDataServiceDTO } from '../shared/dtos/FrontDataServiceDTO';
import { HttpClient } from '@angular/common/http';
import { ApiServicesService } from './api-services.service';
import { LocalStorageServiceService } from './local-storage-service.service';


listaPizzaSabores : [] = [];

@Injectable({
  providedIn: 'root'
})
export class GetDadosServiceService {
  dadosGeral : FrontDataServiceDTO = new FrontDataServiceDTO();
  constructor(
    private _apiService: ApiServicesService,
    private _localStorageService : LocalStorageServiceService
    ) {}
    getDadosGeral(){
      this._apiService.get('getDadosGeral').then((response) => {
        localStorage.setItem('listaBebidas', JSON.stringify(response.bebidas));
          localStorage.setItem('listaPizzaSabores', JSON.stringify(response.pizzasSabor));
          // localStorage.setItem('listaRuas', JSON.stringify(response.ruas));
          // localStorage.setItem('listaBairros', JSON.stringify(response.bairros));
          localStorage.setItem('listaPizzaAcrescimos', JSON.stringify(response.pizzasAcrescimos));
          this._localStorageService.handleLocalStorageDataFront();
        })
    }
  
}
