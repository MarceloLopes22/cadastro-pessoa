import { Uf } from './../../modelo/enums/uf';
import { Sexo } from './../../modelo/enums/sexo';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseApi } from 'src/app/modelo/responseApi.model';
import { Pessoa } from 'src/app/modelo/pessoa.model';
import { PessoaService } from 'src/app/servicos/pessoa.service';
import { NgForm }   from '@angular/forms';

@Component({
  selector: 'app-pessoa-novo',
  templateUrl: './pessoa-novo.component.html'
})
export class PessoaNovoComponent implements OnInit {

  @ViewChild("form", {static: true}) form: NgForm;

  pessoa = new Pessoa(null,"",Sexo.SELECIONE,"",null,"","","","","", "","",null,null,null,null);
  menssage: {type: string, text: string};
  erros = [];
  classCss: {}
  sexos = new Array<string>();
  ufs = new Array<string>();
  isEdicao:boolean = false;

  ngAfterViewInit() {
  }
  

  constructor(private pessoaService: PessoaService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { 
    }

  ngOnInit() {
    let id: number = this.activatedRoute.snapshot.params['id'];
    if(id != undefined){
      this.pesquisar(id);
      this.isEdicao = true;
    }
    this.sexos = Object.keys(Sexo);
    this.ufs = Object.keys(Uf);
  }

  salvar(){
    this.pessoaService.salvarOrAtualizar(this.pessoa).subscribe((responseApi: ResponseApi) => {
      this.pessoa = responseApi.data;
      this.showMessage({
        type: 'success',
        text: responseApi.mensagemSucesso
      });
      setTimeout(()=>{
        this.router.navigate(['/pessoa-lista']);
      }, 8000);
      
    }, err =>{
      if(err["error"]["erros"][0] != null){
        this.erros = err["error"]["erros"];
      } else {
        this.erros = err["error"];
      }
      setTimeout(()=>{
        this.erros = [];
      }, 2000);
    });    
  }

  pesquisar(id: number) {
    this.pessoaService.pesquisar(id).subscribe((responseApi: ResponseApi) =>{
      this.pessoa = responseApi.data;
    }, err =>{
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }

  private showMessage(message: {type: string, text: string}) : void {
    this.menssage = message;
    this.buildClasses(message.type);
    setTimeout(()=>{
      this.menssage = undefined;
    }, 2000);
  }

  private buildClasses(type: string) : void {
    this.classCss = {
      'alert' : true
    }
    this.classCss['alert-'+type] = true;
  }

  getFormGroupClass(isInvalid: boolean, isDirty: boolean): {} {
    return {
      'form-group': true,
      'has-error': isInvalid && isDirty,
      'has-success': !isInvalid && isDirty
    };
  }

  voltar(){
    this.router.navigate(['/pessoa-lista']);
  }
}
