import { Component, EventEmitter, Input, Output } from '@angular/core';
import { LocalStorageServiceService } from '../../../../services/local-storage-service.service';
import { GetDadosServiceService } from '../../../../services/get-dados-service.service';
import { ClientesService } from '../../../cliente-page/clientes.service';
import { ClienteDTO } from '../../../../shared/models/ClienteDTO';

@Component({
  selector: 'app-modal-cliente',
  templateUrl: './modal-cliente.component.html',
  styleUrl: './modal-cliente.component.scss'
})
export class ModalClienteComponent {
  listaTelefones: string[] = [];
  clientePedidoByTelefone: boolean = false;
  clienteEnderecoDescricao : string = '';
  showItemEndereco: boolean = false;
  showModalEndereco: boolean = false;
  opcoesTipoPedido = [
    { id: 1, nome: 'Entrega', img: 'assets/images/entregador.png', imgMsg: 'Entrega' },
    { id: 2, nome: 'Buscar', img: 'assets/images/balcao.png', imgMsg: 'Buscar' }
  ];
  
  @Input() showModal : boolean = false;
  @Input() clientePedido : string = '';
  @Input() telefonePedido : string = '';
  @Input() enderecoDescricao : string = '';
  @Input() opcaoTipoPedidoSelecionada: number = 1;
  
  
  @Output() clientePedidoChange: EventEmitter<string> = new EventEmitter<string>();
  @Output() telefonePedidoChange: EventEmitter<string> = new EventEmitter<string>();
  @Output() enderecoDescricaoChange: EventEmitter<string> = new EventEmitter<string>();
  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();
 
  onCloseModal() {
    this.showModal = false;      
    this.showModalChange.emit(this.showModal);
  }
  openModal() {
    this.showModal = true;
  }

  save(){
    this.onCloseModal();
  }
  constructor(
    private _localStorageService: LocalStorageServiceService,
    private _clientesService: ClientesService,
    private _getDadosService: GetDadosServiceService
  ){
  }

  getDadosClienteByTelefone(telefone: string) {
    this._clientesService.getDadosClienteByTelefone(telefone).then((response) => {
      if (response != null) {
        if (response.nome != undefined) {
          this.clientePedido = response.nome;
        }
        this.clientePedidoByTelefone = true;
      }
    });
  }

  adicionaCliente() {
    const cliente: ClienteDTO = new ClienteDTO
    cliente.nome = this.clientePedido;
    cliente.telefone = this.telefonePedido

    this._clientesService.adicionaCliente(cliente).then((response) => {
      this._getDadosService.getDadosGeral();
    })
  }
  handleTelefone(telefoneSelecionado: string) {
    this.telefonePedido = telefoneSelecionado;
    if (telefoneSelecionado != undefined && telefoneSelecionado != '') {
      this.getDadosClienteByTelefone(telefoneSelecionado);
    }
    this.validaDadosHabilitaEndereco()
  }
  validaDadosHabilitaEndereco() {
    this.showItemEndereco = (this.clientePedido != '' && this.telefonePedido != '')
  }
  openModalEndereco() {

    this.showModalEndereco = true;
  }
}
