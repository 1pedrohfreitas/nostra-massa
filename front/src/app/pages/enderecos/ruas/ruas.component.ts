import { Component } from '@angular/core';
import { RuaDTO } from '../../../shared/models/ClienteDTO';
import { SharedModule } from '../../../shared/shared.module';
import { EnderecosService } from '../enderecos.service';

@Component({
  selector: 'app-ruas',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './ruas.component.html',
  styleUrl: './ruas.component.scss'
})
export class RuasComponent {
  id : string = '';
  nome: string = '';
  valorTaxa : number = 0;
  listaItem : RuaDTO[] = [];

  constructor(
    private _enderecoService : EnderecosService
  ){
    this._enderecoService.getListaDeRuas().then((response)=>{
      this.listaItem = response.content
    })
  }
  adicionar(){

  }
  excluir(){

  }
}
