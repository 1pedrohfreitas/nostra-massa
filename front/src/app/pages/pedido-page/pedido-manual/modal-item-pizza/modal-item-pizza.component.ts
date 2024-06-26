import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { PizzaAcrescimoDTO, PizzaSaborDTO, PizzaSaborIngredienteDTO } from '../../../../shared/models/PizzaDTO';
import { PedidoItemDTO, PedidoItemPizzaDTO } from '../../../../shared/models/PedidoDTO';
import { PedidoService } from '../../pedido.service';
import { LocalStorageServiceService } from '../../../../services/local-storage-service.service';
import { InputSelectOption } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-modal-item-pizza',
  templateUrl: './modal-item-pizza.component.html',
  styleUrl: './modal-item-pizza.component.scss'
})
export class ModalItemPizzaComponent {

  @Input() showModal: boolean = false;
  showConteudo: boolean = false;
  itemPedidoPizza: PedidoItemDTO = new PedidoItemDTO;
  @Input() idItem: number = 0;
  @Input() idPedido: number = 0;
  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() dadosItemChange: EventEmitter<PedidoItemDTO> = new EventEmitter<PedidoItemDTO>();

  pizzaSabor1: PizzaSaborDTO = new PizzaSaborDTO
  pizzaSabor2: PizzaSaborDTO = new PizzaSaborDTO

  sabores: InputSelectOption[] = [];
  saboresPizza: PizzaSaborDTO[] = []
  acrescimosPizza1: Map<string, PizzaAcrescimoDTO> = new Map();
  acrescimosPizza2: Map<string, PizzaAcrescimoDTO> = new Map();
  ingredientesPizza1: Map<string, PizzaAcrescimoDTO> = new Map();
  ingredientesPizza2: Map<string, PizzaAcrescimoDTO> = new Map();

  quantidadeSabores: string = '1'
  sabor1SelectDefault: InputSelectOption = new InputSelectOption;
  sabor2SelectDefault: InputSelectOption = new InputSelectOption;
  optionDefaultQuantidadeSabores: InputSelectOption = new InputSelectOption
  optionDefaultTamanho: InputSelectOption = new InputSelectOption

