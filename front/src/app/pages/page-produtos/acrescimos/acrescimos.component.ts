import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { PizzaAcrescimoDTO } from '../../../shared/models/PizzaDTO';
import { ProdutosService } from '../produtos.service';

@Component({
  selector: 'app-acrescimos',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './acrescimos.component.html',
  styleUrl: './acrescimos.component.scss'
})
export class AcrescimosComponent {
  id : string = '';
  nome: string = '';
  valorMedade = '0';
  valorToda = '0';

  listaItem : PizzaAcrescimoDTO[] = [];

  constructor(
    private _produtoService : ProdutosService
  ){
    this._produtoService.getListaDeAcrescimos().then((response)=>{
      this.listaItem = response.content
    })
  }
  adicionar(){

  }
  excluir(){

  }
}
