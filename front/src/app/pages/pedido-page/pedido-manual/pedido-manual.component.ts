import { CommonModule } from '@angular/common';
import { Component, Input, SimpleChanges } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { PedidoService } from '../pedido.service';
import { ClientesService } from '../../cliente-page/clientes.service';
import { InputSelectOption } from '../../../componentes/input-select/input-select';
import { LocalStorageServiceService } from '../../../services/local-storage-service.service';
import { PedidoDTO, PedidoItemDTO } from '../../../shared/models/PedidoDTO';
import { BairroDTO, EnderecoDTO } from '../../../shared/models/ClienteDTO';
import { PedidoModule } from '../pedido.module';
import { ActivatedRoute } from '@angular/router';
import { PizzaSaborDTO } from '../../../shared/models/PizzaDTO';

@Component({
  selector: 'app-pedido-manual',
  standalone: true,
  imports: [CommonModule, SharedModule, PedidoModule],
  templateUrl: './pedido-manual.component.html',
  styleUrl: './pedido-manual.component.scss'
})
export class PedidoManualComponent {
  pedido: PedidoDTO = new PedidoDTO;
  endereco: EnderecoDTO = new EnderecoDTO;
  itemPedidoPizza: PedidoItemDTO = new PedidoItemDTO
  itemPedidoBebida: PedidoItemDTO = new PedidoItemDTO;
  tipoItemModal: string = 'Pizza';

  idCliente = 0;

  previa = '';

  showModalEndereco = false;
  showModalItemPizza = false;
  showModalItemBebida = false;
  showModalPrevia = false;

  showSalvarCliente = false;

  desabilitaCampoIDPedido = false;
  enderecoPedido = '';

  listaSelectTipo: InputSelectOption[] = [
    {
      option: 'Pizza',
      value: 'Pizza',
      defaultValue: true
    },
    {
      option: 'Bebida',
      value: 'Bebida'
    },
  ]
  constructor(
    private _pedidoService: PedidoService,
    private _clientesService: ClientesService,
    private _localStorageService: LocalStorageServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    this._localStorageService.listaRuas;
    if (this.activatedRoute.snapshot.paramMap.get("idPedido") != undefined) {
      this.pedido.id = Number(this.activatedRoute.snapshot.paramMap.get("idPedido"));
    }

    this.carregaDadosPedidoGeral();
  }

  cancelaPedido() {
    if (this.pedido.idPedido != undefined) {
      this._pedidoService.cancelaPedido(this.pedido.idPedido).then((response) => { })
    }
  }

  changeEntrega(){
    if(!this.pedido.entrega){
      this.pedido.valorTaxa = 0;
      this.calculaValorPedido();
    }
    if(this.pedido.entrega){
      this.getDadosClienteByTelefone();
    }
  }

  getDadosClienteByTelefone() {
    if (this.pedido.clienteTelefone != undefined && this.pedido.clienteTelefone != '') {
      this._clientesService.getDadosClienteByTelefone(this.pedido.clienteTelefone).then((response) => {
        if (response != null) {
          if (response.id != undefined) {
            this.idCliente = response.id
          }
          if (response.nome != undefined) {
            this.pedido.clienteNome = response.nome;
          }
          if (response.endereco != undefined && response.endereco != null) {
            this.endereco = response.endereco;
            if (response.endereco.enderecoDescricao != undefined) {
              this.pedido.enderecoDescricao = response.endereco.enderecoDescricao
            }
            this.pedido.valorTaxa = response.endereco.bairro.valorTaxa;
            this.pedido.bairro = response.endereco.bairro.nome;
            this.pedido.rua = response.endereco.rua.nome;
            this.pedido.numero = response.endereco.numero;
            this.pedido.complemento = response.endereco.complemento;
            this.pedido.bloco = response.endereco.bloco;
            this.pedido.apartamento == response.endereco.apartamento;

          }
          this.gravarPedido();
        } else {
          this.showSalvarCliente = true;
        }
        this.calculaValorPedido();
      });
    }
  }
  adicionarCliente() {
    if (this.pedido.clienteNome == undefined || this.pedido.clienteNome == ''
      || this.pedido.clienteNome.length < 3) {
      alert('Nome Obrigatorio')
    }

    if (this.pedido.clienteTelefone == undefined || this.pedido.clienteTelefone == ''
      || this.pedido.clienteTelefone.length < 8) {
      alert('Telefone Obrigatorio')
    }

    if (this.pedido.clienteNome != undefined && this.pedido.clienteTelefone != undefined
      && this.pedido.clienteNome != '' && this.pedido.clienteTelefone != '' && this.pedido.clienteNome.length > 3 && this.pedido.clienteTelefone.length > 8)
      this._clientesService.adicionaCliente({
        nome: this.pedido.clienteNome,
        telefone: this.pedido.clienteTelefone
      }).then((response) => {
        this.gravarPedido();
      })
  }

