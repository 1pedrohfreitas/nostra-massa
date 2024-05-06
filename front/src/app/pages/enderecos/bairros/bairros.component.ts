import { Component } from '@angular/core';
import { BairroDTO } from '../../../shared/models/ClienteDTO';
import { EnderecosService } from '../enderecos.service';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'app-bairros',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './bairros.component.html',
  styleUrl: './bairros.component.scss'
})
export class BairrosComponent {
  id : string = '';
  nome: string = '';
  valorTaxa : string = '0';
  listaItem : BairroDTO[] = [];

  constructor(
    private _enderecoService : EnderecosService
  ){
    this._enderecoService.getListaDeBairros().then((response)=>{
      this.listaItem = response.content
    })
  }
  adicionar(){

  }
  excluir(){

  }
}
