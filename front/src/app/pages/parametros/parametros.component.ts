import { Component } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { ParametrosService } from './parametros.service';
import { InputSelectOption } from '../../componentes/input-select/input-select';
import { LocalStorageServiceService } from '../../services/local-storage-service.service';
import { Parametros } from '../../shared/models/Parametros';

@Component({
  selector: 'app-parametros',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './parametros.component.html',
  styleUrl: './parametros.component.scss'
})
export class ParametrosComponent {

  listaImpressoras : InputSelectOption[] = [];

  parametros : Parametros = new Parametros

  constructor(
    private _parametrosService : ParametrosService,
    private _localStorageService : LocalStorageServiceService
  ){
    this._parametrosService.getListaImpressoras().then((response)=>{
      if(response.content.length > 0){
        this.listaImpressoras = this._localStorageService.converteListaItemParaOption(response.content, '','',true)
      }
    })

    this._parametrosService.getParametros().then((response)=>{
      this.parametros = response
    })
  }

  setImpressora(impressora : InputSelectOption){
  }

  atualizaParametros(){
    this._parametrosService.atualizaParametros(this.parametros);
  }

}
