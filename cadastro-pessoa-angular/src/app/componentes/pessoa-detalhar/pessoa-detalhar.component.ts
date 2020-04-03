import { Component, OnInit } from '@angular/core';
import { Pessoa } from 'src/app/modelo/pessoa.model';
import { PessoaService } from 'src/app/servicos/pessoa.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseApi } from 'src/app/modelo/responseApi.model';

@Component({
  selector: 'app-pessoa-detalhar',
  templateUrl: './pessoa-detalhar.component.html'
})
export class PessoaDetalharComponent implements OnInit {

  pessoa = new Pessoa(null,"",null,"",null,"","","",null,null);
  menssage: {};
  classCss: {};

  constructor(private pessoaService: PessoaService,
              private route: ActivatedRoute,
              private router: Router
    ) {
   }

  ngOnInit() {
    let id:number = this.route.snapshot.params['id'];
    if(id != undefined) {
      this.pessoaService.pesquisar(id).subscribe((responseApi:ResponseApi) =>{
        this.pessoa = responseApi.data;
      }, err =>{
        this.showMessage({
          type: 'error',
          text: err['error']['errors'][0]
        });
      });
    }
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

  voltar(){
    this.router.navigate(['/pessoa-lista'])
  }

}
