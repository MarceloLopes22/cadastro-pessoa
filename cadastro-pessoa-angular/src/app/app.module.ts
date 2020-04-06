import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS  } from '@angular/common/http'; 
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './componentes/footer/footer.component';
import { HeaderComponent } from './componentes/header/header.component';
import { HomeComponent } from './componentes/home/home.component';
import { MenuComponent } from './componentes/menu/menu.component';
import { FormsModule }   from '@angular/forms';
import { routes } from './app.routes';
import { PessoaListarComponent } from './componentes/pessoa-listar/pessoa-listar.component';
import { CPFPipe } from './componentes/pipes/cpf.pipe';
import { PessoaDetalharComponent } from './componentes/pessoa-detalhar/pessoa-detalhar.component';
import { PessoaNovoComponent } from './componentes/pessoa-novo/pessoa-novo.component';
import { NgxMaskModule, IConfig } from 'ngx-mask';
import { LoginComponent } from './componentes/login/login.component';
import { HttpInterceptorService } from './componentes/login/httpInterceptor.service';
 
export const options: IConfig | (() => IConfig) = null;

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    HomeComponent,
    MenuComponent,
    PessoaListarComponent,
    CPFPipe,
    PessoaDetalharComponent,
    PessoaNovoComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    FormsModule,
    routes,
    HttpClientModule,
    NgxMaskModule.forRoot(options)
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
