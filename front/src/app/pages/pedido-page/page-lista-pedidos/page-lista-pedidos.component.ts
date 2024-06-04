import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { PedidoDTO } from '../../../shared/models/PedidoDTO';
import { PedidoService } from '../pedido.service';
import { LocalStorageServiceService } from '../../../services/local-storage-service.service';
import { Router } from '@angular/router';
import { RelatorioServiceService } from '../../../services/relatorio-service.service';
import { TelegramServiceService } from '../../../services/telegram-service.service';
import { ButtonAction, ButtonActionClick, InputSelectOption } from 'pedrohfreitas-lib';

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

  pageActionsButtons: ButtonAction[] = [
    {
      action: 'imprimir',
      icon: 'imprimir',
      disable: false
    }
  ]
  
  tableHeader = ['ID','Cliente','Status','Data','Endereço']
  tableColuns = ['idPedido','nome','status','dataPedido','endereco']
  tableColunsSize = ['5','20','15','15','40']
  tableData : any [] = []
  tableActions : ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]

  constructor(
    private _pedidoService: PedidoService,
    private _localStorage: LocalStorageServiceService,
    private router: Router,
    private _relatorioService: RelatorioServiceService,
    private _telegramService : TelegramServiceService
  ){
    this._pedidoService.getDatasReferencias().then((response)=>{
      
      this.datasPedidos =  this._localStorage.converteListaItemParaOption(response.content,'','',true);
      if(this.datasPedidos.length > 0){
        this.setDataFilter(this.datasPedidos[0])
        this.dataReferencia = this.datasPedidos[0].value;
      }
            
    })
  }
  tableActionDataClick(action : ButtonActionClick){
    this.goToEditItem(action.data.id)
  }
  actionButtonPageClick(action: ButtonActionClick) {
    switch (action.action?.action) {
      case 'imprimir':
        this.gerarRelatorioDiario();
        break;
      default:
        break;
    }
}

  goToEditItem(idPedido : number){
    this.router.navigate(['/pedido/manual',{
      idPedido : idPedido
    }])
  }

  setDataFilter(dataSelect : InputSelectOption){
    this._pedidoService.getListaPedidos(dataSelect.value).then((response)=>{
      this.tableData = [];
      response.content.forEach(item =>{
        
        this.tableData.push({
          id : item.id,
          idPedido : item.idPedido,
          nome : item.nome,
          status: item.status,
          dataPedido : item.dataPedido,
          endereco : item.entrega ? item.enderecoDescricao : "Buscar"
        })
      })
      this.quantidadeItems = response.totalElements;
    })
  }

  gerarRelatorioDiario(){
    let nomeRelatorio = `RelatorioDiario_${this.dataReferencia.replaceAll('-','_')}`;
    this._telegramService.enviaTexto('1080114657',this.geraArquivoRelatorio())
    this._relatorioService.gerarRelatorio(nomeRelatorio,this.geraArquivoRelatorio()).then((response)=>{

    })
  }
  geraArquivoRelatorio(): string {
    let report = `Relatório Diario - ${this.dataReferencia} \r\n`
      + '========================\r\n';
      let valorTotal = 0;
      this.listaPedidos.reverse().forEach((value)=>{
        report = report + `Pedido: ${value.idPedido}\r\n`
        + `Valor: ${value.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}\r\n`
        + `Status: ${value.status}\r\n`
        + '========================\r\n';
        if(value.status != 'CANCELADO'){
          valorTotal = valorTotal + value.valor
        }
        
      })
      report = report + `Valor Total: ${valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}`;
    return report;
  }
}
