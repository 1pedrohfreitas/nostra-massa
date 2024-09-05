import { Injectable } from '@angular/core';
import { Page } from '../shared/models/RestResponse';
import { ApiServicesService } from './api-services.service';

@Injectable({
  providedIn: 'root'
})
export class CRUDBasicService<T> {
  modelType : string = '';
  constructor(
    private _apiService: ApiServicesService,
  ) {
    
    this.modelType = '';
  }

  setModelType (modelTypeRef : string){
    this.modelType = modelTypeRef;
  }
  async create(data : any) : Promise<T> {
    // const dataRequest = (data as any).data
    console.log(data as object)
    return this._apiService.post(`crud-basic/${this.modelType}`,{data});
  }

  async readList() : Promise<Page<T>> {
    return this._apiService.get(`crud-basic/${this.modelType}`);
  }

  async readItem(id : string) : Promise<T> {
    return this._apiService.get(`crud-basic/${this.modelType}/${id}`);
  }

  async readItemAndChildren(id : string) : Promise<T> {
    return this._apiService.get(`crud-basic/${this.modelType}`);
  }

  async update(id: string, data : T) : Promise<T> {
    return this._apiService.put(`crud-basic/${this.modelType}/${id}`,{data});
  }

  async delete(id : string) : Promise<T> {
    return this._apiService.delete(`crud-basic/${this.modelType}/${id}`);
  }

}
