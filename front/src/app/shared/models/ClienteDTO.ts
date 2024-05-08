export class ClienteDTO {
    id?: number;
    nome?: string;
    telefone?: string;
    endereco?: EnderecoDTO;
}

export class EnderecoDTO {
    id: number = 0;
    bairro: BairroDTO = new BairroDTO;
    rua: RuaDTO = new RuaDTO;
    numero : string = '';
    bloco: string = '';
    apartamento: string = '';
    complemento: string = '';
    enderecoDescricao: string = '';
}

export class BairroDTO {
    id: number = 0;
    nome: string = '';
    valorTaxa: number = 0;
}

export class RuaDTO {
    id: number = 0;
    nome: string = '';
}

