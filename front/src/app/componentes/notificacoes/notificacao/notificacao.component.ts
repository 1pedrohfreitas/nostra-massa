import { Component, Input } from '@angular/core';

import { SharedModule } from '../../../shared/shared.module';
import { Notificacao } from '../../../shared/models/RestResponse';

@Component({
  selector: 'app-notificacao',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './notificacao.component.html',
  styleUrl: './notificacao.component.scss'
})
export class NotificacaoComponent {

  @Input() notificacao: Notificacao = new Notificacao;
  @Input() idNotificacao: number = 0;

  idNotificacaoNome =`notificacao-${this.idNotificacao}`
  ngOnInit(): void {
    setTimeout(() => this.destruir(), 4000);
  }

  destruir(): void {
    const element = document.getElementById(this.idNotificacaoNome);
    if (element) {
      element.parentNode?.removeChild(element);
    }
  }

}