  handleTelefone(telefoneSelecionado: InputSelectOption) {
    this.pedido.clienteTelefone = telefoneSelecionado.option;
    if (telefoneSelecionado?.value != undefined && telefoneSelecionado.option != '') {
      this.getDadosClienteByTelefone();
    }
  }

  setTipoItem(tipo: string) {
    this.tipoItemModal = tipo
  }

  adicionaItemPedido() {
    if (this.pedido.itensPedido == null) {
      this.pedido.itensPedido = []
    }
    const novoItem: PedidoItemDTO = new PedidoItemDTO
    this.pedido.itensPedido.push(novoItem);
    this.itemPedidoBebida = JSON.parse(JSON.stringify(novoItem));
    this.itemPedidoPizza = JSON.parse(JSON.stringify(novoItem));
  }

  removerItemPedido(idItem: number) {
    this._pedidoService.removeItemPedido(this.pedido.id, idItem).then((response) => {
      this.carregaDadosPedidoGeral();
    })
  }

  openModalItem(itemPedido: PedidoItemDTO, tipo: string) {
    if (itemPedido.nome != '') {
      this.tipoItemModal = tipo
    }

    if (this.tipoItemModal == 'Pizza') {
      this.itemPedidoPizza = itemPedido;
      this.showModalItemPizza = true;
    }
    if (this.tipoItemModal == 'Bebida') {
      if(this.itemPedidoBebida.nome == ''){
        this.itemPedidoBebida = {
          nome : 'COCA-COLA',
          quantidade : 1,
          tipo : 'Bebida',
          valor : 13,
          tamanho : '2l',
          descricao : '',
          id : 0,
          idPedido : this.pedido.id,
          pedidoItemPizza : ''
        }
      } else {
        this.itemPedidoBebida = itemPedido;
      }
      
      this.showModalItemBebida = true;
    }
    this.tipoItemModal = 'Pizza'
  }

  novoPedido() {
    this._pedidoService.criarPedido().then((response) => {
      if (response != undefined && response != null) {
        this.pedido = response;
        this.desabilitaCampoIDPedido = true;
        if (this.pedido.clienteTelefone != undefined && this.pedido.clienteNome == '') {
          this.getDadosClienteByTelefone();
        } else {
          this.calculaValorPedido();
        }
        this.preencheEnderecoDTO(response);
      }
      this.carregaDadosPedidoGeral();
    });
    
  }

  preencheEnderecoDTO(dados : PedidoDTO){
    if (dados.apartamento != undefined) {
      this.endereco.apartamento = JSON.parse(JSON.stringify(dados.apartamento))
    }
    if (dados.bairro != undefined) {
      this.endereco.bairro = {
        id: 0,
        nome : JSON.parse(JSON.stringify(dados.bairro)),
        valorTaxa : this.pedido.valorTaxa != undefined ? this.pedido.valorTaxa : 0
      }
    }

    if (dados.bloco != undefined) {
      this.endereco.bloco = JSON.parse(JSON.stringify(dados.bloco))
    }
    if (dados.complemento != undefined) {
      this.endereco.complemento = JSON.parse(JSON.stringify(dados.complemento))
    }
    if (dados.enderecoDescricao != undefined) {
      this.endereco.enderecoDescricao = JSON.parse(JSON.stringify(dados.enderecoDescricao))
    }
    if (dados.numero != undefined) {
      this.endereco.numero = JSON.parse(JSON.stringify(dados.numero))
    }
    if (dados.rua != undefined) {
      this.endereco.rua = {
        id: 0,
        nome: JSON.parse(JSON.stringify(dados.rua))
      }
       
    }
      
  }

  carregaDadosPedidoGeral() {
    if (this.pedido.id != undefined && this.pedido.id != 0) {
      this._pedidoService.getDadosPedido(this.pedido.id).then((response) => {
        if (response != undefined && response != null) {
          this.pedido = response;
          this.preencheEnderecoDTO(response)
          if (response.itensPedido == null || response.itensPedido == undefined) {
            this.pedido.itensPedido = []
          }
          this.desabilitaCampoIDPedido = true;
        }
        this.calculaValorPedido();
      });
    }
  }

