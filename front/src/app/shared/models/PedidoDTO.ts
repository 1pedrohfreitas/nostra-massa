import { ClienteDTO, EnderecoDTO } from "./ClienteDTO";
import { PizzaAcrescimoDTO, PizzaSaborDTO } from "./PizzaDTO";

export class PedidoDTO {
    id: number = 0;
    idPedido?: number = 0;
    dataPedido: string = '';
    entrega : boolean= true;
    apalito : boolean = false;
    tipoPagamento?: string = '';
    status?: string = '';
    valor: number = 0;
    valorTaxa: number = 0;
    enderecoDescricao?: string = '';
    bairro?: string = '';
	rua?: string = '';
	numero?: string = '';
	bloco?: string = '';
	apartamento?: string = '';
	complemento?: string = '';
    observacao?: string = '';
    idCliente?: number;
    clienteTelefone?: string = '';
    clienteNome?: string = '';
    itensPedido: PedidoItemDTO[] = [];
    pedidoRelatorio: string = '';
}
export class PedidoItemDTO {
    id : number = 0;
    idPedido : number = 0;
    descricao : string = '';
    tipo: 'Pizza' | 'Bebida' = 'Pizza';
    valor: number = 0;
    tamanho: string = '';
    quantidade : number = 1;
    nome: string = '';
    pedidoItemPizza?: string;
}
export class PedidoItemPizzaDTO {
    pizzaSabores : PizzaSaborDTO[] = [];
}