  listaSelectQuantidadeSabores: InputSelectOption[] = [
    {
      value: '1',
      option: '1',
      defaultValue: true
    },
    {
      value: '2',
      option: '2'
    },
  ]
  listaSelectTamanhoPizza: InputSelectOption[] = [
    {
      value: 'M',
      option: 'Média'
    },
    {
      value: 'G',
      option: 'Grande'
    },
    {
      value: 'GG',
      option: 'Gigante',
      defaultValue: true
    }
  ]
  constructor(
    private _pedidoService: PedidoService,
    private _localStorageService: LocalStorageServiceService
  ) {
    this.preencheListas()
  }
  preencheListas() {
    this.sabores = this._localStorageService.converteListaItemParaOption(
      this._localStorageService.listaPizzas, 'nome', 'id', false
    )

    this.saboresPizza = this._localStorageService.listaPizzas
    if (this.itemPedidoPizza.pedidoItemPizza == undefined) {
      this.itemPedidoPizza.pedidoItemPizza = '';
      this.saboresPizza.push(this.saboresPizza[0])

    }
    this._localStorageService.listaPizzaAcrescimo.forEach(item => {
      this.acrescimosPizza1.set(item.nome.toString(), JSON.parse(JSON.stringify(item)));
      this.acrescimosPizza2.set(item.nome.toString(), JSON.parse(JSON.stringify(item)));
    });
  }
  resetaAcrescimoPizza1() {
    this._localStorageService.listaPizzaAcrescimo.forEach(item => {
      this.acrescimosPizza1.set(item.nome.toString(), JSON.parse(JSON.stringify(item)));
    });
  }
  resetaAcrescimoPizza2() {
    this._localStorageService.listaPizzaAcrescimo.forEach(item => {
      this.acrescimosPizza2.set(item.nome.toString(), JSON.parse(JSON.stringify(item)));
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['idPedido']) {
      this.idPedido = changes['idPedido'].currentValue;
    }
    if (changes['idItem']) {
      this.idItem = changes['idItem'].currentValue;
    }
    if (changes['showModal']) {
      this.showModal = changes['showModal'].currentValue;
      if (this.showModal) {
        this.getDadosId();
      }
    }
  }

  getDadosId() {
    this._pedidoService.getItemPedido(this.idItem).then((response) => {
      this.itemPedidoPizza = response
      this.preencheDados();
      this.showConteudo = true;
    })
  }

  preencheDados() {
    if (this.itemPedidoPizza.nome == '') {
      this.itemPedidoPizza.tamanho = 'GG';
      this.itemPedidoPizza.quantidade = 1;
    }
    this.optionDefaultTamanho = {
      option: this.itemPedidoPizza.tamanho,
      value: this.itemPedidoPizza.tamanho
    };
    if (this.itemPedidoPizza.pedidoItemPizza != undefined) {
      let saboresItem: PizzaSaborDTO[] = [];
      if(this.itemPedidoPizza.pedidoItemPizza != ''){
        saboresItem = JSON.parse(this.itemPedidoPizza.pedidoItemPizza);
      } else{
        saboresItem.push(new PizzaSaborDTO);
      }
       
      this.quantidadeSabores = saboresItem.length.toString();
      this.optionDefaultQuantidadeSabores = {
        option: this.quantidadeSabores,
        value: this.quantidadeSabores
      }
      if (saboresItem.length > 0) {
        this.pizzaSabor1 = saboresItem[0];
        this.sabor1SelectDefault = {
          option: this.pizzaSabor1.nome,
          value: this.pizzaSabor1.nome
        }
        let retirarItens: PizzaSaborIngredienteDTO[] = []
        if(this.pizzaSabor1.pizzaSaborIngredientesRetirar != undefined){
          retirarItens = JSON.parse(JSON.stringify(this.pizzaSabor1.pizzaSaborIngredientesRetirar))
        }
        
        let acrescimosItens: PizzaSaborIngredienteDTO[] = []
        if(this.pizzaSabor1.pizzasAcrescimos != undefined){
          acrescimosItens = JSON.parse(JSON.stringify(this.pizzaSabor1.pizzasAcrescimos));
        }
        
        this.changeSaborPizza(1, {
          option: this.pizzaSabor1.nome,
          value: this.pizzaSabor1.nome
        });
        if (retirarItens != undefined) {
          retirarItens.forEach((value) => {
            this.ingredientesPizza1.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)))
          });
        }
        this.changeRetirar(1);
        if (acrescimosItens != undefined) {
          acrescimosItens.forEach((value) => {
            this.acrescimosPizza1.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)))
          });
        }
        this.changeAcrescimo(1)
      }

      if (saboresItem.length > 1) {
        this.pizzaSabor2 = saboresItem[1];
        this.sabor2SelectDefault = {
          option: this.pizzaSabor2.nome,
          value: this.pizzaSabor2.nome
        }
        let retirarItens: PizzaSaborIngredienteDTO[] = []
        if(this.pizzaSabor2.pizzaSaborIngredientesRetirar != undefined){
          retirarItens = JSON.parse(JSON.stringify(this.pizzaSabor2.pizzaSaborIngredientesRetirar)) 
        }
         
        let acrescimosItens: PizzaSaborIngredienteDTO[] = []
        if(this.pizzaSabor2.pizzasAcrescimos != undefined){
          acrescimosItens = JSON.parse(JSON.stringify(this.pizzaSabor2.pizzasAcrescimos));
        }
        
        this.changeSaborPizza(2, {
          option: this.pizzaSabor2.nome,
          value: this.pizzaSabor2.nome
        });
        if (retirarItens != undefined) {
          retirarItens.forEach((value) => {
            this.ingredientesPizza2.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)))
          });
        }
        this.changeRetirar(2);
        if (acrescimosItens != undefined) {
          acrescimosItens.forEach((value) => {
            this.acrescimosPizza2.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)))
          });
        }
        this.changeAcrescimo(2)
      }
    }
  }


  calculaValorItem() {
    let valorSabor1 = 0;
    let valorSabor2 = 0;
    let valorSabor = 0;
    let valorTotal = 0;

    valorSabor1 = this.calculaValorSabor(this.pizzaSabor1);
    valorSabor = valorSabor1;

    if (this.quantidadeSabores == '2') {
      valorSabor2 = this.calculaValorSabor(this.pizzaSabor2);
      valorSabor = (valorSabor1 + valorSabor2) / 2
    }

    valorTotal = valorSabor + this.calculaValorAcrescimos()
    this.itemPedidoPizza.valor = valorTotal * this.itemPedidoPizza.quantidade
  }

  calculaValorSabor(sabor: PizzaSaborDTO): number {
    switch (this.itemPedidoPizza.tamanho) {
      case 'GG':
        return sabor.tamanhoGigante;
      case 'G':
        return sabor.tamanhoGrande;
      case 'M':
        return sabor.tamanhoMedia;
      default:
        break;
    }
    return 0;
  }

  calculaValorAcrescimos(): number {
    let pizzasAcrescimosToda: Map<string, PizzaAcrescimoDTO> = new Map();
    let pizzasAcrescimos1: Map<string, PizzaAcrescimoDTO> = new Map();
    let pizzasAcrescimos2: Map<string, PizzaAcrescimoDTO> = new Map();

    this.pizzaSabor1.pizzasAcrescimos = Object.values(this.pizzaSabor1.pizzasAcrescimos);
    this.pizzaSabor1.pizzasAcrescimos.forEach((value) => {
      pizzasAcrescimos1.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)));
    })

    if (this.quantidadeSabores == '1') {
      pizzasAcrescimos1.forEach((value, key) => {
        pizzasAcrescimosToda.set(JSON.parse(JSON.stringify(key)), JSON.parse(JSON.stringify(value)))
      })
      pizzasAcrescimos1 = new Map()
    }
    if (this.quantidadeSabores == '2') {
      this.pizzaSabor2.pizzasAcrescimos = Object.values(this.pizzaSabor2.pizzasAcrescimos);
      this.pizzaSabor2.pizzasAcrescimos.forEach((value) => {
        pizzasAcrescimos2.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)));
      })
      this._localStorageService.listaPizzaAcrescimo.forEach((value) => {
        if (pizzasAcrescimos1.has(JSON.parse(JSON.stringify(value.nome))) && pizzasAcrescimos2.has(JSON.parse(JSON.stringify(value.nome)))) {
          pizzasAcrescimosToda.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)));
        }
      });
      pizzasAcrescimosToda.forEach((value, key) => {
        pizzasAcrescimos1.delete(key);
        pizzasAcrescimos2.delete(key);
      })
    }
    const valorAcrescimo1 = this.calculaValorAcrescimo(pizzasAcrescimos1, false);
    const valorAcrescimo2 = this.calculaValorAcrescimo(pizzasAcrescimos2, false);
    const valorAcrescimoToda = this.calculaValorAcrescimo(pizzasAcrescimosToda, true);
    return valorAcrescimo1 + valorAcrescimo2 + valorAcrescimoToda;
  }

  calculaValorAcrescimo(acrescimos: Map<string, PizzaAcrescimoDTO>, toda: boolean): number {
    let valor = 0;
    acrescimos.forEach((value) => {
      switch (this.itemPedidoPizza.tamanho) {
        case 'GG':
          if (toda) {
            valor = valor + (value.tamanhoGiganteToda != undefined ? value.tamanhoGiganteToda : 0);
          } else {
            valor = valor + (value.tamanhoGiganteMetade != undefined ? value.tamanhoGiganteMetade : 0);
          }
          break;
        case 'G':
          if (toda) {
            valor = valor + (value.tamanhoGrandeToda != undefined ? value.tamanhoGrandeToda : 0);
          } else {
            valor = valor + (value.tamanhoGrandeMetade != undefined ? value.tamanhoGrandeMetade : 0);
          }
          break;
        case 'M':
          if (toda) {
            valor = valor + (value.tamanhoMediaToda != undefined ? value.tamanhoMediaToda : 0);
          } else {
            valor = valor + (value.tamanhoMediaMetade != undefined ? value.tamanhoMediaMetade : 0);
          }
          break;
        default:
          break;
      }
    })
    return valor;
  }

  setQuantidade(item: string) {
    this.itemPedidoPizza.quantidade = Number(item);
    this.calculaValorItem();
  }

  setTamanhoItem(inputSelecionado: InputSelectOption) {
    this.itemPedidoPizza.tamanho = inputSelecionado.value;
    this.calculaValorItem();
  }

  handleChangeQuatidadeSabores(event: InputSelectOption) {
    if (this.quantidadeSabores == '1' && event.value == '2') {

      this.pizzaSabor2 = JSON.parse(JSON.stringify(this.pizzaSabor1));

      this.sabor2SelectDefault = {
        option: this.pizzaSabor2.nome,
        value: this.pizzaSabor2.nome
      }
      this.changeSaborPizza(2, {
        option: this.pizzaSabor2.nome,
        value: this.pizzaSabor2.nome
      })
      this.ingredientesPizza2 = new Map();
      this.ingredientesPizza1.forEach((value, key) => {
        this.ingredientesPizza2.set(JSON.parse(JSON.stringify(key)), JSON.parse(JSON.stringify(value)));
      })
    }
    if (event.value == '1' && this.quantidadeSabores == '2') {
      this.pizzaSabor2 = new PizzaSaborDTO
    }
    this.quantidadeSabores = event.value
  }

  changeSaborPizza(idLado: number, event: InputSelectOption) {
    const sabor = this.saboresPizza.find((value) => value.nome == event.option);

    if (sabor != undefined) {
      if (idLado == 1) {
        this.pizzaSabor1 = JSON.parse(JSON.stringify(sabor));
        this.pizzaSabor1.pizzaSaborIngredientes = Object.values(this.pizzaSabor1.pizzaSaborIngredientes);
        this.ingredientesPizza1 = new Map();
        this.pizzaSabor1.pizzaSaborIngredientes.forEach((value) => {
          this.ingredientesPizza1.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)));
        })
        this.resetaAcrescimoPizza1();

      }
      if (idLado == 2) {
        this.pizzaSabor2 = JSON.parse(JSON.stringify(sabor));
        this.pizzaSabor2.pizzaSaborIngredientes = Object.values(this.pizzaSabor2.pizzaSaborIngredientes);
        this.ingredientesPizza2 = new Map();
        this.pizzaSabor2.pizzaSaborIngredientes.forEach((value) => {
          this.ingredientesPizza2.set(JSON.parse(JSON.stringify(value.nome)), JSON.parse(JSON.stringify(value)));
        })
        this.resetaAcrescimoPizza2();
      }
    }

    this.calculaValorItem();
  }
  changeRetirar(idLado: number) {
    if (idLado == 1) {
      this.pizzaSabor1.pizzaSaborIngredientesRetirar = [];
      this.ingredientesPizza1.forEach((value, key) => {
        if (value.habilitado) {
          this.pizzaSabor1.pizzaSaborIngredientesRetirar.push(JSON.parse(JSON.stringify(value)))
        }
      });
    }
    if (idLado == 2) {
      this.pizzaSabor2.pizzaSaborIngredientesRetirar = [];
      this.ingredientesPizza2.forEach((value, key) => {
        if (value.habilitado) {
          this.pizzaSabor2.pizzaSaborIngredientesRetirar.push(JSON.parse(JSON.stringify(value)))
        }
      })
    }
  }
  changeAcrescimo(idLado: number) {
    if (idLado == 1) {
      this.pizzaSabor1.pizzasAcrescimos = [];
      this.acrescimosPizza1.forEach((value, key) => {
        if (value.habilitado) {
          this.pizzaSabor1.pizzasAcrescimos.push(JSON.parse(JSON.stringify(value)))
        }
      });
    }
    if (idLado == 2) {
      this.pizzaSabor2.pizzasAcrescimos = [];
      this.acrescimosPizza2.forEach((value, key) => {
        if (value.habilitado) {
          this.pizzaSabor2.pizzasAcrescimos.push(JSON.parse(JSON.stringify(value)))
        }
      })
    }
    this.calculaValorItem();
  }
  preencheDescricaoRetirar(sabor: PizzaSaborDTO): string {
    let descricao = '';
    if (sabor.pizzaSaborIngredientesRetirar != undefined && sabor.pizzaSaborIngredientesRetirar.length > 0) {
      descricao = '=========RETIRAR=======\r\n';
      sabor.pizzaSaborIngredientesRetirar.forEach((value) => {
        descricao = `${descricao} - ${value.nome} \r\n`
      });
    }
    return descricao;
  }

  preencheDescricaoAcrescimo(sabor: PizzaSaborDTO): string {
    let descricao = '';
    if (sabor.pizzasAcrescimos != undefined && sabor.pizzasAcrescimos.length > 0) {
      descricao = '======ACRESCIMO========\r\n';
      sabor.pizzasAcrescimos.forEach((value) => {
        descricao = `${descricao} - ${value.nome} \r\n`
      });
    }
    return descricao;
  }

  save() {
    let tamanho = 'Gigante';

    switch (this.itemPedidoPizza.tamanho) {
      case 'GG':
          tamanho = 'GIGANTE'
        break;
      case 'G':
          tamanho = 'GRANDE'
        break;
      case 'M':
          tamanho = 'MEDIA'
        break;
    
      default:
        break;
    }

    this.itemPedidoPizza.descricao = '';
    const pedidoItemPizza: PizzaSaborDTO[] = []
    const sabor1Dados: PizzaSaborDTO = JSON.parse(JSON.stringify(this.pizzaSabor1))
    const sabor2Dados: PizzaSaborDTO = JSON.parse(JSON.stringify(this.pizzaSabor2))
    if (this.quantidadeSabores == '1') {
      this.itemPedidoPizza.nome = sabor1Dados.nome
      this.itemPedidoPizza.descricao =
        this.preencheDescricaoRetirar(sabor1Dados)
        + '=======================\r\n'
        + this.preencheDescricaoAcrescimo(sabor1Dados);
      pedidoItemPizza.push(sabor1Dados);

      this.itemPedidoPizza.descricao = this.itemPedidoPizza.quantidade + ' x '
        + this.itemPedidoPizza.nome + '\r\n'
        + 'Tamanho: ' + tamanho + '\r\n'
        + 'Valor: ' + this.itemPedidoPizza.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
        + '\r\n'
        + this.itemPedidoPizza.descricao;
    }

    if (this.quantidadeSabores == '2') {
      this.itemPedidoPizza.nome = `${sabor1Dados.nome} / ${sabor2Dados.nome}`
      this.itemPedidoPizza.descricao =
        'MEIO: ->' + sabor1Dados.nome
        + '\r\n'
        + this.preencheDescricaoRetirar(sabor1Dados)
        + this.preencheDescricaoAcrescimo(sabor1Dados)
        + 'MEIO: ->' + sabor2Dados.nome
        + '\r\n'
        + this.preencheDescricaoRetirar(sabor2Dados)
        + this.preencheDescricaoAcrescimo(sabor2Dados);

      pedidoItemPizza.push(sabor1Dados);
      pedidoItemPizza.push(sabor2Dados);

      this.itemPedidoPizza.descricao = this.itemPedidoPizza.quantidade
        + ' x MEIO A MEIO' + '\r\n'
        + 'Tamanho: ' + tamanho + '\r\n'
        + 'Valor: ' + this.itemPedidoPizza.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
        + '\r\n'
        + this.itemPedidoPizza.descricao;
    }

    this.itemPedidoPizza.pedidoItemPizza = JSON.stringify(pedidoItemPizza);
    this.itemPedidoPizza.tipo = 'Pizza'
    this._pedidoService.adicionaItemPedido(Number(this.idPedido), this.itemPedidoPizza).then((response) => {
      this.onCloseModal();
    });
  }

  onCloseModal() {
    this.showModal = false;
    this.showConteudo = false;
    this.showModalChange.emit(this.showModal);
    this.dadosItemChange.emit(this.itemPedidoPizza);
  }
  openModal() { this.showModal = true; }
}
