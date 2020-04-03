import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PESSOA_API } from './pessoa.api';
import { Pessoa } from '../modelo/pessoa.model';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  constructor(private http: HttpClient) { }

  salvarOrAtualizar(pessoa: Pessoa) {
    if(pessoa.id != null && pessoa.id > 0) {
      return this.http.put(`${PESSOA_API}/api/pessoa/atualizar`, pessoa);
    }
    return this.http.post(`${PESSOA_API}/api/pessoa/salvar`, pessoa);
  }

  listar(page:number, count: number) {
    return this.http.get(`${PESSOA_API}/api/pessoa/listar/${page}/${count}`);
  }

  pesquisar(id:number) {
    return this.http.get(`${PESSOA_API}/api/pessoa/pesquisar/${id}`);
  }
  
  apagar(id:number) {
    return this.http.delete(`${PESSOA_API}/api/pessoa/deletar/${id}`);
  }
}
