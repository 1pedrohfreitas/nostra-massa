import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { PedidoService } from '../pedido.service';
import { ClientesService } from '../../cliente-page/clientes.service';
import { PedidoDTO, PedidoItemDTO } from '../../../shared/models/PedidoDTO';
import { PedidoModule } from '../pedido.module';
import { ActivatedRoute } from '@angular/router';
import { ClienteDTO } from '../../../shared/models/ClienteDTO';
import { ButtonAction, ButtonActionClick, InputSelectOption } from 'pedrohfreitas-lib';
import { AutoCompleteServiceService } from '../../../services/auto-complete-service.service';

@Component({
  selector: 'app-pedido-manual',
  standalone: true,
  imports: [CommonModule, SharedModule, PedidoModule],
  templateUrl: './pedido-manual.component.html'
})
export class PedidoManualComponent {
  pageTitulo = 'Pedido: '
  pageActionsButtons: ButtonAction[] = [
    {
      action: 'imprimir',
      icon: 'imprimir',
    },
    {
      action: 'cancelar',
      icon: 'x',
    },
    {
      action: 'previa',
      icon: 'imprimir-previa',
    },
    {
      action: 'novo',
      icon: 'novo-pedido',
    },
  ]

  pedido: PedidoDTO = new PedidoDTO;

  telefone : string = '';
  nome : string = '';
  idCliente: number = 0;
  idItem: number = 0;
  itemPedidoPizza: PedidoItemDTO = new PedidoItemDTO
  itemPedidoBebida: PedidoItemDTO = new PedidoItemDTO;

  previa = '';

  showModalEndereco = false;
  showModalItemPizza = false;
  showModalItemBebida = false;
  showModalPrevia = false;

  showNomeCliente = false;
  showSalvarCliente = false;
  showBotaoBuscarCliente = false;
  showBotaoRemoveCliente = false;
  showBotaoEnderecoCliente = false;
  showItensPedido = false;

  enderecoPedido = '';

  optionDefaultTipoPagamento?: InputSelectOption;

  listaTelefones : string[] = []
  listaTipoPagamentoOptions: InputSelectOption[] = [
    {
      option: 'GERAL',
      value: 'GERAL',
      defaultValue: true
    },
    {
      option: 'DINHEIRO',
      value: 'DINHEIRO'
    },
    {
      option: 'CARTAO',
      value: 'CARTAO',
    },
    {
      option: 'PIX',
      value: 'PIX'
    },
  ]
  constructor(
    private _pedidoService: PedidoService,
    private _clientesService: ClientesService,
    private activatedRoute: ActivatedRoute,
    private _autoCompleteService : AutoCompleteServiceService
  ) {
    if (this.activatedRoute.snapshot.paramMap.get("idPedido") != undefined) {
      this.pedido.id = Number(this.activatedRoute.snapshot.paramMap.get("idPedido"));
    }

    this.carregaDadosPedidoGeral();
  }

  actionButtonPageClick(action: ButtonActionClick) {
      switch (action.action?.action) {
        case 'imprimir':
          this.imprimir();
          break;
        case 'cancelar':
          this.cancelaPedido();
          break;
        case 'novo':
          this.novoPedido();
          break;
        case 'previa':
          this.mostrarEmNovaAba();
          break;
  
        default:
          break;
      }
  }

  cancelaPedido() {
    if (this.pedido.idPedido != undefined) {
      this._pedidoService.cancelaPedido(this.pedido.idPedido).then((response) => { })
    }
  }

  changeEntrega() {
    if (!this.pedido.entrega) {
      this.pedido.taxaEntrega = 0;
      this.calculaValorPedido();
    }
    if (this.pedido.entrega) {
      this.getDadosClienteByTelefone();
    }
  }

  handleTelefone(value : string){
    this.telefone = value
    this.listaTelefones = []
    if(value!= ''){
      this._autoCompleteService.autoCompleteTelefone(value).then((response)=>{
        this.listaTelefones = response.content
      });
    }
  }

  getDadosClienteByTelefone() {
    this.showNomeCliente = true;
    if (this.telefone != undefined && this.telefone != '') {
      this._clientesService.getDadosClienteByTelefone(this.telefone).then((response) => {
        if (response != null) {
          console.log(response)
          this.nome = response.nome;
          this.pedido.nome = response.nome;
          this.pedido.enderecoDescricao = response.enderecoDescricao
          this.pedido.telefone = this.telefone
          this.pedido.rua = response.rua;
          this.pedido.bairro = response.bairro;
          this.pedido.numero = response.numero;
          this.pedido.complemento = response.complemento;
          this.pedido.bloco = response.bloco;
          this.pedido.apartamento = response.apartamento;
          
          
        } else {
          this.showSalvarCliente = true;
        }
      }
      ).then(()=>{
        if(this.pedido.entrega){
          if(this.pedido.bairro != undefined){
            this._pedidoService.getTaxaDeEntrega(this.pedido.bairro).then((responseTx)=>{
              console.log(responseTx)
              this.pedido.taxaEntrega = responseTx
            }).then(()=>{
              console.log('123')
              this.calculaValorPedido();
            })
          }
        } else{
          this.calculaValorPedido();
        }
        
      });
    }
  }

