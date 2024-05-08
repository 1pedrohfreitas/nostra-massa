import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { InputSelectOption } from '../input-select/input-select';
import { LocalStorageServiceService } from '../../services/local-storage-service.service';
import { BairroDTO, EnderecoDTO, RuaDTO } from '../../shared/models/ClienteDTO';

@Component({
  selector: 'app-modal-endereco',
  templateUrl: './modal-endereco.component.html',
  styleUrl: './modal-endereco.component.scss'
})
export class ModalEnderecoComponent {
  
  @Input() enderecoInput ?: EnderecoDTO = new EnderecoDTO;
  @Input() showModal : boolean = false;
  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() returnEndereco: EventEmitter<EnderecoDTO> = new EventEmitter<EnderecoDTO>();

  listaRuas : InputSelectOption[] = [];
  listaBairros : InputSelectOption[] = [];
  ruaDefault ?: InputSelectOption;
  bairroDefault ?: InputSelectOption;
  
  endereco : EnderecoDTO = new EnderecoDTO
  
  constructor(
    private _localStorageService : LocalStorageServiceService
  ){
    this.listaRuas = this._localStorageService.converteListaItemParaOption(this._localStorageService.listaRuas,'nome','id',false);
    this.listaBairros = this._localStorageService.converteListaItemParaOption(this._localStorageService.listaBairros,'nome','id',false);
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['enderecoInput']) {
      this.endereco = changes['enderecoInput'].currentValue
      this.carregaDadosEndereco();
    }
  }
  carregaDadosEndereco(){
    
    if(this.enderecoInput != undefined && this.enderecoInput.id != undefined){
      this.endereco = this.enderecoInput
      if(this.endereco.bairro != undefined){
        this.bairroDefault = {
          option : this.endereco.bairro.nome,
          value: this.endereco.bairro.id.toString()
        }
      }
      if(this.endereco.rua != undefined){
        this.ruaDefault = {
          option : this.endereco.rua.nome,
          value: this.endereco.rua.id.toString()
        }
      }
    }
  }
  onCloseModal() {
    this.showModal = false;      
    this.showModalChange.emit(this.showModal);

    this.returnEndereco.emit(this.endereco);
  }
  openModal() {
    this.showModal = true;
  }

  save(){
    this.endereco.enderecoDescricao = this.endereco.rua?.nome + ', N: ' + this.endereco.numero;

    if(this.endereco.bloco != null && this.endereco.bloco != ''){
      this.endereco.enderecoDescricao = this.endereco.enderecoDescricao + ', Bloco: ' + this.endereco.bloco;
    }

    if(this.endereco.apartamento != null && this.endereco.apartamento != ''){
      this.endereco.enderecoDescricao = this.endereco.enderecoDescricao + ', AP: ' + this.endereco.apartamento;
    }
    this.endereco.enderecoDescricao = this.endereco.enderecoDescricao + ', Bairro: ' + this.endereco.bairro?.nome;
    if(this.endereco.complemento != null && this.endereco.complemento != ''){
      this.endereco.enderecoDescricao = this.endereco.enderecoDescricao + ', Comp: ' + this.endereco.complemento;
    }

    this.endereco = this.endereco;
    this.onCloseModal();
  }

  handleBairro(inputSelecionado: InputSelectOption){
    const bairroSelecionado: BairroDTO | undefined = this._localStorageService.listaBairros.find(opt => opt.id!= undefined && opt.id.toString() === inputSelecionado.value);
    if(bairroSelecionado != undefined){
      this.endereco.bairro = bairroSelecionado;
    }
  }

  handleRua(inputSelecionado: InputSelectOption){
    const ruaSelecionada: RuaDTO | undefined = this._localStorageService.listaRuas.find(opt => opt.id!= undefined && opt.id.toString() === inputSelecionado.value);
    if(ruaSelecionada != undefined){
      this.endereco.rua= ruaSelecionada;
    }
  }
}