  gravarPedido() {
    if (this.pedido.id != undefined) {
      this._pedidoService.atualizaDadosPedido(this.pedido.id, this.pedido).then((response) => {
        this.carregaDadosPedidoGeral();
      })
    }
  }
  geraArquivoRelatorio(): string{
    const itensPedidoPizza : PedidoItemDTO[] = [];
    const itensPedidoBebida : PedidoItemDTO[] = [];
    
    let pedidoReport =`      Pedido: ${this.pedido.idPedido} \r\n`
    + '===========================\r\n'
    + `Data/Hora : ${new Date(this.pedido.dataPedido).toLocaleString()}\r\n`
    + '===========================\r\n'
    +`Nome: ${this.pedido.clienteNome}\r\n`
    + '===========================\r\n'
    +`Telefone: ${this.pedido.clienteTelefone}\r\n`
    + '===========================\r\n'
    +`End: ${this.pedido.enderecoDescricao}\r\n`
    + '===========================\r\n';
    this.pedido.itensPedido.forEach((value)=>{
      if(value.tipo == 'Pizza'){
        itensPedidoPizza.push(value)
      }
      if(value.tipo == 'Bebida'){
        itensPedidoBebida.push(value)
      }
      
      
    });

    if(itensPedidoPizza.length > 0){
      pedidoReport = pedidoReport + '==========Pizzas============\r\n'
      itensPedidoPizza.forEach((value)=>{
        pedidoReport = pedidoReport + value.descricao +'\r\n'
        + '\r\n'
        + '\r\n'
        + '==========================\r\n';
      })
    }
    if(itensPedidoBebida.length > 0){
      pedidoReport = pedidoReport + '==========Bebidas===========\r\n'
      itensPedidoBebida.forEach((value)=>{
        pedidoReport = pedidoReport + value.descricao +'\r\n'
        + '\r\n'
        + '\r\n'
        + '==========================\r\n';
      })
    }
     
    pedidoReport = `${pedidoReport}Taxa de Entrega: ${this.pedido.valorTaxa.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}\r\n`
    + '===========================\r\n'
    +`Valor Pedido: ${this.pedido.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}\r\n`
    + '===========================\r\n';
    if(this.pedido.observacao != null){
      pedidoReport = `${pedidoReport}Observação: ${this.pedido.observacao}`;
    }
    
    return pedidoReport;
  }
  imprimir() {
    if (this.pedido.itensPedido.length > 0) {
      
      this.pedido.pedidoRelatorio = this.geraArquivoRelatorio();
      
      this._pedidoService.salvarEImprimir(this.pedido).then((response) => {
      })
    } else {
      //TODO - Criar notificação para avisar que não tem item
    }
  }
  openModalEndereco() {
    this.showModalEndereco = true;
  }

  atualizaEndereco(enderecoModal: EnderecoDTO) {
    if (enderecoModal.enderecoDescricao != undefined) {
      this.pedido.enderecoDescricao = enderecoModal.enderecoDescricao;
      this.pedido.rua = enderecoModal.rua.nome
      this.pedido.bairro = enderecoModal.bairro.nome
      this.pedido.numero = enderecoModal.numero
      this.pedido.bloco = enderecoModal.bloco
      this.pedido.apartamento = enderecoModal.apartamento
      this.pedido.complemento = enderecoModal.complemento
    }
    if (enderecoModal.bairro != undefined) {
      this.pedido.valorTaxa = enderecoModal.bairro.valorTaxa;
    }
    if (this.idCliente != 0) {
      this._clientesService.saveDadosEnderecoCliente(this.idCliente, enderecoModal).then((response) => {

      });
    }
    this.calculaValorPedido();
    this.gravarPedido();
  }

  calculaValorPedido() {
    let valor: number = 0
    if (this.pedido.valorTaxa != undefined && this.pedido.valorTaxa != 0) {
      valor = valor + this.pedido.valorTaxa
    }
    if (this.pedido.itensPedido != undefined && this.pedido.itensPedido.length > 0) {
      this.pedido.itensPedido.forEach((item) => {
        if (item.valor != undefined) {
          valor = valor + item.valor
        }
      })
    }
    this.pedido.valor = valor
  }
  mostrarEmNovaAba(){
    this.previa = this.geraArquivoRelatorio();
    this.showModalPrevia = true
  }
}
