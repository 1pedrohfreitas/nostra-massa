import { Injectable } from '@angular/core';
import { BairroDTO, RuaDTO } from '../shared/models/ClienteDTO';
import { FrontDataServiceDTO } from '../shared/dtos/FrontDataServiceDTO';
import { BebidaDTO } from '../shared/models/BebidaDTO';
import { PizzaAcrescimoDTO, PizzaSaborDTO } from '../shared/models/PizzaDTO';
import { InputSelectOption } from 'pedrohfreitas-lib';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageServiceService {

  listaBebidas : BebidaDTO[] = []
  listaPizzas : PizzaSaborDTO[]= []
  listaPizzaAcrescimo : PizzaAcrescimoDTO[] = []

  constructor() { 
    this.handleLocalStorageDataFront();
  }


  frontDataServiceDTO = new FrontDataServiceDTO;
  
  handleLocalStorageDataFront(){
    const storedListaBebidas = localStorage.getItem('listaBebidas');
    const storedListaPizzas = localStorage.getItem('listaPizzaSabores');
    const storedListaPizzasAcrescimos = localStorage.getItem('listaPizzaAcrescimos');
    
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
