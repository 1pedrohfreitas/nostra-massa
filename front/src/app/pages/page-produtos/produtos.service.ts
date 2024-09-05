import { Injectable } from '@angular/core';
import { PizzaAcrescimoDTO, PizzaSaborDTO, PizzaSaborIngredienteDTO } from '../../shared/models/PizzaDTO';
import { ApiServicesService } from '../../services/api-services.service';
import { Page } from '../../shared/models/RestResponse';
import { BebidaDTO } from '../../shared/models/BebidaDTO';

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {

  constructor(
    private _apiService: ApiServicesService
  ) { }

  create(data : any, field : string) : Promise<any>{
    return this._apiService.post(`product/${field}`,data);
  }
  getAll(field : string) : Promise<Page<any>>{
    return this._apiService.get(`product/${field}`);
  }

  adicionaAcrescimo(acrescimo : PizzaAcrescimoDTO) : Promise<PizzaAcrescimoDTO>{
    return this._apiService.post('produto/acrescimo',acrescimo);
  }

  getAcrescimo(id : number) : Promise<PizzaAcrescimoDTO>{
    return this._apiService.get('produto/acrescimo/'+ id);
  }

  deleteAcrescimo(id : number) : Promise<string>{
    return this._apiService.delete('produto/acrescimo/'+ id);
  }

  getListaDeAcrescimos() : Promise<Page<PizzaAcrescimoDTO>>{
    return this._apiService.get('produto/acrescimo');
  }

  atualizaAcrescimo(id : number, acrescimo : PizzaAcrescimoDTO) : Promise<PizzaAcrescimoDTO>{
    return this._apiService.put('produto/acrescimo/'+ id,acrescimo);
  }


  adicionaBebida(bebida : BebidaDTO) : Promise<BebidaDTO>{
    return this._apiService.post('produto/bebida',bebida);
  }

  getBebida(id : number) : Promise<BebidaDTO>{
    return this._apiService.get('produto/bebida/'+ id);
  }

  deleteBebida(id : number) : Promise<string>{
    return this._apiService.delete('produto/bebida/'+ id);
  }

  getListaTamanhoDeBebidas() : Promise<Page<string>>{
    return this._apiService.get('produto/bebidaTamanho');
  }

  getListaDeBebidas() : Promise<Page<BebidaDTO>>{
    return this._apiService.get('produto/bebida');
  }

  atualizaBebida(id : number, bebida : BebidaDTO) : Promise<BebidaDTO>{
    return this._apiService.put('produto/bebida/'+ id,bebida);
  }


  adicionaPizza(pizza : PizzaSaborDTO) : Promise<PizzaSaborDTO>{
    return this._apiService.post('produto/pizza',pizza);
  }

  getPizza(id : number) : Promise<PizzaSaborDTO>{
    return this._apiService.get('produto/pizza/'+ id);
  }

  deletePizza(id : number) : Promise<string>{
    return this._apiService.delete('produto/pizza/'+ id);
  }

  getListaDePizzas() : Promise<Page<PizzaSaborDTO>>{
    return this._apiService.get('produto/pizza');
  }

  atualizaPizza(id : number, pizza : PizzaSaborDTO) : Promise<PizzaSaborDTO>{
    return this._apiService.put('produto/pizza/'+ id,pizza);
  }

  
  adicionaIngrediente(ingrediente : PizzaSaborIngredienteDTO) : Promise<PizzaSaborIngredienteDTO>{
    return this._apiService.post('produto/ingrediente',ingrediente);
  }

  getIngrediente(id : number) : Promise<PizzaSaborIngredienteDTO>{
    return this._apiService.get('produto/ingrediente/'+ id);
  }

  deleteIngrediente(id : number) : Promise<string>{
    return this._apiService.delete('produto/ingrediente/'+ id);
  }

  getListaDeIngredientes() : Promise<Page<PizzaSaborIngredienteDTO>>{
    return this._apiService.get('produto/ingrediente');
  }

}
