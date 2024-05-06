import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { ProdutosService } from '../produtos.service';
import { PizzaSaborIngredienteDTO } from '../../../shared/models/PizzaDTO';

@Component({
  selector: 'app-ingredientes',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './ingredientes.component.html',
  styleUrl: './ingredientes.component.scss'
})
export class IngredientesComponent {
  id : string = '';
  nome: string = '';

  listaItem : PizzaSaborIngredienteDTO[] = [];

  constructor(
    private _produtoService : ProdutosService
  ){
    this._produtoService.getListaDeIngredientes().then((response)=>{
      this.listaItem = response.content
    })
  }

  adicionar(){

  }
  excluir(){

  }
}
