import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { InputSelectOption } from '../input-select/input-select';

@Component({
  selector: 'app-input-text-autocomplete',
  templateUrl: './input-text-autocomplete.component.html',
  styleUrl: './input-text-autocomplete.component.scss'
})
export class InputTextAutocompleteComponent {
  @Input() label: string = '';
  @Input() id: string = '';
  @Input() options:InputSelectOption[] = [];
  @Input() optionDefault ?: InputSelectOption;
  @Input() userInput: any = '';

  @Output() optionSelected : EventEmitter<InputSelectOption> = new EventEmitter<InputSelectOption>();
  @Output() valueChange: EventEmitter<string> = new EventEmitter<string>();
  @Output() listClosed = new EventEmitter<InputSelectOption>();
  

  showList = false;

  filteredOptions: InputSelectOption[] = [];
  
  constructor(){
    if(this.optionDefault != undefined){
      this.selectOption(this.optionDefault)
    }
    if(this.userInput == undefined){
      this.userInput = ''
    } else {
      this.userInput = this.userInput.toString()
    }
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['optionDefault']) {
      if(this.optionDefault != undefined){
        this.selectOption(this.optionDefault);
      }
    }
  }
  selectOption(optionValue: InputSelectOption) {
    this.optionSelected.emit(optionValue);
    this.userInput = optionValue.option;
    this.changeShowList(false)
    this.valueChange.emit(optionValue.option);
  }

  filterOptions() {
      this.filteredOptions = this.options.filter(opt =>
        opt.option.toLowerCase().includes(this.userInput.toLowerCase())
      );
  }

  changeShowList(show : boolean){
    if(this.userInput == ''){
      this.filteredOptions = this.options;
    }
    this.showList = show;
  }
  onCloseList() {
    if(this.filteredOptions.length == 0){
      const userImputOption : InputSelectOption = {option : this.userInput, value : this.userInput}
      this.selectOption(userImputOption);
    }
    if(this.showList == true){
      this.changeShowList(false);
      this.listClosed.emit();
    }
    
  }

}
