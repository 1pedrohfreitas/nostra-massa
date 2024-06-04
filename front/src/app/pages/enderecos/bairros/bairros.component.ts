import { Component } from '@angular/core';
import { BairroDTO } from '../../../shared/models/ClienteDTO';
import { EnderecosService } from '../enderecos.service';
import { SharedModule } from '../../../shared/shared.module';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-bairros',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './bairros.component.html',
  styleUrl: './bairros.component.scss'
})
export class BairrosComponent {

  bairro : BairroDTO = new BairroDTO;
  listaBairros : string[] = [];
  tableCabecalho = ['Nome','Valor Taxa'];
  tableColunas = ['nome','taxaEntrega'];
  tableColunasSize = ['80','15'];
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
    this._enderecoService.getListaDeBairros().then((response)=>{
      this.tableData = [];
      response.content.forEach(value =>{
        this.tableData.push({
          id : value.id,
          nome : value.nome,
          taxaEntrega : value.taxaEntrega.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
        })
      })
    })
  }

  tableActionDataClick(action : ButtonActionClick){
  }
  adicionar(){

  }
  excluir(){

  }
}
