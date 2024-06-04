import { Component } from '@angular/core';
import { ClientesService } from './clientes.service';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { PedidoModule } from '../pedido-page/pedido.module';
import { ClienteDTO } from '../../shared/models/ClienteDTO';
import { PedidoService } from '../pedido-page/pedido.service';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-cliente-page',
  standalone: true,
  imports: [RouterOutlet,RouterLink, RouterLinkActive, PedidoModule, SharedModule],
  templateUrl: './cliente-page.component.html'
})
export class ClientePageComponent {


  telefoneFiltro = '';

  // listaClientes : ClienteDTO[] = [];
  
  listaRuas : string[] = [];
  listaBairros : string[] = [];

  tableClienteDiarioCabecalho = ['Data / Hora:','Telefone','Nome','Endereço:'];
  tableClienteDiarioColunas = ['dataPedido','telefone','nome','enderecoDescricao'];
  tableClienteDiarioColunasSize = ['10','15','15','50'];
  tableClienteDiarioData : any[] = [];
  tableClienteDiarioActionsButtons : ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]

  tablePedidoClienteCabecalho = ['Data / Hora:','Endereço:', 'Valor:'];
  tablePedidoClienteColunas = ['dataPedido','enderecoDescricao','valor'];
  tablePedidoClienteColunasSize = ['5','30','10'];
  tablePedidoClienteData : any[] = [];
  tablePedidoClienteActionsButtons = [];

  clientePedidoByTelefone : boolean = false;
  showModalEndereco: boolean = false;

  cliente : ClienteDTO = new ClienteDTO;

  dataRefencia : string = '1900-01-01'

  constructor(
    private _clientesService : ClientesService,
    private _pedidoService : PedidoService
  ){
    this._pedidoService.getDatasReferencias().then((response)=>{
      if(response.content.length > 0){
        this.dataRefencia = response.content[0];
        this.getListaPedidos();
      }
    })
    
  }
  getListaPedidos(){
    if(this.dataRefencia != '1900-01-01'){
      this._pedidoService.getListaPedidos(this.dataRefencia).then((response)=>{
        this.tableClienteDiarioData = response.content;
      })
    }
  }
  tableClientesActionDataClick(action : ButtonActionClick){
    this.getDadosClienteByTelefone(action.data.telefone);
  }
  tableClientePedidosActionDataClick(action : ButtonActionClick){
  }

  handleTelefone(telefoneSelecionado: string) {
    this.telefoneFiltro = telefoneSelecionado;
  }
  adicionarCliente(){

    if(this.cliente.nome == '' && this.cliente.nome.length < 3){
      alert('Nome Obrigatorio')
    }

    if(this.cliente.telefone == '' && this.cliente.telefone.length < 8){
      alert('Telefone Obrigatorio')
    }

    if(this.cliente.nome != '' && this.cliente.telefone != '')
    this._clientesService.adicionaCliente(this.cliente).then((data)=>{
      this.cliente =  JSON.parse(JSON.stringify(data));
      this.getListaPedidos();
      this.cliente = new ClienteDTO
    })
  }
  
  getDadosClienteByTelefone(telefone : string) {
      this._clientesService.getDadosClienteByTelefone(telefone).then((response) => {
        if (response != null) {
          this.cliente = response;
          this.getListaPedidosByClientes(telefone);
          this.clientePedidoByTelefone = true;
        }
        this.telefoneFiltro = ''
      });

    }
  getListaPedidosByClientes(telefone : string){
    this._clientesService.getListaPedidosByClientes(telefone).then((response) => {
      this.tablePedidoClienteData = []
      response.content.forEach(value=>{
        const dataFormatada = new Date(value.dataPedido).toLocaleString('pt-BR', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: 'numeric',
          minute: 'numeric',
        }).replace(',','');
        this.tablePedidoClienteData.push({
          dataPedido : dataFormatada,
          enderecoDescricao : value.enderecoDescricao,
          valor : value.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
        })
      })
    })
  }
}
