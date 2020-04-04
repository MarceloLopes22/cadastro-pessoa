import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit {

  isUsuarioLogado = false;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService) { }

  ngOnInit() {
    this.isUsuarioLogado = this.authService.isUsuarioLogado();
    console.log('menu ->' + this.isUsuarioLogado);
    this.authService.mostrarMenuEmitter.subscribe(
      (mostrar) => this.isUsuarioLogado = mostrar
    );
  }

  logout() {
    this.authService.logout();
    this.authService.mostrarMenuEmitter.subscribe(
      (mostrar) => this.isUsuarioLogado = mostrar
    );
  }

}
