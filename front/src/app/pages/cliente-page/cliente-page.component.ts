import { Component, Input } from '@angular/core';
import { ClientesService } from './clientes.service';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { PedidoModule } from '../pedido-page/pedido.module';
import { InputSelectOption } from '../../componentes/input-select/input-select';
import { LocalStorageServiceService } from '../../services/local-storage-service.service';
import { GetDadosServiceService } from '../../services/get-dados-service.service';
import { ClienteDTO, EnderecoDTO } from '../../shared/models/ClienteDTO';

@Component({
  selector: 'app-cliente-page',
  standalone: true,
  imports: [RouterOutlet,RouterLink, RouterLinkActive, PedidoModule, SharedModule],
  templateUrl: './cliente-page.component.html',
  styleUrl: './cliente-page.component.scss'
})
export class ClientePageComponent {

  listaTelefones : InputSelectOption[] = [];

  listaClientes : ClienteDTO[] = [];
  clienteNome : string = '';
  clienteTelefone : string = '';

  clientePedidoByTelefone : boolean = false;
  showModalEndereco: boolean = false;

  cliente : ClienteDTO = new ClienteDTO;

  constructor(
    private _clientesService : ClientesService,
    private _localStorageService: LocalStorageServiceService,
  ){
    this.listaTelefones = this._localStorageService.converteListaItemParaOption(this._localStorageService.listaTelefones, '', '', true);
    this.getListaClientes();
  }

  handleTelefone(telefoneSelecionado: InputSelectOption) {
    this.clienteTelefone = telefoneSelecionado.option;
    if (telefoneSelecionado?.value != undefined && telefoneSelecionado.option != '') {
      this.getDadosClienteByTelefone(telefoneSelecionado.value);
    }
  }

  adicionarCliente(){
    this.cliente.nome = this.clienteNome;
    this.cliente.telefone = this.clienteTelefone;

    if(this.cliente.nome == '' && this.cliente.nome.length < 3){
      alert('Nome Obrigatorio')
    }

    if(this.cliente.telefone == '' && this.cliente.telefone.length < 8){
      alert('Telefone Obrigatorio')
    }

    if(this.cliente.nome != '' && this.cliente.telefone != '')
    this._clientesService.adicionaCliente(this.cliente).then((data)=>{
      this.cliente = data;
      this.getListaClientes();
    })
  }
  openModalEndereco() {
    this.showModalEndereco = true;
  }

  adicionaEnderecoCliente(endereco: EnderecoDTO){
    if(this.cliente.id != undefined){
      this._clientesService.saveDadosEnderecoCliente(this.cliente.id, endereco).then((response)=>{
        this.cliente.endereco = response
        this.getListaClientes();
      })
    }
  }

  excluirCliente(){
    if(this.cliente.id != undefined){
      this._clientesService.excluirCliente(this.cliente.id).then((response)=>{
        this.getListaClientes();
      })
    }
    
  }

  getDadosClienteByTelefone(telefone: string | undefined) {
    if(telefone != undefined){
      this.clienteTelefone = telefone;
      this._clientesService.getDadosClienteByTelefone(telefone).then((response) => {
        if (response != null) {
          this.cliente = response;
          if (response.nome != undefined) {
            this.clienteNome = response.nome;
          }
          this.clientePedidoByTelefone = true;
        }
      });
    }
    
  }
  getListaClientes(){
    this._clientesService.getListaClientes().then((response) => {
      this.listaClientes = response.content
    })
  }
}
