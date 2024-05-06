import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrl: './input.component.scss'
})
export class InputComponent {

  @Input() label: string = '';
  @Input() id: string = '';
  @Input() type: 'text' | 'number'= 'text';
  @Input() readonly: boolean = false;

  @Input() value: any = '';
  @Output() valueChange = new EventEmitter<string>();

  constructor(){
    if(this.value == undefined){
      this.value = ''
    } else {
      this.value = this.value.toString()
    }
  }
  onValueChange(event: any) {
    this.valueChange.emit(event.target.value);
  }
}
