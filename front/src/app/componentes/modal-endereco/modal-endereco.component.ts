import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { LocalStorageServiceService } from '../../services/local-storage-service.service';
import { BairroDTO, ClienteDTO, RuaDTO } from '../../shared/models/ClienteDTO';
import { ClientesService } from '../../pages/cliente-page/clientes.service';
import { AutoCompleteServiceService } from '../../services/auto-complete-service.service';

@Component({
  selector: 'app-modal-endereco',
  templateUrl: './modal-endereco.component.html',
  styleUrl: './modal-endereco.component.scss'
})
export class ModalEnderecoComponent {
  
  @Input() telefoneCliente : string = '';
  @Input() showModal : boolean = false;
  showConteudo : boolean = false;
  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  
  cliente : ClienteDTO = new ClienteDTO;

  listaRuas : string[] = [];
  listaBairros : string[] = [];
  
  constructor(
    private _localStorageService : LocalStorageServiceService,
    private _clienteService : ClientesService,
    private _autoCompleteService : AutoCompleteServiceService
  ){
    
    
    // console.log(this.listaRuas)
    
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['telefoneCliente']) {
      this.telefoneCliente = changes['telefoneCliente'].currentValue
    }
    if (changes['showModal']) {
      this.showModal = changes['showModal'].currentValue;
      if(this.showModal && this.telefoneCliente != ''){
        this.getDadosClienteByTelefone();
      }
      
    }
  }
  getDadosClienteByTelefone(){
    if(this.telefoneCliente != ''){
      this._clienteService.getDadosClienteByTelefone(this.telefoneCliente).then((response)=>{
        this.cliente = response
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
    this._clienteService.adicionaCliente(this.cliente).then((response)=>{
      this.onCloseModal();
    })  
  }

  handleBairro(value: string){
    if(value == ''){
      this.listaBairros = []
    this._localStorageService.listaBairros.forEach(value=>{
      this.listaBairros.push(value.nome);
    });
    } else {
      this._autoCompleteService.autoCompleteBairro(value).then((response)=>{
        this.listaBairros = response.content
      });
    }
    console.log(value)
    console.log(this.listaBairros)
    // const bairroSelecionado: BairroDTO | undefined = this._localStorageService.listaBairros.find(opt => opt.nome!= undefined && opt.nome === value);
    // if(bairroSelecionado != undefined){
    //   this.cliente.bairro = bairroSelecionado.nome;
    //   this.cliente.taxaEntrega = bairroSelecionado.taxaEntrega
    // }
  }

  handleRua(value: string){
    if(value == ''){
      this.listaRuas = [];
this._localStorageService.listaRuas.forEach(value=>{
      this.listaRuas.push(value.nome);
    });
    } else {
      this._autoCompleteService.autoCompleteRua(value).then((response)=>{
        this.listaRuas = response.content
      });
    }
    console.log(value)
    console.log(this.listaRuas)
    
    // const ruaSelecionada: RuaDTO | undefined = this._localStorageService.listaRuas.find(opt => opt.nome != undefined && opt.nome === value);
    // if(ruaSelecionada != undefined){
    //   this.cliente.rua= ruaSelecionada.nome;
    // }
  }
}
