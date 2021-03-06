package br.ufpe.cin.if1001.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardViewClickActivity extends Activity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //criando view
        recyclerView = new RecyclerView(this);

        //o tamanho do recycler view não é alterado pelo conteúdo (ocupa a tela inteira sempre)
        recyclerView.setHasFixedSize(true);

        //diferente de ListView e GridView, RecyclerView não sabe nada sobre como organizar elementos
        //esta tarefa eh delegada para o LayoutManager, possibilitando diferentes abordagens
        //neste caso, temos um layout linear para estruturar elementos verticalmente...
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //outras opcoes incluem GridLayoutManager, por ex.
        //tambem pode ser implementado um LayoutManager customizado

        //definindo o adapter (semelhante a listadapter...)
        recyclerView.setAdapter(new PessoaAdapter(Constants.maisPessoas));

        //definindo layout da activity - sem usar XML (nao tem um ListActivity que possamos estender)
        setContentView(recyclerView);

    }

    private class PessoaAdapter extends RecyclerView.Adapter<CardClickHolder> {
        //fonte de dados
        Pessoa[] pessoas;

        //instanciando fonte de dados
        PessoaAdapter(Pessoa[] pArray) {
            pessoas = pArray;
        }

        @Override
        public CardClickHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //cria, configura e retorna um ViewHolder para uma linha da lista
            //parent é o ViewGroup que contem as views, usado pelo layout inflater
            //viewType é para o caso de ter múltiplos tipos de Views, em um RecyclerView
            //View v = getLayoutInflater().inflate(R.layout.itemlistacardview,parent,false);
            //View v = getLayoutInflater().inflate(R.layout.itemlistacardviewclick,parent,false);
            View v = getLayoutInflater().inflate(R.layout.itemlistacardviewclick2,parent,false);
            return new CardClickHolder(v);
        }

        @Override
        public void onBindViewHolder(CardClickHolder holder, int position) {
            //responsavel por atualizar ViewHolder com dados de um elemento na posição 'position'
            holder.bindModel(pessoas[position]);
        }

        @Override
        public int getItemCount() {
            //total de elementos
            return pessoas.length;
        }
    }

    //responsavel por fazer o binding dos dados com widgets para cada linha da lista
    static class CardClickHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nome=null;
        TextView login=null;
        ImageView icone=null;
        Uri site = null;

        //poderia tambem passar algum objeto aqui construido no adapter, para nao adicionar atributos
        CardClickHolder(View row) {
            super(row);

            nome = (TextView) row.findViewById(R.id.nome);
            login = (TextView) row.findViewById(R.id.login);
            icone = (ImageView) row.findViewById(R.id.icone);

            //definindo listener para linha/card inteiro
            //poderia definir click listener para cada view (nome, login...)
            row.setOnClickListener(this);
        }

        void bindModel(Pessoa p) {
            nome.setText(p.getNome());
            login.setText(p.getLogin());
            site = Uri.parse(p.getSite());

            if (p.getLogin().equals("lmt")) {
                icone.setImageResource(R.drawable.ok);
            }
            else {
                icone.setImageResource(R.drawable.delete);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(v.getContext(), "Clicou no item da posição: "+position,Toast.LENGTH_SHORT).show();

            //Intent i = new Intent(Intent.ACTION_VIEW,site);
            //v.getContext().startActivity(i);
        }
    }
}
