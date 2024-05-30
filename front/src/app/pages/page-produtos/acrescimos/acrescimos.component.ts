import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { PizzaAcrescimoDTO } from '../../../shared/models/PizzaDTO';
import { ProdutosService } from '../produtos.service';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-acrescimos',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './acrescimos.component.html',
  styleUrl: './acrescimos.component.scss'
})
export class AcrescimosComponent {
  pizzaAcrescimo : PizzaAcrescimoDTO = new PizzaAcrescimoDTO

  tableCabecalho = ['Nome','M Metade','M Toda','G Metade','G Toda','GG Metade','GG Toda'];
  tableColunas = ['nome','tamanhoMediaMetade','tamanhoMediaToda','tamanhoGrandeMetade','tamanhoGrandeToda','tamanhoGiganteMetade','tamanhoGiganteToda'];
  tableColunasSize = ['40','10','10','10','10','10','10'];
  tableData : any[] = [];
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
    this._produtoService.getListaDeAcrescimos().then((response)=>{
      console.log(response)
      this.tableData = [];
      response.content.forEach(value =>{
        this.tableData.push({
          id : value.id,
          nome: value.nome,
          tamanhoGiganteMetade: value.tamanhoGiganteMetade.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
          tamanhoGiganteToda: value.tamanhoGiganteToda.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
          tamanhoGrandeMetade: value.tamanhoGrandeMetade.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
          tamanhoGrandeToda: value.tamanhoGrandeToda.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
          tamanhoMediaMetade: value.tamanhoMediaMetade.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
          tamanhoMediaToda: value.tamanhoMediaToda.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }),
        });
      })
    })
  }

  tableActionDataClick(action : ButtonActionClick){
    this.pizzaAcrescimo = action.data
  }
  adicionar(){

  }
  excluir(){

  }
}
