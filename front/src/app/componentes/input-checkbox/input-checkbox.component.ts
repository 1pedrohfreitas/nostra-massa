import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-input-checkbox',
  templateUrl: './input-checkbox.component.html',
  styleUrl: './input-checkbox.component.scss'
})
export class InputCheckboxComponent {
  @Input() label: string | undefined = '';
  @Input() id: string = '';
  @Input() readonly: boolean = false;

  @Input() value: boolean = false;
  @Output() valueChange = new EventEmitter<boolean>();

  constructor(){
    if(this.value == undefined){
      this.value = false
    }
    if(this.label == undefined){
      this.label = ''
    }
  }
  onValueChange(event: any) {
    this.valueChange.emit(event.target.checked);
  }
}
