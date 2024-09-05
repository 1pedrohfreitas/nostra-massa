import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { ProductType } from '../../../shared/models/Product';
import { ButtonAction, ButtonActionClick } from 'pedrohfreitas-lib';
import { ProdutosService } from '../produtos.service';

@Component({
  selector: 'app-produto-tipo',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './produto-tipo.component.html',
})
export class ProdutoTipoComponent {
 
  pageTitle : string = 'Tipos de Produtos';
  productType : ProductType = new ProductType();

  tableHeader: any[] = ['Tipo'];
  tableColluns: any[] = ['type'];
  tableCollunsSize: any[] = ['100'];
  tableData: any[] = [];
  tableActionsButtons: ButtonAction[] = [
    {
      action: 'detalhe',
      icon: 'lupa',
      disable: false
    },
  ]
new: any;
  constructor(
    private _produtoService : ProdutosService
  ){
    // this.getAll();
  }

  // save(){
  //   this._produtoService.create(this.productType,'type').then((response)=>{
  //     this.productType = response
  //   });
  // }
  // tableActionDataClick(action: ButtonActionClick) {
    
  // }

  // getAll() {
  //   this._produtoService.getAll('type').then((response) => {
  //     this.tableData = response.content
  //   })
  // }


//   export class ProductTypeDTO{
//     id ?: string;
//     created ?: string;
//     createdBy ?: string;
//     updated ?: string;
//     updatedBy ?: string;
//     tenantID ?: string;
//     actived ?: boolean;
//     type ?: string;
//     description ?: string;
//     itens ?: ProductItemDTO[] = [];
// }

}
