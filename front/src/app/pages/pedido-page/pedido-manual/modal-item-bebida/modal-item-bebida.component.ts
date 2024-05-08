import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { InputSelectOption } from '../../../../componentes/input-select/input-select';
import { LocalStorageServiceService } from '../../../../services/local-storage-service.service';
import { PedidoItemDTO, PedidoItemPizzaDTO } from '../../../../shared/models/PedidoDTO';
import { BebidaDTO } from '../../../../shared/models/BebidaDTO';
import { PedidoService } from '../../pedido.service';

@Component({
  selector: 'app-modal-item-bebida',
  templateUrl: './modal-item-bebida.component.html',
  styleUrl: './modal-item-bebida.component.scss'
})
export class ModalItemBebidaComponent {
  @Input() showModal: boolean = false;
  @Input() idPedido: number = 0;
  @Input() itemPedidoBebida: PedidoItemDTO = {
    id: 0,
    idPedido: this.idPedido,
    descricao: '',
    tipo: 'Bebida',
    valor: 0,
    tamanho: '2L',
    quantidade: 1,
    nome: 'COCA-COLA'
  }

  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() dadosItemChange: EventEmitter<PedidoItemDTO> = new EventEmitter<PedidoItemDTO>();

  optionDefaultBebida?: InputSelectOption;
  optionDefaultTamanho?: InputSelectOption;

  listaBebidas: BebidaDTO[] = []
  listaBebidasOptions: InputSelectOption[] = []
  listaTamanhoItem: InputSelectOption[] = [
    {
      option: '2L',
      value: '2L',
      defaultValue: true
    },
    {
      option: '1,5L',
      value: '1,5L'
    },
    {
      option: '1L',
      value: '1L',
    },
    {
      option: '600ML',
      value: '600ML',
    },
    {
      option: 'Lata',
      value: 'Lata',
    },

  ];
  constructor(
    private _localStorageService: LocalStorageServiceService,
    private _pedidoService: PedidoService
  ) {
    this.listaBebidas = this._localStorageService.listaBebidas;
    this.listaBebidasOptions = this._localStorageService.converteListaItemParaOption(this.listaBebidas, 'nome', 'nome', false);

  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['idPedido']) {
      this.idPedido = changes['idPedido'].currentValue;
    }
    if (changes['itemPedidoBebida']) {
      this.itemPedidoBebida = changes['itemPedidoBebida'].currentValue
      console.log(this.itemPedidoBebida)
      this.optionDefaultBebida = {
        option : this.itemPedidoBebida.nome,
        value: this.itemPedidoBebida.nome
      }
      if(this.optionDefaultBebida != undefined){
        this.setBebida(this.optionDefaultBebida);
      }
      this.optionDefaultTamanho = {
        option : this.itemPedidoBebida.tamanho,
        value : this.itemPedidoBebida.tamanho
      }
      if(this.optionDefaultTamanho != undefined){
        this.setTamanhoItem(this.optionDefaultTamanho);
      }
      console.log(this.itemPedidoBebida);
      
    }
  }

  setBebida(inputSelecionado: InputSelectOption) {
    this.itemPedidoBebida.nome = inputSelecionado.option
    let bebidaDTO: BebidaDTO | undefined = this.listaBebidas.find(item => item.nome == this.itemPedidoBebida.nome)
    if (bebidaDTO != undefined) {
      if (bebidaDTO.tamanhoValor != undefined) {
        this.listaTamanhoItem = this._localStorageService.converteListaItemParaOption(bebidaDTO.tamanhoValor, 'tamanho', 'tamanho', false)
      }

    }
    this.calculaValorItem();
  }

  setQuantidade(item: string) {
    this.itemPedidoBebida.quantidade = Number(item);
    this.calculaValorItem();
  }

  setTamanhoItem(inputSelecionado: InputSelectOption) {
    this.itemPedidoBebida.tamanho = inputSelecionado.option
    this.calculaValorItem();
  }
  calculaValorItem() {
    let bebida: BebidaDTO | undefined = this.listaBebidas.find(item => item.nome == this.itemPedidoBebida.nome)
    if (bebida != undefined) {
      let valor = bebida.tamanhoValor?.find(item => item.tamanho == this.itemPedidoBebida.tamanho)
      if (valor != undefined) {
        if (valor.valor != undefined) {
          let valorFinal = Number(this.itemPedidoBebida.quantidade) * valor.valor
          this.itemPedidoBebida.valor = valorFinal;
        }
      }
    }
    
  }

  onCloseModal() {
    this.showModal = false;
    this.showModalChange.emit(this.showModal);
    this.dadosItemChange.emit(this.itemPedidoBebida);
    this.itemPedidoBebida = {
      id: 0,
      idPedido: this.idPedido,
      descricao: '',
      tipo: 'Bebida',
      valor: 13,
      tamanho: '2L',
      quantidade: 1,
      nome: 'COCA-COLA'
    }
  }
  openModal() {
    this.showModal = true;
  }

  save() {
    this.itemPedidoBebida.descricao = this.itemPedidoBebida.quantidade + ' x ' + this.itemPedidoBebida.nome + ' - ' + this.itemPedidoBebida.tamanho + ' = ' + this.itemPedidoBebida.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    this.itemPedidoBebida.tipo = 'Bebida'
    this._pedidoService.adicionaItemPedido(Number(this.idPedido), this.itemPedidoBebida).then((response) => {
      this.onCloseModal();
    });
  }
}
