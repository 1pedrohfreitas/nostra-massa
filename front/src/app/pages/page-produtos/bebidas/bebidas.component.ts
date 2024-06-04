import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { BebidaDTO, BebidaTamanhoValorDTO } from '../../../shared/models/BebidaDTO';
import { ProdutosService } from '../produtos.service';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-bebidas',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './bebidas.component.html',
  styleUrl: './bebidas.component.scss'
})
export class BebidasComponent {

  bebidaDTO: BebidaDTO = new BebidaDTO

  tamanhoBebidas: string[] = []
  // = tamanhoBebidas

  tableCabecalho: any[] = [];
  tableColunas: any[] = [];
  tableColunasSize: any[] = [];
  tableData: any[] = [];
  tableActionsButtons: ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]

  constructor(
    private _produtoService: ProdutosService
  ) {
    this.getListaTamanhoBebidas()
  }

  getListaTamanhoBebidas() {
    
    
    this._produtoService.getListaTamanhoDeBebidas().then((response) => {
      let cabecalho : any[] = []
      cabecalho.push('Nome')
      let colunas = []
      colunas.push('nome')
      let colunasSize = []
      colunasSize.push('50')
      response.content.forEach(value => {
        cabecalho.push(value);
        colunas.push(value);
        colunasSize.push('10');
        this.tamanhoBebidas.push(value);
      })
      this.tableCabecalho = cabecalho
      this.tableColunas = colunas
      this.tableColunasSize = colunasSize

    }).then((value)=>{
      this.getListaDeBebidas();
    })
  }

  getListaDeBebidas() {
    this._produtoService.getListaDeBebidas().then((response) => {
      let data : any[] = []
      response.content.forEach(value => {
        let item: any = {
          id: value.id,
          nome: value.nome
        }
        this.tamanhoBebidas.forEach(tb => {
          item[tb] = value.tamanhoValor.find(tv => tv.tamanho == tb) != undefined ? value.tamanhoValor.find(tv => tv.tamanho == tb)?.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) : '';
        })
        data.push(item);
      })
      this.tableData = data
    })
  }

  tableActionDataClick(action: ButtonActionClick) {
    this.bebidaDTO = action.data
  }

  adicionar() {

  }
  excluir() {

  }
}
