import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';

@Component({
  selector: 'app-pizzas',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './pizzas.component.html',
  styleUrl: './pizzas.component.scss'
})
export class PizzasComponent {
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

  tableActionDataClick(action : ButtonActionClick){
    console.log(action)
  }

}
