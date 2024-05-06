import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, lastValueFrom } from 'rxjs';
import { Notificacao, ResponseDTO, ResponseMessagemDTO } from '../shared/models/RestResponse';
import {  environment } from '../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class ApiServicesService {
  private novaNotificacao = new Subject<Notificacao>();
  constructor(
    private _http: HttpClient,
  ) { }
  async get(url : string) {
    const response = await lastValueFrom(this._http.get<ResponseDTO<any>>(`${environment.url}${url}`));
    this.printMsg(response.msgs);
    return response.data;
  }

  async post(url : string, data : any) {
    const response = await lastValueFrom(this._http.post<ResponseDTO<any>>(`${environment.url}${url}`,data));
    this.printMsg(response.msgs);
    return response.data;
  }

  async put(url : string, data : any) {
    const response = await lastValueFrom(this._http.put<ResponseDTO<any>>(`${environment.url}${url}`,data));
    this.printMsg(response.msgs);
    return response.data;
  }

  async delete(url : string) {
    const response = await lastValueFrom(this._http.delete<ResponseDTO<any>>(`${environment.url}${url}`));
    this.printMsg(response.msgs);
    return response.data;
    
  }

  printMsg(msgs ?: ResponseMessagemDTO[]){
    if(msgs != undefined && msgs.length > 0){
      for(let i = 0; i < msgs.length; i ++){
        let notificacao : Notificacao = {
          tipo : msgs[i].typeMsg,
          titulo : msgs[i].titulo,
          mensagem : msgs[i].msg
        }    
        this.imprimiNotificacao(notificacao)
      }
    }
  }
  imprimiNotificacao(notificacao : Notificacao) {
    this.novaNotificacao.next(notificacao);
  }

  get imprimiNotificacaoAppComponent(){
    return this.novaNotificacao.asObservable();
  }

}
