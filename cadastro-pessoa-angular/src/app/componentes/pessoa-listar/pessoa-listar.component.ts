import { Component, OnInit } from '@angular/core';
import { DialogService } from 'src/app/servicos/dialog.service';
import { Router } from '@angular/router';
import { ResponseApi } from 'src/app/modelo/responseApi.model';
import { PessoaService } from 'src/app/servicos/pessoa.service';

@Component({
  selector: 'app-pessoa-listar',
  templateUrl: './pessoa-listar.component.html',
  styleUrls: ['./pessoa-listar.component.css']
})
export class PessoaListarComponent implements OnInit {

  page:number = 0;
  count:number = 5;
  pages:Array<number>;
  menssage: {type: string, text: string};
  classCss: {};
  listaPessoas = [];

  constructor(
    private dialogService: DialogService,
    private pessoaService: PessoaService,
    private router: Router
  ) {
   }

  ngOnInit() {
    this.listar(this.page, this.count);
  }

  listar(page: number, count:number) {
    this.pessoaService.listar(page, count).subscribe((responseApi: ResponseApi) => {
      this.listaPessoas = responseApi['data']['content'];
      this.pages = new Array(responseApi['data']['totalPages']);
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['erros'][0]
      });
    });
  }

  editar(id:number) {
    this.router.navigate(['/pessoa-novo', id]);
  }

  apagar(id: number) {
    this.dialogService.confirm("Você quer deletar essa pessoa?")
    .then((podeApagar:boolean) =>{
      if(podeApagar) {
        this.pessoaService.apagar(id).subscribe((responseApi: ResponseApi) =>{
          this.showMessage({
            type: 'success',
            text: 'Registro deletado.'
          });
          this.listar(this.page, this.count);
        }, err => {
          this.showMessage({
            type: 'error',
            text: err['error']['erros'][0]
          });
        });
      }
    });
  }

  detalhar(id:string) {
    this.router.navigate(['/detalhar-pessoa',id]);
  }

  setNextPagina(event:any) {
    event.preventDefault(); // evitar reload na tela
    if(this.page+1 < this.pages.length) {
      this.page = this.page + 1;
      this.listar(this.page, this.count);
    }
  }

  setPreviousPagina(event:any) {
    event.preventDefault(); // evitar reload na tela
    if(this.page+1 < this.pages.length) {
      this.page = this.page - 1;
      this.listar(this.page, this.count);
    }
  }

  setPagina(i:number, event:any) {
    event.preventDefault(); // evitar reload na tela
      this.page = i;
      this.listar(this.page, this.count);
  }

  private showMessage(message: {type: string, text: string}) : void {
    this.menssage = message;
    this.buildClasses(message.type);
    setTimeout(()=>{
      this.menssage = undefined;
    }, 3000);
  }

  private buildClasses(type: string) : void {
    this.classCss = {
      'alert' : true
    }
    this.classCss['alert-'+type] = true;
  }

}
