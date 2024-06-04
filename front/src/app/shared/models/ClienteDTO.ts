export class ClienteDTO {
    id: number = 0;
    nome: string = '';
    telefone: string = '';
    bairro: string = '';
    rua: string = '';
    numero : string = '';
    bloco: string = '';
    apartamento: string = '';
    complemento: string = '';
    enderecoDescricao: string = '';
}

export class BairroDTO {
    id: number = 0;
    nome: string = '';
    taxaEntrega: number = 0;
}

export class RuaDTO {
    id: number = 0;
    nome: string = '';
}

