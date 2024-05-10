import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { InputSelectOption } from '../input-select/input-select';
import { LocalStorageServiceService } from '../../services/local-storage-service.service';
import { BairroDTO, ClienteDTO, RuaDTO } from '../../shared/models/ClienteDTO';
import { ClientesService } from '../../pages/cliente-page/clientes.service';

@Component({
  selector: 'app-modal-endereco',
  templateUrl: './modal-endereco.component.html',
  styleUrl: './modal-endereco.component.scss'
})
export class ModalEnderecoComponent {
  
  @Input() idCliente : number = 0;
  @Input() showModal : boolean = false;
  showConteudo : boolean = false;
  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  
  cliente : ClienteDTO = new ClienteDTO;

  listaRuas : InputSelectOption[] = [];
  listaBairros : InputSelectOption[] = [];
  
  constructor(
    private _localStorageService : LocalStorageServiceService,
    private _clienteService : ClientesService
  ){
    this.listaRuas = this._localStorageService.converteListaItemParaOption(this._localStorageService.listaRuas,'nome','id',false);
    this.listaBairros = this._localStorageService.converteListaItemParaOption(this._localStorageService.listaBairros,'nome','id',false);
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['idCliente']) {
      this.idCliente = changes['idCliente'].currentValue
    }
    if (changes['showModal']) {
      this.showModal = changes['showModal'].currentValue;
      if(this.showModal && this.idCliente != 0){
        this.getDadosClienteById();
      }
      
    }
  }
  getDadosClienteById(){
    if(this.idCliente != 0){
      this._clienteService.getDadosClienteById(this.idCliente).then((response)=>{
        this.cliente = response
        console.log(response)
        this.showConteudo = true;
      })
    }

  }
  
  onCloseModal() {
    this.showModal = false;      
    this.showConteudo = false;
    this.showModalChange.emit(this.showModal);
  }
  openModal() {
    this.showModal = true;
  }

  save(){
    this.cliente.taxaEntrega 
    this.cliente.enderecoDescricao = this.cliente.rua + ', N: ' + this.cliente.numero;

    if(this.cliente.bloco != null && this.cliente.bloco != ''){
      this.cliente.enderecoDescricao = this.cliente.enderecoDescricao + ', Bloco: ' + this.cliente.bloco;
    }

    if(this.cliente.apartamento != null && this.cliente.apartamento != ''){
      this.cliente.enderecoDescricao = this.cliente.enderecoDescricao + ', AP: ' + this.cliente.apartamento;
    }
    this.cliente.enderecoDescricao = this.cliente.enderecoDescricao + ', Bairro: ' + this.cliente.bairro;
    if(this.cliente.complemento != null && this.cliente.complemento != ''){
      this.cliente.enderecoDescricao = this.cliente.enderecoDescricao + ', Comp: ' + this.cliente.complemento;
    }
    this._clienteService.adicionaCliente(this.cliente).then((response)=>{
      this.onCloseModal();
    })  
  }

  handleBairro(inputSelecionado: InputSelectOption){
    const bairroSelecionado: BairroDTO | undefined = this._localStorageService.listaBairros.find(opt => opt.id!= undefined && opt.nome === inputSelecionado.option);
    if(bairroSelecionado != undefined){
      this.cliente.bairro = bairroSelecionado.nome;
      this.cliente.taxaEntrega = bairroSelecionado.taxaEntrega
    }
  }

  handleRua(inputSelecionado: InputSelectOption){
    const ruaSelecionada: RuaDTO | undefined = this._localStorageService.listaRuas.find(opt => opt.id!= undefined && opt.id.toString() === inputSelecionado.value);
    if(ruaSelecionada != undefined){
      this.cliente.rua= ruaSelecionada.nome;
    }
  }
}
