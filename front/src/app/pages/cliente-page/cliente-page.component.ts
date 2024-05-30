import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { ClientesService } from './clientes.service';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { PedidoModule } from '../pedido-page/pedido.module';
import { LocalStorageServiceService } from '../../services/local-storage-service.service';
import { ClienteDTO } from '../../shared/models/ClienteDTO';
import { PedidoDTO } from '../../shared/models/PedidoDTO';
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

  listaClientes : ClienteDTO[] = [];
  
  listaRuas : string[] = [];
  listaBairros : string[] = [];

  tableClienteDiarioCabecalho = ['Data / Hora:','Telefone','Nome','Endereço:'];
  tableClienteDiarioColunas = ['dataPedido','clienteTelefone','clienteNome','enderecoDescricao'];
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

  constructor(
    private _clientesService : ClientesService,
    private _localStorageService: LocalStorageServiceService,
    private _pedidoService : PedidoService
  ){
    this._localStorageService.listaRuas.forEach(value=>{
      this.listaRuas.push(value.nome);
    });
    this._localStorageService.listaBairros.forEach(value=>{
      this.listaBairros.push(value.nome);
    });
    this.getListaClientes();
    this._pedidoService.getDatasReferencias().then((response)=>{
      if(response.content.length > 0){
        this._pedidoService.getListaPedidos(response.content[0]).then((response)=>{
          this.tableClienteDiarioData = response.content;
        })
      }
    })
    
  }
  tableClientesActionDataClick(action : ButtonActionClick){
    this.getDadosClienteByTelefone(action.data.clienteTelefone);
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
      this.getListaClientes();
    })
  }
  excluirCliente(){
    // if(this.cliente.id != undefined){
    //   this._clientesService.excluirCliente(this.cliente.id).then((response)=>{
    //     this.getListaClientes();
    //   })
    // }
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
      console.log(response.content)
    })
  }
  getListaClientes(){
    this._clientesService.getListaClientes().then((response) => {
      this.listaClientes = response.content
      this.cliente = new ClienteDTO
    })
  }
}
