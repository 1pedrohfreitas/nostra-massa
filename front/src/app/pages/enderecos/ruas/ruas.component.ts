import { Component } from '@angular/core';
import { RuaDTO } from '../../../shared/models/ClienteDTO';
import { SharedModule } from '../../../shared/shared.module';
import { EnderecosService } from '../enderecos.service';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-ruas',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './ruas.component.html',
  styleUrl: './ruas.component.scss'
})
export class RuasComponent {
  
  rua : RuaDTO = new RuaDTO;
  listaRuas : string[] = [];
  tableCabecalho = ['Nome'];
  tableColunas = ['nome'];
  tableColunasSize = ['80'];
  tableData : any[] = [];
  tableActionsButtons : ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]
  
  constructor(
    private _enderecoService : EnderecosService
  ){
    this._enderecoService.getListaDeRuas().then((response)=>{
      this.tableData = response.content;
    })
  }

  tableActionDataClick(action : ButtonActionClick){
  }
  adicionar(){

  }
  excluir(){

  }
}
