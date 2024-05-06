import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { tamanhoBebidas } from './bebidas';
import { BebidaDTO, BebidaTamanhoValorDTO } from '../../../shared/models/BebidaDTO';
import { ProdutosService } from '../produtos.service';

@Component({
  selector: 'app-bebidas',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './bebidas.component.html',
  styleUrl: './bebidas.component.scss'
})
export class BebidasComponent {
  id : number = 0
  nome: string = '';
  valor = '0';
  tamanho = '';
  tamanhoBebidas = tamanhoBebidas

  listaItem : BebidaDTO[] = [];

  constructor(
    private _produtoService : ProdutosService
  ){
    this._produtoService.getListaDeBebidas().then((response)=>{
      this.listaItem = response.content
    })
  }

  getValorBebidaByTamanho(tamanho : string , listaTamanhoValor :BebidaTamanhoValorDTO[] | undefined):string{
    if(!listaTamanhoValor){
      return '';
    }
    let dado = listaTamanhoValor.find(item => item.tamanho && item.tamanho == tamanho)
    if(dado && dado.valor){
      return dado.valor.toString();
    }
    return '';
  }

  adicionar(){

  }
  excluir(){

  }
}
