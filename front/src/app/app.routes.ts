import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { PedidoPageComponent } from './pages/pedido-page/pedido-page.component';
import { ClientePageComponent } from './pages/cliente-page/cliente-page.component';
import { PedidoManualComponent } from './pages/pedido-page/pedido-manual/pedido-manual.component';
import { PageListaPedidosComponent } from './pages/pedido-page/page-lista-pedidos/page-lista-pedidos.component';
import { ParametrosComponent } from './pages/parametros/parametros.component';
import { PageProdutosComponent } from './pages/page-produtos/page-produtos.component';
import { PizzasComponent } from './pages/page-produtos/pizzas/pizzas.component';
import { AcrescimosComponent } from './pages/page-produtos/acrescimos/acrescimos.component';
import { IngredientesComponent } from './pages/page-produtos/ingredientes/ingredientes.component';
import { BebidasComponent } from './pages/page-produtos/bebidas/bebidas.component';
import { BairrosComponent } from './pages/enderecos/bairros/bairros.component';
import { RuasComponent } from './pages/enderecos/ruas/ruas.component';
import { ProdutoTipoComponent } from './pages/page-produtos/tipos/produto-tipo.component';

export const routes: Routes = [
    {
        path : '',
        component: HomeComponent,
    },
    {
        path : 'pedido',
        component: PedidoPageComponent,
        children: [
            {
                path: 'manual',
                component: PedidoManualComponent
            },
            {
                path: 'lista',
                component: PageListaPedidosComponent
            },
        ]
    },
    {
        path : 'cliente',
        component: ClientePageComponent,
        
    },
    {
        path : 'produtos',
        component: PageProdutosComponent,
        children: [
            {
                path: 'tipo',
                component: ProdutoTipoComponent,
            },
            {
                path: 'pizza',
                component: PizzasComponent,
            },
            {
                path: 'acrescimos',
                component: AcrescimosComponent,
            },
            {
                path: 'ingredientes',
                component: IngredientesComponent,
            },
            {
                path: 'bebidas',
                component: BebidasComponent,
            }
        ]
        
    },
    {
        path : 'endereco',
        children: [
            {
                path: 'rua',
                component: RuasComponent,
            },
            {
                path: 'bairro',
                component: BairrosComponent,
            },
        ]
        
    },
    {
        path : 'parametros',
        component: ParametrosComponent,
        
    },
];
