import { CommonModule } from '@angular/common';
import { Component, Input, SimpleChanges } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { PedidoService } from '../pedido.service';
import { ClientesService } from '../../cliente-page/clientes.service';
import { InputSelectOption } from '../../../componentes/input-select/input-select';
import { LocalStorageServiceService } from '../../../services/local-storage-service.service';
import { PedidoDTO, PedidoItemDTO } from '../../../shared/models/PedidoDTO';
import { EnderecoDTO } from '../../../shared/models/ClienteDTO';
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

  showModalEndereco = false;
  showModalItemPizza = false;
  showModalItemBebida = false;

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
            this.pedido.valorTaxa = Number(response.endereco.bairro?.valorTaxa)
            this.pedido.bairro = response.endereco.bairro?.nome
            this.pedido.rua = response.endereco.rua?.nome
            this.pedido.numero = response.endereco.numero
            this.pedido.complemento = response.endereco.complemento
            this.pedido.bloco = response.endereco.bloco
            this.pedido.apartamento == response.endereco.apartamento

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
      this.itemPedidoBebida = itemPedido;
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
        }
      }
    });
  }

  carregaDadosPedidoGeral() {
    if (this.pedido.id != undefined && this.pedido.id != 0) {
      this._pedidoService.getDadosPedido(this.pedido.id).then((response) => {
        if (response != undefined && response != null) {
          this.pedido = response;
          if (response.apartamento != undefined) {
            this.endereco.apartamento = JSON.parse(JSON.stringify(response.apartamento))
          }
          if (response.bairro != undefined) {
            this.endereco.bairro = JSON.parse(JSON.stringify(response.bairro))
          }

          if (response.bloco != undefined) {
            this.endereco.bloco = JSON.parse(JSON.stringify(response.bloco))
          }
          if (response.complemento != undefined) {
            this.endereco.complemento = JSON.parse(JSON.stringify(response.complemento))
          }
          if (response.enderecoDescricao != undefined) {
            this.endereco.enderecoDescricao = JSON.parse(JSON.stringify(response.enderecoDescricao))
          }
          if (response.numero != undefined) {
            this.endereco.numero = JSON.parse(JSON.stringify(response.numero))
          }
          if (response.rua != undefined) {
            this.endereco.rua = JSON.parse(JSON.stringify(response.rua))
          }
          console.log(this.pedido)
          if (response.itensPedido == null || response.itensPedido == undefined) {
            this.pedido.itensPedido = []
          }
          this.desabilitaCampoIDPedido = true;
        }
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
  imprimir() {
    if (this.pedido.itensPedido.length > 0) {
      this._pedidoService.salvarEImprimir(this.pedido).then((response) => {
      })
    } else {
      //TODO - Criar notificação para avisar que não tem item
    }
  }
  openModalEndereco() {
    console.log(this.endereco)
    this.showModalEndereco = true;
  }

  atualizaEndereco(enderecoModal: EnderecoDTO) {
    if (enderecoModal.enderecoDescricao != undefined) {
      this.pedido.enderecoDescricao = enderecoModal.enderecoDescricao;
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
}
