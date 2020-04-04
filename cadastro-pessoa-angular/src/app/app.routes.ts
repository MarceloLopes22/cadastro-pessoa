import { LoginComponent } from './componentes/login/login.component';
import { PessoaNovoComponent } from './componentes/pessoa-novo/pessoa-novo.component';
import { PessoaDetalharComponent } from './componentes/pessoa-detalhar/pessoa-detalhar.component';
import { PessoaListarComponent } from './componentes/pessoa-listar/pessoa-listar.component';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { HomeComponent } from './componentes/home/home.component';

export const ROUTES: Routes = [
    {path: '', component: LoginComponent},
    {path: 'login', component: LoginComponent},
    {path: 'home', component: HomeComponent},
    {path: 'pessoa-lista', component: PessoaListarComponent},
    {path: 'pessoa-novo', component: PessoaNovoComponent},
    {path: 'pessoa-novo/:id', component: PessoaNovoComponent},
    {path: 'detalhar-pessoa/:id', component: PessoaDetalharComponent},
]

export const routes: ModuleWithProviders = RouterModule.forRoot(ROUTES);