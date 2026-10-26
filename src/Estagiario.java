
public class Estagiario extends Funcionario {

    public Estagiario(int id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean podeBaterPonto() {
        return false;
    }

    @Override
    public int limiteHorasExtras() {
        return 0;
    }
}
