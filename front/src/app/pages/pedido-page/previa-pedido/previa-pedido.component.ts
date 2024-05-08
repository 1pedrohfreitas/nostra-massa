import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-previa-pedido',
  templateUrl: './previa-pedido.component.html',
  styleUrl: './previa-pedido.component.scss'
})
export class PreviaPedidoComponent {

  @Input() previa: string = '';
  @Input() showModal: boolean = false;
  @Output() showModalChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['previa']) {
      this.previa = changes['previa'].currentValue//.replace(/\n/g, "<br>");
    }
  }
  onCloseModal() {
    this.showModal = false;
    this.showModalChange.emit(this.showModal);
  }

}
