import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { InputSelectOption } from './input-select';

@Component({
  selector: 'app-input-select',
  templateUrl: './input-select.component.html',
  styleUrl: './input-select.component.scss'
})
export class InputSelectComponent {
  @Input() label: string = '';
  @Input() id: string = '';
  @Input() options: InputSelectOption[] = [];
  @Input() optionDefault?: InputSelectOption;
  
  @Output() optionSelected : EventEmitter<InputSelectOption> = new EventEmitter<InputSelectOption>();
  @Output() valueChange: EventEmitter<string> = new EventEmitter<string>();
  selectedValue: string = '';
  
  ngOnChanges(changes: SimpleChanges) {
    if (changes['optionDefault']) {
      this.optionDefault = changes['optionDefault'].currentValue
      if(this.optionDefault != undefined){
        this.selectedValue = this.optionDefault.value
        let changeOptions = this.options.map((op)=>{
          if(op.value == this.optionDefault?.value){
            op.defaultValue = true;
          }
          return op;
        })
        this.options = changeOptions;
      }
    }

  }
  
  onValueChange(event: any) {
    this.selectedValue = event.target.value;
    this.optionSelected.emit(this.selectOptionByValue(this.selectedValue))
    this.valueChange.emit(this.selectedValue);
  }
  
  selectOptionByValue(value : string) : InputSelectOption{
    const selectedOption: InputSelectOption | undefined = this.options.find(opt => opt.value === value);
    return selectedOption != undefined ? selectedOption : new InputSelectOption();
  }
}
