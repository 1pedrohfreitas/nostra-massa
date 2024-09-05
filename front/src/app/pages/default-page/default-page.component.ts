import { Component, Input, SimpleChanges } from '@angular/core';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';
import { CRUDBasicService } from '../../services/crud-basic.service ';
import { DefaultField } from '../../shared/models/Product';

@Component({
  selector: 'app-default-page',
  templateUrl: './default-page.component.html',
})
export class DefaultPageComponent<T>{

  @Input() modelData : T | undefined;
  @Input() defaultFields : DefaultField[] = [];
  @Input() pageTitle : string = 'Page Defaut';

  modelFields : any;

  modelName : string = '';

  

  tableHeader: string[] = ['Tipo'];
  tableColluns: string[] = ['type'];
  tableCollunsSize: any[] = ['100'];
  tableData: T[] = [];
  tableActionsButtons: ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]
  constructor(
    private _crud : CRUDBasicService<T>
  ){
    
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['modelData']) {
      if(this.modelData != undefined){
        this.modelName = this.modelData.constructor.name;
        this.modelFields = this.modelData;
        this._crud.setModelType(this.modelData.constructor.name);
      }
      this.getAll();
    }
    
  }

  save(){
    this.modelData = this.modelFields;
    if(this.modelData != undefined){
      if((this.modelData as any).id != null){
        this._crud.update((this.modelData as any).id,this.modelData).then((response)=>{
          this.getAll();
        });
      } else {
        this._crud.create(this.modelData).then((response)=>{
          this.getAll();
        });
      }
    }
  }

  getAll(){
    this._crud.readList().then((response)=>{
      this.tableData = response.content
    });
  }
  getById(id : string) {
    this._crud.readItem(id).then((response)=>{
      this.modelData = response
    });
  }

  getByIdDetail(id : string) {
    this._crud.readItemAndChildren(id).then((response)=>{
      this.modelData = response
    });
  }

  delete(id : string) {
    this._crud.delete(id).then((response)=>{this.getAll()});
  }
  
  tableActionDataClick(action : ButtonActionClick){
    this.getById(action.data.id)
  }
}
