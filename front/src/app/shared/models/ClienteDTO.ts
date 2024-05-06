export class ClienteDTO {
    id?: number;
    nome?: string;
    telefone?: string;
    endereco?: EnderecoDTO;
}

export class EnderecoDTO {
    id?: number;
    bairro?: BairroDTO;
    rua?: RuaDTO;
    numero?: string;
    bloco?: string;
    apartamento?: string;
    complemento?: string;
    enderecoDescricao?: string;
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

