export class ResponseDTO<T>{
    data ?: T;
    msgs ?: ResponseMessagemDTO[];
}

export class Page<T>{
    content : T[] = [];
    totalElements : number = 0;
    totalPages : number = 0;
}
export class ResponseMessagemDTO {
	msg  : string = '';
	typeMsg : 'sucesso' | 'alerta' | 'erro' = 'alerta';
    titulo : string = ''
}

export class Notificacao{
    tipo : 'sucesso' | 'alerta' | 'erro' = 'alerta';
    titulo: string = '';
    mensagem: string = '';
}