export class MenuDTO {
    nome: string = '';
    img: string = '';
    path: string = '';
    children?: MenuDTO[] = [];
}

export const Menu: MenuDTO[] = [
    {
        nome: 'Inicio',
        img: '',
        path: '',
    },
    {
        nome: 'Pedido',
        img: 'assets/images/pedido.png',
        path: '',
        children: [
            {
                nome: 'Novo',
                img: 'assets/images/plus.png',
                path: 'pedido/manual',
            },
            {
                nome: 'Listar',
                img: 'assets/images/lista.png',
                path: 'pedido/lista',
            },
        ]
    },
    {
        nome: 'Cliente',
        img: 'assets/images/cliente.png',
        path: 'cliente',
    },
    {
        nome: 'Produto',
        img: 'assets/images/produto.png',
        path: '',
        children: [
            {
                nome: 'Pizza',
                img: 'assets/images/pizza.png',
                path: 'produtos/pizza',
    
            },
            {
                nome: 'Ingredientes',
                img: 'assets/images/ingrediente.png',
                path: 'produtos/ingredientes',
    
            },
            {
                nome: 'Acrescimos',
                img: 'assets/images/plus.png',
                path: 'produtos/acrescimos',
            },
            {
                nome: 'Bebidas',
                img: 'assets/images/bebida.png',
                path: 'produtos/bebidas',
            }
        ]

    },
    {
        nome: 'Endereços',
        img: 'assets/images/map.png',
        path: '',
        children: [
            {
                nome: 'Ruas',
                img: 'assets/images/rua.png',
                path: 'endereco/rua',
    
            },
            {
                nome: 'Bairros',
                img: 'assets/images/bairro.png',
                path: 'endereco/bairro',
    
            },
        ]

    },
    {
        nome: 'Parametros',
        img: 'assets/images/ferramenta.png',
        path: 'parametros',

    },
];