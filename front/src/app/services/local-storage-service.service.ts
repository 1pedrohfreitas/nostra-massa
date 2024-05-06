import { Injectable } from '@angular/core';
import { InputSelectOption } from '../componentes/input-select/input-select';
import { BairroDTO, RuaDTO } from '../shared/models/ClienteDTO';
import { FrontDataServiceDTO } from '../shared/dtos/FrontDataServiceDTO';
import { BebidaDTO } from '../shared/models/BebidaDTO';
import { PizzaAcrescimoDTO, PizzaSaborDTO } from '../shared/models/PizzaDTO';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageServiceService {


  listaTelefones : string[]= []
  listaRuas : RuaDTO[] = []
  listaBairros : BairroDTO[]= []
  listaBebidas : BebidaDTO[] = []
  listaPizzas : PizzaSaborDTO[]= []
  listaPizzaAcrescimo : PizzaAcrescimoDTO[] = []

  constructor() { 
    this.handleLocalStorageDataFront();
  }


  frontDataServiceDTO = new FrontDataServiceDTO;
  
  handleLocalStorageDataFront(){
    const storedListaRuas = localStorage.getItem('listaRuas');
    const storedListaBairros = localStorage.getItem('listaBairros');
    const storedListaBebidas = localStorage.getItem('listaBebidas');
    const storedListaPizzas = localStorage.getItem('listaPizzaSabores');
    const storedListaPizzasAcrescimos = localStorage.getItem('listaPizzaAcrescimos');
    
    if(storedListaRuas != null){
      this.listaRuas = JSON.parse(storedListaRuas);
    }
    if(storedListaBairros != null){
      this.listaBairros = JSON.parse(storedListaBairros);
    }
    if(storedListaBebidas != null){
      this.listaBebidas = JSON.parse(storedListaBebidas);
    }
    if(storedListaPizzas != null){
      this.listaPizzas = JSON.parse(storedListaPizzas);
    }
    if(storedListaPizzasAcrescimos != null){
      this.listaPizzaAcrescimo = JSON.parse(storedListaPizzasAcrescimos);
    }
  }

  converteListaItemParaOption(lista : any[], optionField : string, valueField : string, uniqueField : boolean): InputSelectOption[]{
    let listaRetorno : InputSelectOption[] = []
    listaRetorno = lista.map((item)=>{
      if(uniqueField){
        return {
          option: item.toString(),
          value: item.toString()
        }
      } else{
        return {
          option: item[optionField].toString(),
          value: item[valueField].toString()
        }
      }
      
    })
    return listaRetorno;
  }
}
