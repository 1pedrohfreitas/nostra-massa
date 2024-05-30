import { NgModule } from '@angular/core';
import { AsyncPipe, CommonModule, JsonPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PedroHFreitasLibModule } from 'pedrohfreitas-lib';



@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    FormsModule,
    AsyncPipe,
    PedroHFreitasLibModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    FormsModule,
    AsyncPipe,
    PedroHFreitasLibModule
  ],
})
export class SharedModule { }
