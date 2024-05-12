import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { InputSelectOption } from '../../../componentes/input-select/input-select';
import { PedidoDTO } from '../../../shared/models/PedidoDTO';
import { PedidoService } from '../pedido.service';
import { LocalStorageServiceService } from '../../../services/local-storage-service.service';
import { Router } from '@angular/router';
import { RelatorioServiceService } from '../../../services/relatorio-service.service';

@Component({
  selector: 'app-page-lista-pedidos',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './page-lista-pedidos.component.html',
  styleUrl: './page-lista-pedidos.component.scss'
})
export class PageListaPedidosComponent {
  datasPedidos : InputSelectOption[] = []
  listaPedidos : PedidoDTO[] = [];
  quantidadeItems : number = 0
  dataReferencia : string = '';

  constructor(
    private _pedidoService: PedidoService,
    private _localStorage: LocalStorageServiceService,
    private router: Router,
    private _relatorioService: RelatorioServiceService
  ){
    this._pedidoService.getDatasReferencias().then((response)=>{
      
      this.datasPedidos =  this._localStorage.converteListaItemParaOption(response.content,'','',true);
      if(this.datasPedidos.length > 0){
        this.setDataFilter(this.datasPedidos[0])
        this.dataReferencia = this.datasPedidos[0].value;
      }
            
    })
  }

  goToEditItem(idPedido : number | undefined){
    this.router.navigate(['/pedido/manual',{
      idPedido : idPedido
    }])
  }

  setDataFilter(dataSelect : InputSelectOption){
    this._pedidoService.getListaPedidos(dataSelect.value).then((response)=>{
      this.listaPedidos = response.content
      this.quantidadeItems = response.totalElements;
    })
  }

  gerarRelatorioDiario(){
    let nomeRelatorio = `RelatorioDiario_${this.dataReferencia.replaceAll('-','_')}`;
    this._relatorioService.gerarRelatorio(nomeRelatorio,this.geraArquivoRelatorio()).then((response)=>{

    })
  }
  geraArquivoRelatorio(): string {
    let report = `Relatório Diario - ${this.dataReferencia} \r\n`
      + '========================\r\n';
      let valorTotal = 0;
      console.log(this.listaPedidos)
      this.listaPedidos.reverse().forEach((value)=>{
        report = report + `Pedido: ${value.idPedido}\r\n`
        + `Valor: ${value.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}\r\n`
        + `Status: ${value.status}\r\n`
        + '========================\r\n';
        valorTotal = valorTotal + value.valor
      })
      report = report + `Valor Total: ${valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}`;
    return report;
  }
}
