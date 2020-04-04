import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PESSOA_API } from '../../servicos/pessoa.api';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

 USUARIO_SESAO_NOME_ATRIBUTO = 'autenticacaoUsuario'

 public usuario: string;
 public senha: string;
 mostrarMenuEmitter = new EventEmitter<boolean>();

 constructor(private http: HttpClient) {
 }

 autenticacao(usuario: string, senha: string) {
   return this.http.get(`${PESSOA_API}/api/v1/basicauth`, { 
       headers: { 
          authorization: this.criarTokenAutenticacaoBasica(usuario, senha) 
        } 
      }).pipe(map((res) => {
        if(res != null){
          this.mostrarMenuEmitter.emit(true);
        } else {
          this.mostrarMenuEmitter.emit(false);
        }

       this.usuario = usuario;
       this.senha = senha;
       this.registrarLoginSucesso(usuario);
     }));
 }

 criarTokenAutenticacaoBasica(usuario: string, senha: string) {
   return 'Basic ' + window.btoa(usuario + ":" + senha);
 }

 registrarLoginSucesso(usuario) {
   sessionStorage.setItem(this.USUARIO_SESAO_NOME_ATRIBUTO, usuario);
 }

 logout() {
   sessionStorage.removeItem(this.USUARIO_SESAO_NOME_ATRIBUTO);
   this.usuario = null;
   this.senha = null;
   this.mostrarMenuEmitter.emit(false);
 }

 isUsuarioLogado() {
   let usuario = sessionStorage.getItem(this.USUARIO_SESAO_NOME_ATRIBUTO);
   if (usuario === null) {
     return false;
   }
   return true;
 }

 getUsuarioLogado() {
   let usuario = sessionStorage.getItem(this.USUARIO_SESAO_NOME_ATRIBUTO);
   if (usuario === null) {
     return '';
   }
   return usuario;
 }

}
