import { Injectable } from '@angular/core';
import { ApiServicesService } from './api-services.service';

@Injectable({
  providedIn: 'root'
})
export class TelegramServiceService {

  constructor(
    private _apiService: ApiServicesService
  ) { }

  enviaTexto(chat_id : string, text : string) : Promise<string> {
    return this._apiService.post('telegram',{
      chat_id,
      text
    });
  }
}