  adicionarCliente() {
    if (this.pedido.nome == undefined || this.pedido.nome == ''
      || this.pedido.nome.length < 3) {
      alert('Nome Obrigatorio')
    } else if (this.pedido.telefone == undefined || this.pedido.telefone == ''
      || this.pedido.telefone.length < 8) {
      alert('Telefone Obrigatorio')
    } else {
      let cliente = new ClienteDTO
      cliente.nome = this.pedido.nome;
      cliente.telefone = this.pedido.telefone;

      this._clientesService.adicionaCliente(cliente).then((response) => {
        this.idCliente = response.id
        this.gravarPedido();
      })
    }
  }
  removerCliente() {
    this.nome = '';
    this.telefone = '';
    this.pedido.nome = '';
    this.pedido.telefone = '';
    this.pedido.taxaEntrega = 0;
    this.pedido.apartamento = '';
    this.pedido.bloco = '';
    this.pedido.complemento = '';
    this.pedido.rua = '';
    this.pedido.bairro = '';
    this.pedido.numero = '';
    this.pedido.enderecoDescricao = '';
    this.validaMostrarCampos();
  }

  setTipoPagamento(tipoDePagamento: InputSelectOption) {
    this.pedido.tipoPagamento = tipoDePagamento.value
  }

  adicionaItemPedido(tipo: 'Pizza' | 'Bebida') {
    if (this.pedido.itensPedido == null) {
      this.pedido.itensPedido = []
    }
    const novoItem: PedidoItemDTO = new PedidoItemDTO
    novoItem.tipo = tipo
    this.itemPedidoBebida = JSON.parse(JSON.stringify(novoItem));
    this.itemPedidoPizza = JSON.parse(JSON.stringify(novoItem));
    this.openModalItem(novoItem);
  }

  removerItemPedido(idItem: number) {
    this._pedidoService.removeItemPedido(this.pedido.id, idItem).then((response) => {
      this.carregaDadosPedidoGeral();
    })
  }

  openModalItem(itemPedido: PedidoItemDTO) {

    this.idItem = itemPedido.id

    if (itemPedido.tipo == 'Pizza') {
      this.itemPedidoPizza = JSON.parse(JSON.stringify(itemPedido));
      this.showModalItemPizza = true;
    }
    if (itemPedido.tipo == 'Bebida') {
      if (this.itemPedidoBebida.nome == '') {
        this.itemPedidoBebida = {
          nome: 'COCA-COLA',
          quantidade: 1,
          tipo: 'Bebida',
          valor: 13,
          tamanho: '2l',
          descricao: '',
          id: 0,
          idPedido: this.pedido.id,
          pedidoItemPizza: ''
        }
      } else {
        this.itemPedidoBebida = itemPedido;
      }

      this.showModalItemBebida = true;
    }
  }

  novoPedido() {
    this.idCliente = 0;
    this._pedidoService.criarPedido().then((response) => {
      if (response != undefined && response != null) {
        this.preencheDadosPedido(response);
      }
    }).then(()=> this.validaMostrarCampos());
  }

  carregaDadosPedidoGeral() {
    if (this.pedido.id != undefined && this.pedido.id != 0) {
      this._pedidoService.getDadosPedido(this.pedido.id).then((response) => {
        this.preencheDadosPedido(response);
      }).then(()=> this.validaMostrarCampos());
    }
  }

  preencheDadosPedido(response : PedidoDTO){
    if (response != undefined && response != null) {
      this.pedido = JSON.parse(JSON.stringify(response));
      if(this.pedido.telefone != undefined && this.pedido.telefone != null){
        this.telefone = this.pedido.telefone
      }
      if(this.pedido.nome != undefined && this.pedido.nome != null){
        this.nome = this.pedido.nome
      }
      this.pageTitulo = 'Pedido: ' + (this.pedido.idPedido != null ? this.pedido.idPedido : '');
      if (response.itensPedido == null || response.itensPedido == undefined) {
        this.pedido.itensPedido = []
      }
      if (this.pedido.tipoPagamento != null && this.pedido.tipoPagamento != undefined) {
        this.optionDefaultTipoPagamento = {
          option: response.tipoPagamento,
          value: response.tipoPagamento
        }
      } else {
        this.pedido.tipoPagamento = 'GERAL'
      }
    }
    this.validaMostrarCampos();
    this.calculaValorPedido();
  }

  gravarPedido() {
    if (this.pedido.id != undefined) {
      this._pedidoService.atualizaDadosPedido(this.pedido.id, this.pedido).then((response) => {
      })
    }
  }
  geraArquivoRelatorio(): string {
    const dataFormatada = new Date(this.pedido.dataPedido).toLocaleString('pt-BR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: 'numeric',
      minute: 'numeric',
    }).replace(',', '');

