import { Component, Input, SimpleChanges } from '@angular/core';
import { NotificacaoComponent } from './notificacao/notificacao.component';

import { SharedModule } from '../../shared/shared.module';
import { Notificacao } from '../../shared/models/RestResponse';

@Component({
  selector: 'app-notificacoes',
  standalone: true,
  imports: [NotificacaoComponent, SharedModule],
  templateUrl: './notificacoes.component.html',
  styleUrl: './notificacoes.component.scss'
})
export class NotificacoesComponent {
  @Input() notificacoes: Notificacao[] = [];

  constructor(){
  }

  ngOnChanges(changes: SimpleChanges): void {
    changes['notificacoes'].currentValue
  }
}
