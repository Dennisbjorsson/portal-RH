
public class Analista extends Funcionario {

    public Analista(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeBaterPonto() {
        return true;
    }

    @Override
    public int limiteHorasExtras() {
        return 3;
    }
}