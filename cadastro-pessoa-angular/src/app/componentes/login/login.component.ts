import { PESSOA_API } from './../../servicos/pessoa.api';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  usuario: string;
  senha : string;
  mensagemErro = 'Senha Invalida';
  mensagemSucesso: string;
  loginInvalido = false;
  loginSucesso = false;
  

  constructor(
    private router: Router,
    private authService: AuthService) {   }

  ngOnInit() {
  }

  login() {
    this.authService.autenticacao(this.usuario, this.senha).subscribe((result)=> {
      this.loginInvalido = false;
      this.loginSucesso = true;
      this.mensagemSucesso = 'Login efetuado com sucesso.';
      setTimeout(()=>{
        this.router.navigate(['/home']);
      }, 2000);
      
    }, () => {
      this.loginInvalido = true;
      this.loginSucesso = false;
    });      
  }
}