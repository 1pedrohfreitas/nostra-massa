import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { ProdutosService } from '../produtos.service';
import { PizzaSaborIngredienteDTO } from '../../../shared/models/PizzaDTO';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-ingredientes',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './ingredientes.component.html',
  styleUrl: './ingredientes.component.scss'
})
export class IngredientesComponent {

  pizzaSaborIngredienteDTO : PizzaSaborIngredienteDTO = new PizzaSaborIngredienteDTO;

  tableCabecalho = ['Nome'];
  tableColunas = ['nome'];
  tableColunasSize = ['100'];
  tableData : PizzaSaborIngredienteDTO[] = [];
  tableActionsButtons : ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]

  constructor(
    private _produtoService : ProdutosService
  ){
    this.getListaIngredientes();
  }

  getListaIngredientes(){
    this._produtoService.getListaDeIngredientes().then((response)=>{
      this.tableData = response.content
    })
  }

  tableActionDataClick(action : ButtonActionClick){
    this.pizzaSaborIngredienteDTO = action.data
  }

  adicionar(){
    if(this.pizzaSaborIngredienteDTO.nome != ''){
      this._produtoService.adicionaIngrediente(this.pizzaSaborIngredienteDTO).then((response)=>{
        this.getListaIngredientes();
      });
    }
    
  }
  excluir(){

  }
}
