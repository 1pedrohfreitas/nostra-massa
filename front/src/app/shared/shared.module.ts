import { NgModule } from '@angular/core';
import { AsyncPipe, CommonModule, JsonPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PedroHFreitasLibModule } from 'pedrohfreitas-lib';
import { DefaultPageComponent } from '../pages/default-page/default-page.component';



@NgModule({
  declarations: [
    DefaultPageComponent
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
    PedroHFreitasLibModule,
    DefaultPageComponent
  ],
})
export class SharedModule { }
