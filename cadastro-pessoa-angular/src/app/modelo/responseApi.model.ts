import { Injectable } from '@angular/core';

@Injectable()
export class ResponseApi {

    public data: any;
    public erros: Array<string>;
    public httpStatus: any;
	public mensagemSucesso:string;
}