    const itensPedidoPizza: PedidoItemDTO[] = [];
    const itensPedidoBebida: PedidoItemDTO[] = [];

    let pedidoReport = `      Pedido: ${this.pedido.idPedido} \r\n`
      + '========================\r\n'
      + `Data/Hora: ${dataFormatada}\r\n`
      + `Pagamento: ${this.pedido.tipoPagamento}\r\n`
      + '========================\r\n'
      + `Nome: ${this.pedido.nome}\r\n`
      + `Telefone: ${this.pedido.telefone}\r\n`
      + '========================\r\n';
    if (this.pedido.entrega) {
      pedidoReport = pedidoReport + `End: ${this.pedido.rua}\r\n`
        + `N: ${this.pedido.numero}\r\n`
        + `Bairro: ${this.pedido.bairro}\r\n`;
    }
    if (!this.pedido.entrega) {
      pedidoReport = pedidoReport + `BUSCAR\r\n`;
    }

    if (this.pedido.bloco != undefined && this.pedido.bloco != '') {
      pedidoReport = pedidoReport + 'Bloco: ' + this.pedido.bloco + '\r\n';
    }

    if (this.pedido.apartamento != undefined && this.pedido.apartamento != '') {
      pedidoReport = pedidoReport + 'Apartamento: ' + this.pedido.apartamento + '\r\n';
    }
    if (this.pedido.complemento != undefined && this.pedido.complemento != '') {
      pedidoReport = pedidoReport + 'Complemento: ' + this.pedido.complemento + '\r\n';
    }
    pedidoReport = pedidoReport + '========================\r\n';

    this.pedido.itensPedido.forEach((value) => {
      if (value.tipo == 'Pizza') {
        itensPedidoPizza.push(value)
      }
      if (value.tipo == 'Bebida') {
        itensPedidoBebida.push(value)
      }


    });

    if (itensPedidoPizza.length > 0) {
      pedidoReport = pedidoReport + '==========Pizzas=========\r\n'
      itensPedidoPizza.forEach((value, index) => {
        pedidoReport = pedidoReport + '======____Pizza:' + (index + 1) + '____=====\r\n' + value.descricao + '\r\n'
          + '\r\n'
          + '====================\r\n';
      })
    }
    if (itensPedidoBebida.length > 0) {
      pedidoReport = pedidoReport + '==========Bebidas========\r\n'
      itensPedidoBebida.forEach((value) => {
        pedidoReport = pedidoReport + value.descricao + '\r\n'
          + '\r\n'
          + '\r\n'
          + '====================\r\n';
      })
    }

    pedidoReport = `${pedidoReport}Taxa de Entrega: ${this.pedido.taxaEntrega.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}\r\n`
      + '========================\r\n'
      + `Valor Pedido: ${this.pedido.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}\r\n`
      + '========================\r\n';
    if (this.pedido.observacao != null) {
      pedidoReport = `${pedidoReport}Observação: ${this.pedido.observacao}`;
    }

    return pedidoReport;
  }
  imprimir() {
    if (this.pedido.itensPedido.length > 0) {

      this.pedido.pedidoRelatorio = this.geraArquivoRelatorio();

      this._pedidoService.salvarEImprimir(this.pedido).then((response) => {
        this.carregaDadosPedidoGeral();
      })
    }
  }
  openModalEndereco() {
    this.showModalEndereco = true;
  }

  calculaValorPedido() {
    let valor: number = 0
    if (this.pedido.taxaEntrega != undefined && this.pedido.taxaEntrega != 0) {
      valor = valor + this.pedido.taxaEntrega
    }
    if (this.pedido.itensPedido != undefined && this.pedido.itensPedido.length > 0) {
      this.pedido.itensPedido.forEach((item) => {
        if (item.valor != undefined) {
          valor = valor + item.valor
        }
      })
    }
    this.pedido.valor = valor
    this.gravarPedido();
  }

  mostrarEmNovaAba() {
    this.previa = this.geraArquivoRelatorio();
    this.showModalPrevia = true
  }

  validaMostrarCampos(){
    if(this.pedido.nome != ''){
      this.showNomeCliente = true;
    } else {
      this.showNomeCliente = false;
    }
    if(this.pedido.telefone != '' && this.pedido.nome !=''){
      this.showBotaoBuscarCliente = false;
      this.showBotaoRemoveCliente = true;
      this.showItensPedido = true;
      if(this.pedido.entrega){
        this.showBotaoEnderecoCliente = true;
      } else{
        this.showBotaoEnderecoCliente = false;
      }
    } else {
      this.showBotaoBuscarCliente = true;
      this.showBotaoRemoveCliente = false;
      this.showBotaoEnderecoCliente = false;
      this.showItensPedido = false;
    }
    
    
    
    
  }
}
