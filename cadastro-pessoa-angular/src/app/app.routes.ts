import { PessoaListarComponent } from './componentes/pessoa-listar/pessoa-listar.component';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { HomeComponent } from './componentes/home/home.component';

export const ROUTES: Routes = [
    
    {path: '', component: HomeComponent},
    {path: 'pessoa-lista', component: PessoaListarComponent},
    /*{path: 'equipamento-novo', component: EquipamentoNovoComponent},
    {path: 'equipamento-novo/:id', component: EquipamentoNovoComponent},
    {path: 'detalhar-equipamento/:id', component: EquipamentoDetalheComponent},*/
]

export const routes: ModuleWithProviders = RouterModule.forRoot(ROUTES);