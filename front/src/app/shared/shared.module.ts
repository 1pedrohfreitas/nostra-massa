import { NgModule } from '@angular/core';
import { AsyncPipe, CommonModule, JsonPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from '../componentes/input/input.component';
import { InputSelectComponent } from '../componentes/input-select/input-select.component';
import { InputTextAutocompleteComponent } from '../componentes/input-text-autocomplete/input-text-autocomplete.component';
import { ClickOutsideDirective } from '../diretivas/click-outside.directive';
import { InputCheckboxComponent } from '../componentes/input-checkbox/input-checkbox.component';

@NgModule({
  declarations: [
    InputComponent,
    InputSelectComponent,
    InputTextAutocompleteComponent,
    InputCheckboxComponent,
    ClickOutsideDirective,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    FormsModule,
    AsyncPipe,
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    FormsModule,
    AsyncPipe,
    InputComponent,
    InputSelectComponent,
    InputTextAutocompleteComponent,
    InputCheckboxComponent,
    ClickOutsideDirective,
  ],
})
export class SharedModule { }
